package com.example.songservice.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Document(collection = "songs")
public class Song implements Comparable<Song>, Serializable {
    @Id
    private UUID id;
    private String title;
    private String artist;
    private int length;
    private String genre;

    @DBRef
    private Playlist playlist;

    @Override
    public int compareTo(Song o) {
        return this.id.compareTo(o.id);
    }

}
