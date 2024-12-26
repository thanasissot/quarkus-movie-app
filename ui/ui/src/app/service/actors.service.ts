import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Actor} from "../model/actor.interface";
import {Movie} from "../model/movie.interface";

@Injectable({
  providedIn: 'root'
})
export class ActorsService {
  private MOVIES_URL = 'http://localhost:8881/rest';  // Replace with your API URL

  constructor(private http: HttpClient) { }

  getActors(): Observable<Actor[]> {
    return this.http.get<Actor[]>(`${this.MOVIES_URL}/actors`);
  }

  getMoviesByActor(actorId: number): Observable<Movie[]> {
    return this.http.get<Movie[]>(`${this.MOVIES_URL}/movies/by-actor/${actorId}`);
  }

  getAvailableMovies(actorId: number): Observable<Movie[]> {
    return this.http.get<Movie[]>(`${this.MOVIES_URL}/movies/available-for/${actorId}`);
  }

  addActor(actor: Partial<Actor>): Observable<Actor> {
    return this.http.put<Actor>(`${this.MOVIES_URL}/actors`, actor);
  }

  addMovieToActor(actorId: number, movieId: number): Observable<any> {
    return this.http.put(`${this.MOVIES_URL}/movies/assign/actors`, { movieId, "actorIds": [actorId] });
  }

}
