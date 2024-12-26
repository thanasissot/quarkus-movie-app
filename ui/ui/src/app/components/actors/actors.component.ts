import { Component } from '@angular/core';
import {Actor} from "../../model/actor.interface";
import {ActorsService} from "../../service/actors.service";
import {Movie} from "../../model/movie.interface";

@Component({
  selector: 'app-actors',
  templateUrl: './actors.component.html',
  styleUrls: ['./actors.component.css']
})
export class ActorsComponent {
  displayedColumns: string[] = ['id', 'fullName'];
  actors: Actor[] = [];
  movies: Movie[] = [];
  selectedActorId: number | null = null;
  availableMovies: Movie[] = [];
  newActor: Partial<Actor> = {};
  newMovieId: number | null = null;

  constructor(private actorService: ActorsService) { }

  ngOnInit(): void {
    this.loadActors();
  }

  loadActors(): void {
    this.actorService.getActors().subscribe(actors => {
      this.actors = actors;
    });
  }

  selectActor(actor: Actor): void {
    this.selectedActorId = this.selectedActorId === actor.id ? null : actor.id;
    if (this.selectedActorId) {
      this.actorService.getMoviesByActor(this.selectedActorId).subscribe(movies => {
        this.movies = movies;
      });
      this.actorService.getAvailableMovies(this.selectedActorId).subscribe(availableMovies => {
        this.availableMovies = availableMovies;
      });
    } else {
      this.movies = [];
      this.availableMovies = [];
    }
  }

  addActor(): void {
    if (this.newActor.firstName && this.newActor.lastName) {
      this.actorService.addActor(this.newActor).subscribe(() => {
        this.newActor = {};
        this.loadActors();
      });
    }
  }

  addMovie(): void {
    if (this.selectedActorId && this.newMovieId) {
      this.actorService.addMovieToActor(this.selectedActorId, this.newMovieId).subscribe(() => {
        this.newMovieId = null;
        this.selectActor({ id: this.selectedActorId } as Actor); // Refresh the movie list
      });
    }
  }

  closeMovies(): void {
    this.selectedActorId = null;
    this.movies = [];
    this.availableMovies = [];
  }
}
