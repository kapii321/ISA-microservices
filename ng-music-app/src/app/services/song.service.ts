import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Song} from "../models/song/song";
import {Songs} from "../models/song/songs";

@Injectable({
  providedIn: 'root'
})
export class SongService {
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getSongs(playlistId: string): Observable<Song[]> {
    return this.http.get<Songs>(`${this.apiUrl}/playlists/${playlistId}/songs`)
      .pipe(map(response => response.songs));
  }

  getSong(songId: string): Observable<Song> {
    return this.http.get<Song>(`${this.apiUrl}/songs/${songId}`);
  }

  createSong(playlistId: string, song: Omit<Song, 'id' | 'playlist'>): Observable<Song> {
    return this.http.post<Song>(`${this.apiUrl}/playlists/${playlistId}/songs`, song);
  }

  updateSong(songId: string, song: Partial<Song>): Observable<Song> {
    return this.http.put<Song>(`${this.apiUrl}/songs/${songId}`, song);
  }

  deleteSong(songId: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/songs/${songId}`);
  }
}
