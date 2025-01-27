import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Playlist} from "../models/playlist/playlist";
import {Playlists} from "../models/playlist/playlists";


@Injectable({
  providedIn: 'root',
})
export class PlaylistService {
  private apiUrl = 'http://localhost:8080/playlists';

  constructor(private http: HttpClient) {}

  getPlaylists(): Observable<Playlist[]> {
    return this.http.get<Playlists>(this.apiUrl)
      .pipe(map(response => response.playlists));
  }

  getPlaylist(id: string): Observable<Playlist> {
    return this.http.get<Playlist>(`${this.apiUrl}/${id}`);
  }

  createPlaylist(playlist: Omit<Playlist, 'id'>): Observable<Playlist> {
    return this.http.post<Playlist>(this.apiUrl, playlist);
  }

  updatePlaylist(id: string, playlist: Partial<Playlist>): Observable<Playlist> {
    return this.http.put<Playlist>(`${this.apiUrl}/${id}`, playlist);
  }

  deletePlaylist(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
