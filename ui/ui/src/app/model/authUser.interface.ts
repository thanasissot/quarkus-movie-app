export interface AuthUser {
  id: number;
  username: string;
  viewedMovies: ViewedMovies[];
}

export interface ViewedMovies {
  id: number;
  movieId: number;
}
