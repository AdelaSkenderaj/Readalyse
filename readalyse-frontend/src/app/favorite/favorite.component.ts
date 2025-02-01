import { Component, OnInit } from '@angular/core';
import {Book, LibraryService} from "../data-access/api";

@Component({
  selector: 'app-favorite',
  templateUrl: './favorite.component.html',
  styleUrls: ['./favorite.component.css']
})
export class FavoriteComponent implements OnInit {

  bookList: Book[] | undefined;
  constructor(private libraryService: LibraryService) { }

  ngOnInit(): void {
    this.libraryService.getFavoriteBooksForUser({page: 0, size: 20}).subscribe((response) => {
      this.bookList = response.books;
      console.log(this.bookList)
    })
  }

}
