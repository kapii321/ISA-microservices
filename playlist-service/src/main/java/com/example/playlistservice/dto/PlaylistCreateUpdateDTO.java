package com.example.playlistservice.dto;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PlaylistCreateUpdateDTO {
    private String name;
    private int rank;
}
