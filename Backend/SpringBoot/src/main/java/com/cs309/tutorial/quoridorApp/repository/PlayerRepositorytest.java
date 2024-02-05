package com.cs309.tutorial.quoridorApp.repository;

import com.cs309.tutorial.quoridorApp.player.Player;
import org.springframework.data.repository.Repository;

import java.util.List;

@org.springframework.stereotype.Repository
public class PlayerRepositorytest implements Repository {


    public List<Player> findAll() {
        return null;
    }


    public void save(Player newPlayer) {
    }
}
