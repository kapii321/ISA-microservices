import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PlaylistService } from '../../../services/playlist.service';

@Component({
  selector: 'app-playlist-form',
  template: `
    <div class="container mt-4">
      <h2>{{ isEditMode ? 'Edit' : 'Create' }} Playlist</h2>

      <!-- Error message -->
      <div *ngIf="error" class="alert alert-danger alert-dismissible fade show" role="alert">
        {{ error }}
        <button type="button" class="btn-close" (click)="error = null"></button>
      </div>

      <form [formGroup]="playlistForm" (ngSubmit)="onSubmit()" class="mt-4">
        <div class="mb-3">
          <label for="name" class="form-label">Playlist Name</label>
          <input
            type="text"
            class="form-control"
            id="name"
            formControlName="name"
            [class.is-invalid]="submitted && form['name'].errors"
          >
          <div class="invalid-feedback" *ngIf="submitted && form['name'].errors?.['required']">
            Playlist name is required
          </div>
        </div>

        <div class="mb-3">
          <label for="rank" class="form-label">Rank</label>
          <input
            type="number"
            class="form-control"
            id="rank"
            formControlName="rank"
            [class.is-invalid]="submitted && form['rank'].errors"
          >
          <div class="invalid-feedback" *ngIf="submitted && form['rank'].errors?.['required']">
            Rank is required
          </div>
        </div>

        <div class="mt-4">
          <!-- Loading spinner on submit button when saving -->
          <button
            type="submit"
            class="btn btn-primary"
            [disabled]="loading">
            <span *ngIf="loading" class="spinner-border spinner-border-sm me-2"></span>
            {{ isEditMode ? 'Update' : 'Create' }} Playlist
          </button>
          <button
            type="button"
            class="btn btn-secondary ms-2"
            routerLink="/playlists"
            [disabled]="loading">
            Cancel
          </button>
        </div>
      </form>
    </div>
  `
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
