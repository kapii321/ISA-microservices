package com.example.playlistservice.dto;

import lombok.*;

import java.util.UUID;



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
}
