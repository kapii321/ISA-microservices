package com.example.songservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "playlists")

public class Playlist implements Serializable {
    @Id
    private UUID id;
    private String name;
    
}
