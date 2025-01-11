package com.example.demo.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class SongCreateUpdateDTO {
    private String title;
    private String artist;
    private int length;
    private String genre;
}
