package com.cs309.tutorial.quoridorApp.repository;

import com.cs309.tutorial.quoridorApp.player.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Repository
public interface GameRepository extends JpaRepository<Player, String> {

}
