package com.example.songservice.entity;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder

@Entity
@Table(name = "songs")
public class Song implements Comparable<Song>, Serializable {
    @Id
    private UUID id;
    private String title;
    private String artist;
    private int length;
    private String genre;


    @ManyToOne
    @JoinColumn(name="playlist")
    private Playlist playlist;

    @Override
    public int compareTo(Song o) {
        return this.id.compareTo(o.id);
    }

}
