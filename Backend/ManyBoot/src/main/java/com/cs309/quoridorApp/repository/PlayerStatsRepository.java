package com.cs309.quoridorApp.repository;


import ch.qos.logback.core.util.COWArrayList;
import com.cs309.quoridorApp.player.Player;
import com.cs309.quoridorApp.player.PlayerStats;
import com.sun.xml.bind.v2.TODO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Repository
@RequestMapping("/api")

public interface PlayerStatsRepository extends JpaRepository<PlayerStats, String> {

    public default <PlayerStatsSorter> List<PlayerStats> getTopTen() {
        List<PlayerStats> topTen = new ArrayList<PlayerStats>();

        List<PlayerStats> playerStatsList = findAll();
        Collections.sort(playerStatsList, new PlayerStatsWinRateComparator());

        for (int i = 0; i < Math.min(playerStatsList.size(), 10); i++) {
            topTen.add(playerStatsList.get(i));
        }

        return topTen;
    }
    public class PlayerStatsWinRateComparator implements Comparator<PlayerStats> {
        @Override
        public int compare(PlayerStats p1, PlayerStats p2) {
            double winRate1 = p1.getWinRate();
            double winRate2 = p2.getWinRate();

            if (winRate1 > winRate2) {
                return -1;
            } else if (winRate1 < winRate2) {
                return 1;
            } else {
                return 0;
            }
        }
    }

}
