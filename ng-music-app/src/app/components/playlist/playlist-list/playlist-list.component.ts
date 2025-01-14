import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PlaylistService } from '../../../services/playlist.service';
import { Playlist } from '../../../models/playlist/playlist';

@Component({
  selector: 'app-playlist-list',
  templateUrl: './playlist-list.component.html',
  styleUrls: ['./playlist-list.component.css']
})
export class PlaylistListComponent implements OnInit {
  playlists: Playlist[] = [];
  loading = false;
  error: string | null = null;

  constructor(
    private playlistService: PlaylistService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadPlaylists();
  }

  loadPlaylists(): void {
    this.loading = true;
    this.error = null;

    this.playlistService.getPlaylists().subscribe({
      next: (playlists) => {
        this.playlists = playlists;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to load playlists. Please try again later.';
        this.loading = false;
        console.error('Error loading playlists:', err);
      }
    });
  }

  deletePlaylist(playlist: Playlist): void {
    if (confirm(`Are you sure you want to delete "${playlist.name}"? This will also delete all songs in the playlist.`)) {
      this.playlistService.deletePlaylist(playlist.id).subscribe({
        next: () => {
          this.loadPlaylists(); // Reload the list after deletion
        },
        error: (err) => {
          this.error = 'Failed to delete playlist. Please try again.';
          console.error('Error deleting playlist:', err);
        }
      });
    }
  }
}
