import { Component, OnInit } from '@angular/core';
import {
  faHome,
  faBookBookmark,
  faBook,
  faBookReader,
  faDownload,
  faHeart,
  faGear,
  faRightFromBracket, faSearch
} from "@fortawesome/free-solid-svg-icons";
import {AuthService} from "../service/auth.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {
  isSidenavOpen: boolean = false;
  homeIcon = faHome;
  categoryIcon = faBookBookmark;
  libraryIcon = faBook;
  downloadIcon = faDownload;
  favoriteIcon = faHeart;
  settingsIcon = faGear;
  logoutIcon = faRightFromBracket;
  searchIcon = faSearch;
  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
  }

  toggleSidenav() {
    this.isSidenavOpen = !this.isSidenavOpen;
  }

  handleLogout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
