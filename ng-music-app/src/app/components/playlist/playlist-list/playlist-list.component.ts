import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PlaylistService } from '../../../services/playlist.service';
import { Playlist } from '../../../models/playlist/playlist';

@Component({
  selector: 'app-playlist-list',
  template: `
    <div class="container mt-4">
      <!-- Header with Add button -->
      <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>My Playlists</h2>
        <button class="btn btn-primary" routerLink="/playlists/new">
          Add New Playlist
        </button>
      </div>

      <!-- Loading indicator -->
      <div *ngIf="loading" class="text-center">
        <div class="spinner-border" role="status">
          <span class="visually-hidden">Loading...</span>
        </div>
      </div>

      <!-- Error message -->
      <div *ngIf="error" class="alert alert-danger">
        {{ error }}
      </div>

      <!-- Playlists list -->
      <div class="row" *ngIf="!loading && !error">
        <div class="col-md-6 col-lg-4 mb-3" *ngFor="let playlist of playlists">
          <div class="card h-100">
            <div class="card-body">
              <h5 class="card-title">{{ playlist.name }}</h5>
              <p class="card-text">Rank: {{ playlist.rank }}</p>
            </div>
            <div class="card-footer bg-transparent border-top-0">
              <div class="btn-group w-100">
                <button class="btn btn-info" [routerLink]="['/playlists', playlist.id]">
                  View
                </button>
                <button class="btn btn-warning" [routerLink]="['/playlists', playlist.id, 'edit']">
                  Edit
                </button>
                <button class="btn btn-danger" (click)="deletePlaylist(playlist)">
                  Delete
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- Empty state -->
        <div *ngIf="playlists.length === 0" class="col-12 text-center">
          <p class="text-muted">No playlists found. Create one to get started!</p>
        </div>
      </div>
    </div>
  `
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
