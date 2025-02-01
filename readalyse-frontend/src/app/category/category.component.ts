import { Component, OnInit } from '@angular/core';
import {faChevronRight} from "@fortawesome/free-solid-svg-icons";
import {Book, DiscoverService} from "../data-access/api";

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {
  arrowRightIcon = faChevronRight;
  fictionList: Book[] | undefined;
  scienceFictionList: Book[] | undefined;

  shortStoresList: Book[] | undefined;

  adventureList: Book[] | undefined;

  constructor(private discoverService: DiscoverService) {
    this.discoverService.getBooksByCategory("Poetry", {page: 0, size: 5}).subscribe((response) => {
      this.fictionList = response.books;
    })

    this.discoverService.getBooksByCategory("Science Fiction",{page: 6, size: 5}).subscribe((response) => {
      this.scienceFictionList = response.books;
    })

    this.discoverService.getBooksByCategory("Short Stories",{page: 0, size: 5}).subscribe((response) => {
      this.shortStoresList = response.books;
    })

    this.discoverService.getBooksByCategory("Adventure", {page: 0, size: 5}).subscribe((response) => {
      this.adventureList = response.books;
    })
  }

  ngOnInit(): void {
  }


}
