package com.example.playlistservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@Entity
@Table(name="playlists")
public class Playlist implements Comparable<Playlist>, Serializable {
    @Id
    private UUID id;
    private String name;
    private int rank;

    @Override
    public int compareTo(Playlist o) {
        return this.id.compareTo(o.id);
    }
}
