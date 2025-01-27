package com.example.songservice.repositories;

import com.example.songservice.entity.Song;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SongBackupRepository extends MongoRepository<Song, UUID> {
}
