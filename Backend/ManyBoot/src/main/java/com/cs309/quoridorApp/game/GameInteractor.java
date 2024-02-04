package com.cs309.quoridorApp.game;

import com.cs309.quoridorApp.controllers.ChatWebSocketControl;
import com.cs309.quoridorApp.controllers.WebSocketControl;
import com.cs309.quoridorApp.dataPackets.ListedPlayer;
import com.cs309.quoridorApp.dataPackets.toServer.lobby.ChangeSettingsPacket;
import com.cs309.quoridorApp.dataPackets.websockets.NewTurnPacket;
import com.cs309.quoridorApp.dataPackets.websockets.PlayerMovedPacket;
import com.cs309.quoridorApp.dataPackets.websockets.UpdateChatPacket;
import com.cs309.quoridorApp.dataPackets.websockets.WallPlacedPacket;
import com.cs309.quoridorApp.game.board.Board;
import com.cs309.quoridorApp.game.board.Tile;
import com.cs309.quoridorApp.game.board.tileBound.DoubleWall;
import com.cs309.quoridorApp.game.board.tileBound.PlayerMarker;
import com.cs309.quoridorApp.player.*;
import com.cs309.quoridorApp.repository.HistoryRepository;
import com.cs309.quoridorApp.repository.PlayerRepository;
import com.cs309.quoridorApp.repository.PlayerStatsRepository;

import java.util.ArrayList;
import java.util.List;

public class GameInteractor
{

    public static Game CreateGame()
    {
        return GameHolder.newGame();
    }

    public static void deleteGame(Game game)
    {
        GameHolder.removeGame(game);
    }

    public static void addClientToGame(Game game, Player player)
    {
        game.getClientList().addClient(player);
    }

    public static void removeClientFromGame(Game game, Player player)
    {
        PlayerList pl = game.getClientList();
        List<Player> nb = pl.getNotBots();

        if(pl.isHost(player) && nb.size() > 1)
        {
            nb.remove(player);
            setHost(game, nb.get((int) (Math.random() * nb.size())));
        }

        game.getClientList().removeClient(player);

        if(game.getClientList().getNotBots().size() == 0)
            deleteGame(game);
    }

    public static void setHost(Game game, Player player)
    {
        game.getClientList().setHost(player);
    }

    public static void startGame(Game game, PlayerRepository players, HistoryRepository history, PlayerStatsRepository stats)
    {
        game.startGame();
        History newHistory = new History();
        game.setHistory(newHistory);
        PlayerList pl = game.getClientList();
        history.save(newHistory);


        //only messes with actual players to update their info and prep game stuff
        List<PlayerStats> toSave = new ArrayList<>();
        for(Player player : pl.getNotBots())
        {

            //player.getPreviousGames().add(newHistory);
            PlayerStats pStat = stats.getOne(player.getUsername());
            toSave.add(pStat);

            System.out.println("adding gameHistory " + newHistory.getGameID() + " " +
                    "to player " + player.getUsername() + "'s " +
                    "stats: " + pStat.getId());

            pStat.connectGame(game);
            pStat.getPrevGames().add(newHistory);

            System.out.println("added gameHistory " + newHistory.getGameID() + " " +
                    "to player " + player.getUsername() + "'s " +
                    "stats: " + pStat.getId());

            pl.setPlay(player, true);

            PlayerMarker pm = pl.getPlayerMaker(player);
            pm.setStartTitle(pm.getTile());
            pl.setWallsRemaining(player, game.getRules().getMaxWalls());
        }
        stats.saveAll(toSave);

        //only messes with bots for game stuff
        for(Player player : pl.getBots())
        {
            pl.setPlay(player, true);

            PlayerMarker pm = pl.getPlayerMaker(player);
            pm.setStartTitle(pm.getTile());
            pl.setWallsRemaining(player, game.getRules().getMaxWalls());
        }

        pl.prepTurns();

        //newHistory.updateOpponents(game);
        //history.save(newHistory);
    }

    public static void changeGameSettings(Game game, ChangeSettingsPacket settings)
    {
        PlayerList pl = game.getClientList();

        int player_count = settings.getPlayer_count();
        int host = settings.getHost();
        int botCount = settings.getBotCount();

        if(player_count > 0)
            game.getRules().setMaxPlayers(player_count);
        if(host >= 0)
            pl.setHost(pl.getClients().get(host));
        if(botCount >= 0)
        {
            List<Player> bots = pl.getBots();
            int offset = botCount - bots.size();

            //add bot (name might be a prob but just slap a random on it and call it a day
            for (int i = 0; i < offset; i++)
                pl.addClient(new BotPlayer("QUORIBOT #" + (int)(Math.random() * 100 * i)));

            //refresh bots
            bots = pl.getBots();

            //remove excess bots
            for(int i = 0; i > offset; i--)
                pl.removeClient(bots.get(-i));

        }
    }

    public static boolean hasGameEnded(Game game)
    {
        return game.getWinner() > 0;
    }

    public static void moveMarker(HistoryRepository history, Game game, Player player, int x, int y, Tile.Direction dir)
    {
        game.getClientList().getPlayerMaker(player)
                .setTile(game.getGameBoard().getTile(x, y),
                        dir);

        WebSocketControl.broadcast(game, new PlayerMovedPacket(new ListedPlayer(game, player), x, y));

        History gameHistory = history.getOne(game.getHistory().getGameID());
        if(gameHistory != null) {
            gameHistory.addPlayerMove(player, game.getGameBoard().getTile(x, y));
            history.save(gameHistory);
        }

        game.checkEndState();
    }

    public static boolean canMove(Game game, Player player, int x, int y, Tile.Direction... dir)
    {
        return game.getRules().canMoveThere(game.getGameBoard().getTile(x, y), player,
                dir);
    }

    public static void placeWall(HistoryRepository history, Game game, Player player, int x, int y, boolean isHorizontal)
    {
        DoubleWall wall = new DoubleWall(player);
        game.getClientList().addWall(player, wall);
        wall.setTile(game.getGameBoard().getTile(x, y), isHorizontal);
        WebSocketControl.broadcast(game, new WallPlacedPacket(new ListedPlayer(game, player), x, y, isHorizontal));

        History gameHistory = history.getOne(game.getHistory().getGameID());
        if(gameHistory != null) {
            gameHistory.addWallMove(player, wall);
            history.save(gameHistory);
        }
    }

    public static boolean canPlaceWall(Game game, Player player, int x, int y, boolean isHorizontal)
    {
        return game.getClientList().getWallsRemaining(player) > 0 &&
                DoubleWall.isValidPlacement(game.getGameBoard().getTile(x, y), isHorizontal) &&
                singleArea(game, player, x, y, isHorizontal);
    }

    public static boolean singleArea(Game game, Player player, int x, int y, boolean isHorizontal)
    {
        Board b = game.getGameBoard();
        PlayerMarker pm = game.getClientList().getPlayerMaker(player);

        List<Tile> checked = new ArrayList<>();
        List<Tile> toBeChekced = new ArrayList<>();

        Tile t1 = b.getTile(x, y);
        Tile t2 = t1.getAdjacent(Tile.Direction.DOWN);

        Tile t3 = t1.getAdjacent(Tile.Direction.RIGHT);
        Tile t4 = t3.getAdjacent(Tile.Direction.DOWN);

        if(!isHorizontal)
        {
            t2 = t1.getAdjacent(Tile.Direction.RIGHT);

            t3 = t1.getAdjacent(Tile.Direction.DOWN);
            t4 = t3.getAdjacent(Tile.Direction.RIGHT);
        }

        toBeChekced.add(b.getTile(0, 0));

        while(!toBeChekced.isEmpty())
        {
            Tile t = toBeChekced.remove(0);
            checked.add(t);
            //System.out.println(t.x + ", " + t.y + " connections -");
            for(Tile.Direction dir : Tile.Direction.getNonNone())
            {
                Tile adj = t.getAdjacent(dir);

                if(adj != null)
                {
                    //System.out.println("- " + adj.x + ", " + adj.y + ":");
                    //System.out.println(!(checked.contains(adj) || toBeChekced.contains(adj)));
                    //System.out.println(adj.canAddOnlyBuildings(pm, dir.getOpposite()));
                    //System.out.println((!(t.equals(t1) && adj.equals(t2)) && !(t.equals(t2) && adj.equals(t1))));
                    //System.out.println((!(t.equals(t3) && adj.equals(t4)) && !(t.equals(t4) && adj.equals(t3))));

                }
                if(adj != null && !(checked.contains(adj) || toBeChekced.contains(adj)) && adj.canAddOnlyBuildings(pm, dir.getOpposite()) &&
                        (!(t.equals(t1) && adj.equals(t2)) && !(t.equals(t2) && adj.equals(t1)) &&
                                !(t.equals(t3) && adj.equals(t4)) && !(t.equals(t4) && adj.equals(t3)))) {
                    toBeChekced.add(adj);

                }
            }
        }

        GameRules r = game.getRules();

        return checked.size() == (r.getColumnSize() * r.getRowSize());
    }

    public static void endTurn(HistoryRepository history, Game game, Player player)
    {
        PlayerList pl = game.getClientList();
        pl.nextTurn();
        if(game.isGameOver()) {
            History gameHistory = history.getOne(game.getHistory().getGameID());

            List<Player> winn = pl.getTeamMembers(game.getWinner());

            if(gameHistory != null) {
                gameHistory.setWinner(winn.size() > 0 ? winn.get(0).getUsername() : "Player");
                history.save(gameHistory);
            }

            WebSocketControl.broadcast(game, new NewTurnPacket(null));
        }
        else
        {
            WebSocketControl.broadcast(game, new NewTurnPacket(new ListedPlayer(game, player)));
            //botAct(game, player);
        }
    }

    public static void addChatMessage(Game game, Player player, String chatMessage)
    {
        String fullMessage = player.getUsername() + ": " + chatMessage;

        game.getChat().addChat(fullMessage);
        ChatWebSocketControl.broadcast(game, fullMessage);
    }

    public static void addChatMessage(Game game, String player, String chatMessage)
    {
        String fullMessage = player + ": " + chatMessage;

        game.getChat().addChat(fullMessage);
        ChatWebSocketControl.broadcast(game, fullMessage);
    }

    public static List<String> getChat(Game game)
    {
        return game.getChat().getChat();
    }

    public static void botAct(Game game, BotPlayer bot)
    {
        PlayerMarker pm = game.getClientList().getPlayerMaker(bot);
        for(int i = 0; i < 4; i++)
        {
            List<Tile.Direction> dirs = Tile.Direction.getNonNone();
            Tile.Direction dir = dirs.get((int) (Math.random() * dirs.size()));
            if(true)
                game.getRules().canMoveThere(pm.getTile(), bot, dir, Tile.Direction.NONE);
        }
    }
}
