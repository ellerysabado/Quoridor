package com.cs309.quoridorApp.repository;

import com.cs309.quoridorApp.player.Player;
import com.cs309.quoridorApp.player.PlayerSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Repository
//@RequestMapping("/api")
public interface SessionRepository extends JpaRepository<PlayerSession, String>
{
    public List<PlayerSession> findByPlayerUsername(String playerUsername);

    public default boolean hasSession(String username)
    {
        List<PlayerSession> ps = findByPlayerUsername(username);
        return !ps.isEmpty();
    }

    public default Player getPlayer(String id)
    {
        return getOne(id).getPlayer();
    }

    /*
    public default boolean existsById(UUID id)
    {
        return existsById(id.toString());
    }

    public default void deleteById(UUID id)
    {
        deleteById(id.toString());
    }

    public default PlayerSession getOne(UUID id)
    {
        return getOne(id.toString());
    }
     */
}
