import {Component, OnInit} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SongService} from "../../../services/song.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-song-form',
  template: `
    <div class="container mt-4">
      <h2>{{ isEditMode ? 'Edit' : 'Add' }} Song</h2>

      <form [formGroup]="songForm" (ngSubmit)="onSubmit()" class="mt-4">
        <div class="mb-3">
          <label class="form-label">Title</label>
          <input type="text" class="form-control" formControlName="title">
        </div>

        <div class="mb-3">
          <label class="form-label">Artist</label>
          <input type="text" class="form-control" formControlName="artist">
        </div>

        <div class="mb-3">
          <label class="form-label">Length (seconds)</label>
          <input type="number" class="form-control" formControlName="length">
        </div>

        <div class="mb-3">
          <label class="form-label">Genre</label>
          <input type="text" class="form-control" formControlName="genre">
        </div>

        <button type="submit" class="btn btn-primary" [disabled]="songForm.invalid">
          {{ isEditMode ? 'Update' : 'Create' }}
        </button>
        <button type="button" class="btn btn-secondary ms-2"
                [routerLink]="['/playlists', playlistId]">Cancel</button>
      </form>
    </div>
  `
})
export class SongFormComponent implements OnInit {
  songForm: FormGroup;
  isEditMode = false;
  playlistId?: string;
  songId?: string;

  constructor(
    private fb: FormBuilder,
    private songService: SongService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.songForm = this.fb.group({
      title: ['', Validators.required],
      artist: ['', Validators.required],
      length: ['', [Validators.required, Validators.min(1)]],
      genre: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.playlistId = this.route.snapshot.paramMap.get('playlistId') ?? undefined;
    this.songId = this.route.snapshot.paramMap.get('songId') ?? undefined;
    this.isEditMode = !!this.songId;

    if (this.isEditMode && this.songId) {
      this.songService.getSong(this.songId).subscribe(song => {
        this.songForm.patchValue({
          title: song.title,
          artist: song.artist,
          length: song.length,
          genre: song.genre
        });
      });
    }
  }

  onSubmit(): void {
    if (this.songForm.valid && this.playlistId) {
      if (this.isEditMode && this.songId) {
        this.songService.updateSong(this.songId, this.songForm.value)
          .subscribe(() => {
            this.router.navigate(['/playlists', this.playlistId]);
          });
      } else {
        this.songService.createSong(this.playlistId, this.songForm.value)
          .subscribe(() => {
            this.router.navigate(['/playlists', this.playlistId]);
          });
      }
    }
  }
}
