import {Movie} from "./movie.interface";

export interface Actor {
  id: number;
  firstName: string;
  lastName: string;
  movies: Movie[];
}
