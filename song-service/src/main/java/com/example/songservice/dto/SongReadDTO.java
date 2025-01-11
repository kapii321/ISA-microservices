package com.example.songservice.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class SongReadDTO {
    private UUID id;
    private String title;
    private String artist;
    private int length;
    private String genre;
    private UUID playlistId;
}
