package com.example.songservice.event;

import org.springframework.context.ApplicationEvent;

public class PlaylistsReadyEvent extends ApplicationEvent {
    public PlaylistsReadyEvent(Object source) {
        super(source);
    }
}
