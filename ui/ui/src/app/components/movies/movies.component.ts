import {Component, OnInit} from '@angular/core';
import {MovieService} from "../../service/movie.service";
import {Movie} from "../../model/movie.interface";
import {ViewedMovies} from "../../model/authUser.interface";
import { Actor } from 'src/app/model/actor.interface';

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.scss']
})
export class MoviesComponent implements OnInit {
  displayedColumns: string[] = ['id', 'title', 'releaseYear', 'viewed', 'watchlist', 'saveChanges'];
  movies: Movie[] = [];
  viewedMovies: ViewedMovies[] = [];
  newMovie: Partial<Movie> = {};
  actors: Actor[] = [];
  selectedMovieId: number | null = null;
  availableActors: Actor[] = [];
  newActorId: number | null = null;

  constructor(private movieService: MovieService) { }

  ngOnInit(): void {
    let moviesThatAreViewed = localStorage.getItem('viewedMovies');
    if (moviesThatAreViewed !== null) {
      this.viewedMovies = (JSON.parse(moviesThatAreViewed) as ViewedMovies[]);
    }
    this.loadMovies();
  }

  loadMovies(): void {
    this.movieService.getAllMovies().subscribe(movies => {
        this.movies = movies.map(movie => ({
          ...movie,
          viewed: this.findIfViewed(movie.id, true),
          watchlist: this.findIfViewed(movie.id, false),
          movieViewedId: this.findViewedId(movie.id),
      }))
    });

  }

  toggleViewed(movie: Movie): void {
    movie.viewed = !movie.viewed;
    // You can add a service call here to update the viewed status on the backend
  }

  toggleWatchlist(movie: Movie): void {
    movie.watchlist = !movie.watchlist;
    // You can add a service call here to update the viewed status on the backend
  }

  addMovie(): void {
    if (this.newMovie.title && this.newMovie.releaseYear) {
      this.movieService.addMovie(this.newMovie).subscribe(() => {
        this.newMovie = {};
        this.loadMovies();
      });
    }
  }

  selectMovie(movie: Movie): void {
    this.selectedMovieId = this.selectedMovieId === movie.id ? null : movie.id;
    if (this.selectedMovieId) {
      this.movieService.getActorsByMovie(this.selectedMovieId).subscribe(actors => {
        this.actors = actors;
      });
      this.movieService.getAvailableActors(this.selectedMovieId).subscribe(availableActors => {
        this.availableActors = availableActors;
      });
    } else {
      this.actors = [];
      this.availableActors = [];
    }
  }

  addActor(): void {
    if (this.selectedMovieId && this.newActorId) {
      this.movieService.addActorToMovie(this.selectedMovieId, this.newActorId).subscribe(() => {
        this.newActorId = null;
        this.selectMovie({ id: this.selectedMovieId } as Movie); // Refresh the actor list
      });
    }
  }

  closeActors(): void {
    this.selectedMovieId = null;
    this.actors = [];
    this.availableActors = [];
  }

  saveMovie(movie: Movie): void {

  }

  private findIfViewed(moviedId: number, viewField: boolean): boolean {
    let filteredMovies = this.viewedMovies.filter(movie => movie.id === moviedId);
    if (filteredMovies.length > 0) {
      return viewField ? filteredMovies[0].viewed : filteredMovies[0].watchlist;
    }

    return false;
  }

  private findViewedId(moviedId: number): any {
    let filteredMovies = this.viewedMovies.filter(movie => movie.id === moviedId);
    if (filteredMovies.length > 0) {
      return filteredMovies[0].id;
    }

    return null;
  }
}
