package com.example.songservice.event;

import java.util.UUID;

public record PlaylistEvent(UUID playlistId, String name, String eventType) {}
