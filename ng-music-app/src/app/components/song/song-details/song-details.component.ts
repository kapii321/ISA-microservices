import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SongService } from '../../../services/song.service';
import { Song } from '../../../models/song/song';

@Component({
  selector: 'app-song-details',
  template: `
    <div class="container mt-4" *ngIf="song">
      <div class="card">
        <div class="card-header d-flex justify-content-between align-items-center">
          <h2 class="mb-0">{{ song.title }}</h2>
          <div class="btn-group">
            <button class="btn btn-warning"
                    [routerLink]="['/playlists', song.playlistId, 'songs', song.id, 'edit']">
              Edit
            </button>
            <button class="btn btn-danger" (click)="deleteSong()">Delete</button>
          </div>
        </div>
        <div class="card-body">
          <dl class="row">
            <dt class="col-sm-3">Artist</dt>
            <dd class="col-sm-9">{{ song.artist }}</dd>

            <dt class="col-sm-3">Genre</dt>
            <dd class="col-sm-9">{{ song.genre }}</dd>

            <dt class="col-sm-3">Length</dt>
            <dd class="col-sm-9">{{ song.length }} seconds</dd>
          </dl>
        </div>
      </div>

      <!-- Back button -->
      <button class="btn btn-secondary mt-3"
              [routerLink]="['/playlists', song.playlistId]">
        Back to Playlist
      </button>
    </div>
  `
})
export class SongDetailsComponent implements OnInit {
  song?: Song;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private songService: SongService
  ) {}

  ngOnInit(): void {
    const songId = this.route.snapshot.paramMap.get('songId');
    if (songId) {
      this.loadSong(songId);
    }
  }

  loadSong(id: string): void {
    this.songService.getSong(id).subscribe({
      next: (song) => {
        console.log('Received song data:', song);
        this.song = song;
      },
      error: (error) => {
        console.error('Error loading song:', error);
      }
    });
  }

  deleteSong(): void {
    if (!this.song) return;

    if (confirm('Are you sure you want to delete this song?')) {
      this.songService.deleteSong(this.song.id).subscribe({
        next: () => {
          this.router.navigate(['/playlists', this.song?.playlistId]);
        },
        error: (error) => {
          console.error('Error deleting song:', error);
        }
      });
    }
  }
}
