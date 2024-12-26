import { Injectable } from '@angular/core';
import {JwtHelperService} from "@auth0/angular-jwt";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, switchMap, tap} from "rxjs";
import {UserService} from "./user.service";
import {AuthUser} from "../model/authUser.interface";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  AUTH_API_URL = 'http://localhost:8880/security/auth';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  private jwtHelper = new JwtHelperService();

  constructor(private http: HttpClient,
              private userService: UserService,) { }

  login(credentials: any): Observable<any> {
    return this.http.post<any>(`${this.AUTH_API_URL}/login`, credentials)
      .pipe(
        tap(response => {
          localStorage.setItem('token', response.token);
          localStorage.setItem('roles', JSON.stringify(response.roles));
          localStorage.setItem('username', credentials.username);
        }),
        switchMap(() => this.runBackgroundRequests())
      );
  }

  logout(): void {
    localStorage.clear();
  }

  isAuthenticated(): boolean {
    const token = localStorage.getItem('token');
    return token != null && !this.jwtHelper.isTokenExpired(token);
  }

  private runBackgroundRequests(): Observable<any> {
    return this.userService.getUserData()
      .pipe(
        tap(userData => {
          console.log('User Data:', userData);
          localStorage.setItem('restUserId', JSON.stringify(userData.id))
          localStorage.setItem('viewedMovies', JSON.stringify((userData as AuthUser).viewedMovies))
        })
      );
  }


}
