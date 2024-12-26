import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Movie} from "../model/movie.interface";
import {Actor} from "../model/actor.interface";

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  private MOVIES_URL = 'http://localhost:8881/rest';  // Replace with your API URL

  constructor(private http: HttpClient) { }

  getAllMovies(): Observable<Movie[]> {
    return this.http.get<Movie[]>(`${this.MOVIES_URL}/movies`);
  }

  addMovie(movie: Partial<Movie>): Observable<Movie> {
    return this.http.put<Movie>(`${this.MOVIES_URL}/movies`, movie);
  }

  getActorsByMovie(movieId: number): Observable<Actor[]> {
    return this.http.get<Actor[]>(`${this.MOVIES_URL}/actors/by-movie/${movieId}`);
  }

  getAvailableActors(movieId: number): Observable<Actor[]> {
    return this.http.get<Actor[]>(`${this.MOVIES_URL}/actors/available-for/${movieId}`);
  }

  addActorToMovie(movieId: number, actorId: number): Observable<any> {
    return this.http.put(`${this.MOVIES_URL}/actors/assign/movies`, {
      "actorId": actorId,
      "movieIds": [movieId]
    });
  }

}
