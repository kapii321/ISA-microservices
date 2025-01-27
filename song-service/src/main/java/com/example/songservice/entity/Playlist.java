package com.example.songservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Document(collection = "simplified_playlists_backup")
@Table(name = "playlists")

public class Playlist implements Serializable {
    @Id
    private UUID id;
    private String name;
    
}
