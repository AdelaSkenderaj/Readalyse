import { Component, OnInit } from '@angular/core';
import {Book, DiscoverService} from "../../data-access/api";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-discover-full',
  templateUrl: './discover-full.component.html',
  styleUrls: ['./discover-full.component.css']
})
export class DiscoverFullComponent implements OnInit {
  title: string = '';
  pathParam: string = '';
  bookList: Book[] | undefined;

  constructor(private route: ActivatedRoute, private discoverService: DiscoverService) {
    this.route.params.subscribe((params) => {
      this.pathParam = params['listName'];
      this.title = this.pathParam?.charAt(0).toUpperCase() + this.pathParam?.slice(1);
    })

    if (this.pathParam == 'recommended') {
      this.discoverService.getRecommendedBooks({page: 0, size: 20}).subscribe((response) => {
        this.bookList = response.books;
        console.log(this.bookList)
      })
    }
    else if (this.pathParam == 'new-additions') {
      this.discoverService.getRecommendedBooks({page: 20, size: 20}).subscribe((response) => {
        this.bookList = response.books;
      })
    }
    else if(this.pathParam == 'trending') {
      this.discoverService.getRecommendedBooks({page: 687, size: 20}).subscribe((response) => {
        this.bookList = response.books;
      })
    }
    else if (this.pathParam == 'highest-rated') {
      this.discoverService.getRecommendedBooks({page: 1984, size: 20}).subscribe((response) => {
        this.bookList = response.books;
      })
    }
    console.log(this.pathParam)
  }

  ngOnInit(): void {
  }


}