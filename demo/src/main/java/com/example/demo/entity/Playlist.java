package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "playlists")


public class Playlist implements Comparable<Playlist>, Serializable {
    @Id
    private UUID id;
    private String name;
    private int rank;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Song> songs;

    @ToString.Include(name = "songs")
    public String getSongTitles() {
        if (songs == null || songs.isEmpty()) {
            return "Empty";
        }
        return songs.stream()
                .map(song -> String.format("[ID: %s, Title: %s]", song.getId(), song.getTitle()))
                .reduce((a, b) -> a + ", " + b)
                .orElse("Empty");
    }

    @Override
    public int compareTo(Playlist o) {return this.id.compareTo(o.id);}
}