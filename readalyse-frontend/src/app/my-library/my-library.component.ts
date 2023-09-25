import { Component, OnInit } from '@angular/core';
import {Book, LibraryService} from "../data-access/api";

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

  constructor(private libraryService: LibraryService) {
    this.libraryService.getCurrentlyReadingBooksForUser({page: 0, size: 5}).subscribe((response) => {
      this.currentlyReading = response.books;
    })
    this.libraryService.getWantToReadBooksForUser({page: 0, size: 5}).subscribe((response) => {
      this.wantToRead = response.books;
    })
    this.libraryService.getFinishedReadingBooksForUser({page: 0, size: 5}).subscribe((response) => {
      this.finished = response.books;
    })
    this.libraryService.getFavoriteBooksForUser({page: 0, size: 5}).subscribe((response) => {
      this.favorites = response.books;
    })
  }

  ngOnInit(): void {
  }

}
