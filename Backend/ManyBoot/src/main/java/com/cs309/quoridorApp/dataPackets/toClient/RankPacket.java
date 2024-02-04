package com.cs309.quoridorApp.dataPackets.toClient;

import com.cs309.quoridorApp.dataPackets.toClient.game.ReturnBoardPacket;
import com.cs309.quoridorApp.player.Player;
import com.cs309.quoridorApp.player.PlayerList;
import com.cs309.quoridorApp.player.PlayerStats;
import com.cs309.quoridorApp.repository.PlayerStatsRepository;

import java.util.ArrayList;
import java.util.List;

public class RankPacket extends ClientPacket{

    private List<RankPlayers> players = new ArrayList<RankPlayers>();

    public RankPacket(PlayerStatsRepository stats) {
        List<PlayerStats> topTen = stats.getTopTen();
        for(int i = 0; i < topTen.size(); i++)

        players.add(new RankPlayers(i+1, topTen.get(i).getId(), topTen.get(i).getWinRate()));

    }

    public List<RankPlayers> getPlayers() {
        return players;
    }

    public void setPlayers(List<RankPlayers> players) {
        this.players = players;
    }

    public class RankPlayers {
        private String username;

        private double winPercent;

        private int rankNum;

        public RankPlayers( int rankNum, String username, double winPercent) {
            this.username = username;
            this.winPercent = winPercent;
            this.rankNum =rankNum;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public double getWinPercent() {
            return winPercent;
        }

        public void setWinPercent(double winPercent) {
            this.winPercent = winPercent;
        }

        public int getRankNum() {
            return rankNum;
        }

        public void setRankNum(int rankNum) {
            this.rankNum = rankNum;
        }
    }

}
