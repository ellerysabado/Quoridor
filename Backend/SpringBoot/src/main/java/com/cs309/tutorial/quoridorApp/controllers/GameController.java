package com.cs309.tutorial.quoridorApp.controllers;

import com.cs309.tutorial.quoridorApp.game.Game;
import com.cs309.tutorial.quoridorApp.game.GameHolder;
import com.cs309.tutorial.quoridorApp.packets.GamePacket;
import com.cs309.tutorial.quoridorApp.packets.ResultPacket;
import com.cs309.tutorial.quoridorApp.packets.SessionCheckPacket;
import com.cs309.tutorial.quoridorApp.player.Player;
import com.cs309.tutorial.quoridorApp.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController
{

    @Autowired
    private SessionRepository sessionRepository;

    @PostMapping("/newGame")
    public int newGame(@RequestBody SessionCheckPacket packet)
    {
        if(!sessionRepository.existsById(packet.getId()))
            return -1;
        Player p = sessionRepository.getPlayer(packet.getId());
        Game g = GameHolder.newGame();
        g.getClientList().addClient(p);
        g.getClientList().setHost(p);
        return g.id;
    }

    @PostMapping("/startGame")
    public @ResponseBody ResultPacket startGame(@RequestBody GamePacket packet)
    {
        if(!sessionRepository.existsById(packet.getId()) ||
                !GameHolder.inSession(packet.getGameId()) ||
                !GameHolder.getGame(packet.getGameId()).getClientList().isHost(
                        sessionRepository.getPlayer(packet.getId())
                ))
            return new ResultPacket(false, "noSession, noGame, or notHost");
        GameHolder.getGame(packet.getGameId()).startGame();
        return new ResultPacket(true, "");
    }

    @PostMapping("/joinGame")
    public @ResponseBody ResultPacket joinGame(@RequestBody GamePacket packet)
    {
        if(!sessionRepository.existsById(packet.getId()) ||
                !GameHolder.inSession(packet.getGameId()) ||
                GameHolder.getGame(packet.getGameId()).getClientList()
                        .isClient(sessionRepository.getPlayer(packet.getId())))
            return new ResultPacket(false, "noSession, noGame, or alreadyInGame");

        GameHolder.getGame(packet.getGameId()).getClientList()
                .addClient(sessionRepository.getPlayer(packet.getId()));
        return new ResultPacket(true, "");
    }

}
