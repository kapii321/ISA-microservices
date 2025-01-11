package com.example.demo.dto;

import lombok.*;

import java.util.UUID;
import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode


public class PlaylistReadDTO {
    private UUID id;
    private String name;
    private int rank;
    private SongCollectionDTO songs;
}
