import {Component, OnInit} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SongService} from "../../../services/song.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-song-form',
  templateUrl: './song-form.component.html',
  styleUrls: ['./song-form.component.css']
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
