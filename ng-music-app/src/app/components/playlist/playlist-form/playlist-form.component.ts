import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PlaylistService } from '../../../services/playlist.service';

@Component({
  selector: 'app-playlist-form',
  templateUrl: './playlist-form.component.html',
  styleUrls: ['./playlist-form.component.css']
})
export class PlaylistFormComponent implements OnInit {
  playlistForm: FormGroup;
  isEditMode = false;
  loading = false;
  submitted = false;
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private playlistService: PlaylistService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.playlistForm = this.fb.group({
      name: ['', Validators.required],
      rank: [null, Validators.required]
    });
  }

  ngOnInit(): void {
    const playlistId = this.route.snapshot.paramMap.get('id');
    if (playlistId) {
      this.isEditMode = true;
      this.loadPlaylist(playlistId);
    }
  }

  get form() {
    return this.playlistForm.controls;
  }

  loadPlaylist(id: string): void {
    this.loading = true;
    this.playlistService.getPlaylist(id).subscribe({
      next: (playlist) => {
        this.playlistForm.patchValue(playlist);
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to load playlist details.';
        this.loading = false;
        console.error('Error loading playlist:', err);
      }
    });
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.playlistForm.invalid || this.loading) {
      return;
    }

    this.loading = true;
    this.error = null;

    const playlistId = this.route.snapshot.paramMap.get('id');
    const request = this.isEditMode && playlistId
      ? this.playlistService.updatePlaylist(playlistId, this.playlistForm.value)
      : this.playlistService.createPlaylist(this.playlistForm.value);

    request.subscribe({
      next: () => {
        this.router.navigate(['/playlists']);
      },
      error: (err) => {
        this.error = `Failed to ${this.isEditMode ? 'update' : 'create'} playlist. Please try again.`;
        this.loading = false;
        console.error('Error saving playlist:', err);
      }
    });
  }
}
