package com.example.playlistservice.repositories;

import com.example.playlistservice.entity.Playlist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlaylistRepository extends MongoRepository<Playlist, UUID> {


}
