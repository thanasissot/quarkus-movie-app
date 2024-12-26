import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  REST_API_URL = 'http://localhost:8881/rest';

  constructor(private http: HttpClient) { }

  getUserData(): Observable<any> {
    return this.http.post<any>(`${this.REST_API_URL}/user`, {
      "username": localStorage.getItem('username')
    });
  }

}
