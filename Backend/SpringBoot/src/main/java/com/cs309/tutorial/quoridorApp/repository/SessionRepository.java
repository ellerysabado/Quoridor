package com.cs309.tutorial.quoridorApp.repository;

import com.cs309.tutorial.quoridorApp.player.Player;
import com.cs309.tutorial.quoridorApp.player.PlayerSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<PlayerSession, UUID>
{
    public List<PlayerSession> findByPlayerUsername(String playerUsername);

    public default boolean hasSession(String username)
    {
        List<PlayerSession> ps = findByPlayerUsername(username);
        return !ps.isEmpty();
    }

    public default Player getPlayer(UUID id)
    {
        return getOne(id).getPlayer();
    }

}
