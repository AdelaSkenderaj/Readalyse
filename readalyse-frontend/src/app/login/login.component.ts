import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import {AuthService} from "../service/auth.service";
import {AuthenticationService, User} from "../data-access/api";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  constructor(
    private router: Router,
    private authService: AuthenticationService,
  ) {}
  username: string | undefined;
  password: string | undefined;
  invalidLogin: boolean | undefined;
  hide = true;
  user: User | undefined;
  ngOnInit(): void {}

  handleLogin() {
    this.authService
      .login({username: this.username, password: this.password})
      .subscribe((response) => {
        localStorage.setItem('token', <string>response.token);
        if (response.user) {
          // this.authService.userLoggedIn.next(response.user);
        }
        //this.openSnackBar();
        // this.authService.userLoggedIn.subscribe((response)=> {
        //   this.user = response;
        //
        // })
        this.router.navigate(['/discover']);
      });
  }
 }
