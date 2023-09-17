import { Component, OnInit } from '@angular/core';
import {Book, DiscoverService} from "../data-access/api";

@Component({
  selector: 'app-my-library',
  templateUrl: './my-library.component.html',
  styleUrls: ['./my-library.component.css']
})
export class MyLibraryComponent implements OnInit {
  currentlyReading: Book[] | undefined;
  wantToRead: Book[] | undefined;
  finished: Book[] | undefined;
  favorites: Book[] | undefined;

  constructor(private discoverService: DiscoverService) {
    this.discoverService.getRecommendedBooks({page: 7, size: 5}).subscribe((response) => {
      this.currentlyReading = response.books;
    })
    this.discoverService.getRecommendedBooks({page: 700, size: 5}).subscribe((response) => {
      this.wantToRead = response.books;
    })
    this.discoverService.getRecommendedBooks({page: 1637, size: 5}).subscribe((response) => {
      this.finished = response.books;
    })
    this.discoverService.getRecommendedBooks({page: 13827, size: 5}).subscribe((response) => {
      this.favorites = response.books;
    })
  }

  ngOnInit(): void {
  }

}