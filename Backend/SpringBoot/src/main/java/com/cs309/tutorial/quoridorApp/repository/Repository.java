package com.cs309.tutorial.quoridorApp.repository;

import com.cs309.tutorial.quoridorApp.player.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<Player, Integer> {

        Player findById(int id);
}