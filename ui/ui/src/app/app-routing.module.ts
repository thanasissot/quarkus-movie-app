import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {HomeComponent} from "./components/home/home.component";
import {authGuard} from "./service/auth.guard";
import {MoviesComponent} from "./components/movies/movies.component";
import {ActorsComponent} from "./components/actors/actors.component";

const routes: Routes = [
  { path: '', redirectTo: '/movies', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'movies', component: MoviesComponent, canActivate: [authGuard] },
  { path: 'actors', component: ActorsComponent, canActivate: [authGuard] }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
