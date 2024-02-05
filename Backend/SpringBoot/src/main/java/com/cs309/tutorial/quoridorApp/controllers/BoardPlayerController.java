package com.cs309.tutorial.quoridorApp.controllers;

import com.cs309.tutorial.quoridorApp.game.Game;
import com.cs309.tutorial.quoridorApp.game.GameHolder;
import com.cs309.tutorial.quoridorApp.game.board.Tile;
import com.cs309.tutorial.quoridorApp.packets.GamePacket;
import com.cs309.tutorial.quoridorApp.packets.ResultPacket;
import com.cs309.tutorial.quoridorApp.packets.TestMovementPacket;
import com.cs309.tutorial.quoridorApp.player.Player;
import com.cs309.tutorial.quoridorApp.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BoardPlayerController
{
    /*
    stores the mapping for player input in the actual game
     */

    @Autowired
    private SessionRepository sessionRepository;

    @GetMapping("/seeBoardTest")
    public String seeBoardTest(@RequestBody GamePacket packet)
    {
        if(!sessionRepository.existsById(packet.getId()) ||
                !GameHolder.inSession(packet.getGameId()) ||
                GameHolder.getGame(packet.getGameId()).getGameBoard() == null)
            return "noSession, noGame, or noBoard";
        return GameHolder.getGame(packet.getGameId()).getGameBoard().toString();
    }

    @GetMapping("/byteBoardTest")
    public byte[][] byteBoardTest(@RequestBody GamePacket packet)
    {
        if(!sessionRepository.existsById(packet.getId()) ||
                !GameHolder.inSession(packet.getGameId()) ||
                GameHolder.getGame(packet.getGameId()).getGameBoard() == null)
            return new byte[0][0];
        return GameHolder.getGame(packet.getGameId()).getGameBoard().getBytes();
    }

    @PutMapping("/playTest")
    public @ResponseBody ResultPacket testMove(@RequestBody TestMovementPacket packet)
    {
        if(!sessionRepository.existsById(packet.getId()) ||
                !GameHolder.inSession(packet.getGameId()) ||
                GameHolder.getGame(packet.getGameId()).getGameBoard() == null ||
                !GameHolder.getGame(packet.getGameId()).getClientList()
                        .isClient(sessionRepository.getPlayer(packet.getId())))
            return new ResultPacket(false, "noSession, noGame, noBoard, or notInGame");

        Game g = GameHolder.getGame(packet.getGameId());
        Player p = sessionRepository.getPlayer(packet.getId());
        if(!g.getClientList().isPlaying(p))
            g.getClientList().setPlay(p, true);

        return new ResultPacket(g.getClientList().getPlayerMaker(p).setTile(
                g.getGameBoard().getTile(packet.getX(), packet.getY()),
                Tile.Direction.NONE
        ), "onMoving");
    }
}
