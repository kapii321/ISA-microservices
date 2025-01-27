import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SongService } from '../../../services/song.service';
import { Song } from '../../../models/song/song';

@Component({
  selector: 'app-song-details',
  templateUrl: './song-details.component.html',
  styleUrls: ['./song-details.component.css']
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
