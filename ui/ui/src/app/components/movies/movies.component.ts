import { Component } from '@angular/core';
import {MovieService} from "../../service/movie.service";
import {Movie} from "../../model/movie.interface";
import {ViewedMovies} from "../../model/authUser.interface";
import { Actor } from 'src/app/model/actor.interface';

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.css']
})
export class MoviesComponent {
  displayedColumns: string[] = ['id', 'title', 'releaseYear', 'viewed'];
  movies: Movie[] = [];
  viewedMoviesIds: number[] = [];
  newMovie: Partial<Movie> = {};
  actors: Actor[] = [];
  selectedMovieId: number | null = null;
  availableActors: Actor[] = [];
  newActorId: number | null = null;

  constructor(private movieService: MovieService) { }

  ngOnInit(): void {
    let moviesThatAreViewed = localStorage.getItem('viewedMovies');
    if (moviesThatAreViewed !== null) {
      this.viewedMoviesIds = (JSON.parse(moviesThatAreViewed) as ViewedMovies[]).map(viewedMovie => viewedMovie.movieId);
    }
    this.loadMovies();
  }

  loadMovies(): void {
    this.movieService.getAllMovies().subscribe(movies => {
        this.movies = movies.map(movie => ({
          ...movie,
          viewed: this.viewedMoviesIds.includes(movie.id),
        }));
    });
  }

  toggleViewed(movie: Movie): void {
    movie.viewed = !movie.viewed;
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
}
