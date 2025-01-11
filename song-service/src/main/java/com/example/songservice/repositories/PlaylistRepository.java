package com.example.songservice.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.songservice.entity.Playlist;

import java.util.UUID;
@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, UUID> {
}
