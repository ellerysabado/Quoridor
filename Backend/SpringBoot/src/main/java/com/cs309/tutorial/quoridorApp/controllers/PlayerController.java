package com.cs309.tutorial.quoridorApp.controllers;

import com.cs309.tutorial.quoridorApp.packets.ResultPacket;
import com.cs309.tutorial.quoridorApp.player.Player;
import com.cs309.tutorial.quoridorApp.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This Controller is for the PLayer Information
 * Stores the mapping for player input in the actual game
 */
@RestController
public class PlayerController
{

    @Autowired
    private PlayerRepository playerRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping("/player/all")
    List<Player> GetAllPlayers(){
        return playerRepository.findAll();
    }

    @PostMapping("/player/post/{username}/{password}/{name}")
    Player PostPlayerByPath(@PathVariable String username, @PathVariable String password, @PathVariable String name){
        Player newPlayer = new Player(name, username, password);
        playerRepository.save(newPlayer);
        return newPlayer;
    }

    @PostMapping("/player/post")
    Player PostPlayerByPath(@RequestBody Player newPlayer){
        playerRepository.save(newPlayer);
        return newPlayer;
    }

    /**
     * Deletes user from repository with an ID number
     * @param id Username of user to be deleted
     * @return either a success or fail ResultPacket with corresponding reasons
     */
    @DeleteMapping(path = "/player/{id}")
    ResultPacket deletePlayer(@PathVariable String id){
        if(!playerRepository.existsById(id)){
            return new ResultPacket(false, "ID of player does not exist in database");
        }
        playerRepository.deleteById(id);
        return new ResultPacket(true, "");
    }
}
