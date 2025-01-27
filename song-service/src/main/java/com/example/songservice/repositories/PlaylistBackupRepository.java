package com.example.songservice.repositories;

import com.example.songservice.entity.Playlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlaylistBackupRepository extends MongoRepository<Playlist, UUID> {
}
