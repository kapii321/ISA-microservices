import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {PlaylistDetailsComponent} from "./components/playlist/playlist-details/playlist-details.component";
import {SongFormComponent} from "./components/song/song-form/song-form.component";
import {SongDetailsComponent} from "./components/song/song-details/song-details.component";
import {HttpClientModule} from "@angular/common/http";
import {ReactiveFormsModule} from "@angular/forms";
import {PlaylistListComponent} from "./components/playlist/playlist-list/playlist-list.component";
import {PlaylistFormComponent} from "./components/playlist/playlist-form/playlist-form.component";

@NgModule({
  declarations: [
    AppComponent,
    PlaylistListComponent,
    PlaylistFormComponent,
    PlaylistDetailsComponent,
    SongFormComponent,
    SongDetailsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
