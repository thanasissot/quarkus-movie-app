import {Component, EventEmitter, Input, Output} from '@angular/core';
import {AuthService} from "../../service/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  username: string = '';
  password: string = '';

  constructor(private authService: AuthService, private router: Router) { }

  login(): void {
    let credentials = {
      username: this.username,
      password: this.password
    }
    this.authService.login(credentials).subscribe(
      {
        next: (next: any) => {
          this.router.navigate(['/movies']);
        },
        error: err => {
          console.error('Login failed', err);
        }
      }
    );
  }

}
