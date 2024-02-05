package com.cs309.tutorial.quoridorApp.controllers;

import com.cs309.tutorial.quoridorApp.packets.ResultPacket;
import com.cs309.tutorial.quoridorApp.packets.SessionCheckPacket;
import com.cs309.tutorial.quoridorApp.packets.SessionConfirmationPacket;
import com.cs309.tutorial.quoridorApp.packets.StartSessionPacket;
import com.cs309.tutorial.quoridorApp.player.Player;
import com.cs309.tutorial.quoridorApp.player.PlayerSession;
import com.cs309.tutorial.quoridorApp.repository.PlayerRepository;
import com.cs309.tutorial.quoridorApp.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class SessionController
{
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @PostMapping("/login")
    public @ResponseBody SessionConfirmationPacket startSession(@RequestBody StartSessionPacket sessionPacket)
    {

        UUID uuid = new UUID(0,0);
        String error = "";

        Player player = null;
        if(playerRepository.existsById(sessionPacket.getUsername()))
            player = playerRepository.getOne(sessionPacket.getUsername());

        if(sessionPacket.isLogin())
        {
            if (player == null)
                error = "Cannot find account";
            else if (!player.getPassword().equals(sessionPacket.getPassword()))
                error = "Incorrect password";
            else
            {
                PlayerSession ps = new PlayerSession(player);
                uuid = ps.getUuid();
                sessionRepository.save(ps);
            }
        }
        else
        {
            if (player == null)
            {
                Player p = new Player(sessionPacket.getName(),
                        sessionPacket.getUsername(), sessionPacket.getPassword());
                playerRepository.save(p);
                PlayerSession ps = new PlayerSession(p);
                uuid = ps.getUuid();
                sessionRepository.save(ps);
            }
            else
                error = "Username is taken";
        }
        return new SessionConfirmationPacket(uuid, error);
    }

    @DeleteMapping("/logout")
    public @ResponseBody ResultPacket logout(@RequestBody SessionCheckPacket packet)
    {
        if(!sessionRepository.existsById(packet.getId()))
            return new ResultPacket(false, "Session does not exist closed");
        sessionRepository.deleteById(packet.getId());
        return new ResultPacket(true, "");
    }
}
