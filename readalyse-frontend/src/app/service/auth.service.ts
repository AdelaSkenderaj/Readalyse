import {AuthenticationService, RoleEnum, User} from "../data-access/api";
import {EventEmitter, Injectable, OnInit} from "@angular/core";
import {JwtHelperService} from "@auth0/angular-jwt";
import {BehaviorSubject, Subject} from "rxjs";

@Injectable({
  providedIn: 'root',
})
export class AuthService extends AuthenticationService implements OnInit{

  token: string | null = '';
  jwtHelper = new JwtHelperService();
  loggedInUsername: string = '';

  user = new EventEmitter<User>();
  userRole = new EventEmitter<RoleEnum>();

  ngOnInit(): void {
    // @ts-ignore
    this.userLoggedIn.next(JSON.parse(localStorage.getItem('user')));
  }


  public loadToken(): void {
    this.token = localStorage.getItem('token');
  }

  public getToken(): string {
    return <string>this.token;
  }

  public isLoggedIn(): boolean {
    this.loadToken();
    if (this.token != null && this.token !== '') {
      if (this.jwtHelper.decodeToken(this.token).sub != null || '') {
        if (!this.jwtHelper.isTokenExpired(this.token)) {
          this.loggedInUsername = this.jwtHelper.decodeToken(this.token).sub;
          return true;
        }
      }
    }
    this.logout();
    return false;
  }

  public logout(): void {
    this.token = null;
    this.loggedInUsername = '';
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    localStorage.removeItem('users');
  }


  userLoggedIn: Subject<User> = new BehaviorSubject<User>(
    // @ts-ignore
    JSON.parse(localStorage.getItem('user'))
  );
}
