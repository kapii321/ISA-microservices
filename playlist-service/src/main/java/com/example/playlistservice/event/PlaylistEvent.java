package com.example.playlistservice.event;

import java.util.UUID;

public record PlaylistEvent(UUID playlistId, String name, String eventType) {}
