package com.cs309.quoridorApp.repository;

import com.cs309.quoridorApp.player.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<Player, Integer> {

        //List<Player> findAll();

        //List<Player> findById(@Param("id") int id);

        //Player save(Player player);

        Player findById(int id);

//        @Transactional
//        void deleteById(int id);
}