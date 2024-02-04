package com.cs309.quoridorApp.repository;

import com.cs309.quoridorApp.player.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
public interface HistoryRepository extends JpaRepository<History, Integer>{

}
