import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {PlaylistListComponent} from "./components/playlist/playlist-list/playlist-list.component";
import {PlaylistFormComponent} from "./components/playlist/playlist-form/playlist-form.component";
import {PlaylistDetailsComponent} from "./components/playlist/playlist-details/playlist-details.component";
import {SongFormComponent} from "./components/song/song-form/song-form.component";
import {SongDetailsComponent} from "./components/song/song-details/song-details.component";

const routes: Routes = [
  { path: 'playlists', component: PlaylistListComponent },
  { path: 'playlists/new', component: PlaylistFormComponent },
  { path: 'playlists/:id/edit', component: PlaylistFormComponent },
  { path: 'playlists/:id', component: PlaylistDetailsComponent },
  { path: 'playlists/:playlistId/songs/new', component: SongFormComponent },
  { path: 'playlists/:playlistId/songs/:songId/edit', component: SongFormComponent },
  { path: 'songs/:songId', component: SongDetailsComponent },
  { path: '', redirectTo: '/playlists', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
