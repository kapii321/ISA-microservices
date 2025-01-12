import {Component, OnInit} from "@angular/core";
import {Playlist} from "../../../models/playlist/playlist";
import {Song} from "../../../models/song/song";
import {ActivatedRoute, Router} from "@angular/router";
import {PlaylistService} from "../../../services/playlist.service";
import {SongService} from "../../../services/song.service";

@Component({
  selector: 'app-playlist-details',
  template: `
    <div class="container mt-4" *ngIf="playlist">
      <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>{{ playlist.name }}</h2>
        <button class="btn btn-primary" [routerLink]="['songs', 'new']">Add New Song</button>
      </div>

      <div class="card mb-4">
        <div class="card-body">
          <h5 class="card-title">Playlist Details</h5>
          <p class="card-text">Rank: {{ playlist.rank }}</p>
          <div class="btn-group">
            <button class="btn btn-warning" [routerLink]="['/playlists', playlist.id, 'edit']">
              Edit Playlist
            </button>
            <button class="btn btn-danger" (click)="deletePlaylist()">
              Delete Playlist
            </button>
          </div>
        </div>
      </div>

      <h3>Songs</h3>
      <ng-container *ngIf="songs.length > 0; else noSongsTemplate">
        <div class="list-group">
          <div *ngFor="let song of songs" class="list-group-item">
            <div class="d-flex justify-content-between align-items-center">
              <div>
                <h5 class="mb-1">{{ song.title }}</h5>
              </div>
              <div class="btn-group">
                <button class="btn btn-info btn-sm" [routerLink]="['/songs', song.id]">
                  View
                </button>
                <button class="btn btn-warning btn-sm" [routerLink]="['songs', song.id, 'edit']">
                  Edit
                </button>
                <button class="btn btn-danger btn-sm" (click)="deleteSong(song.id)">
                  Delete
                </button>
              </div>
            </div>
          </div>
        </div>
      </ng-container>

      <ng-template #noSongsTemplate>
        <p class="text-muted">No songs in this playlist yet.</p>
      </ng-template>

      <button class="btn btn-secondary mt-3"
              [routerLink]="['/playlists']">
        Back to List View
      </button>
    </div>
  `
})
export class PlaylistDetailsComponent implements OnInit {
  playlist?: Playlist;
  songs: Song[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private playlistService: PlaylistService,
    private songService: SongService
  ) {}

  ngOnInit(): void {
    const playlistId = this.route.snapshot.paramMap.get('id');
    if (playlistId) {
      this.loadPlaylist(playlistId);
      this.loadSongs(playlistId);
    }
  }

  loadPlaylist(id: string): void {
    this.playlistService.getPlaylist(id).subscribe(playlist => {
      this.playlist = playlist;
    });
  }

  loadSongs(playlistId: string): void {
    this.songService.getSongs(playlistId).subscribe(songs => {
      this.songs = songs;
    });
  }

  deletePlaylist(): void {
    if (!this.playlist?.id) return;

    if (confirm('Are you sure you want to delete this playlist? All songs will be deleted as well.')) {
      this.playlistService.deletePlaylist(this.playlist.id).subscribe(() => {
        this.router.navigate(['/playlists']);
      });
    }
  }

  deleteSong(songId: string): void {
    if (confirm('Are you sure you want to delete this song?')) {
      this.songService.deleteSong(songId).subscribe(() => {
        if (this.playlist?.id) {
          this.loadSongs(this.playlist.id);
        }
      });
    }
  }
}
