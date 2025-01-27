import {Component, OnInit} from "@angular/core";
import {Playlist} from "../../../models/playlist/playlist";
import {Song} from "../../../models/song/song";
import {ActivatedRoute, Router} from "@angular/router";
import {PlaylistService} from "../../../services/playlist.service";
import {SongService} from "../../../services/song.service";

@Component({
  selector: 'app-playlist-details',
  templateUrl: './playlist-details.component.html',
  styleUrls: ['./playlist-details.component.css']
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
