import { Component, OnInit } from '@angular/core';
import {Book, DiscoverService} from "../data-access/api";
import {faChevronRight} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-discover',
  templateUrl: './discover.component.html',
  styleUrls: ['./discover.component.css']
})
export class DiscoverComponent implements OnInit {

  arrowRightIcon = faChevronRight;
  recommendedList: Book[] | undefined;
  newList: Book[] | undefined;

  trendingList: Book[] | undefined;

  highestRatedList: Book[] | undefined;

  constructor(private discoverService: DiscoverService) {
    this.discoverService.getRecommendedBooks({page: 0, size: 5}).subscribe((response) => {
    this.recommendedList = response.books;
    })

    this.discoverService.getNewBooks({page: 20, size: 5}).subscribe((response) => {
      this.newList = response.books;
    })

    this.discoverService.getTrendingBooks({page: 687, size: 5}).subscribe((response) => {
      this.trendingList = response.books;
    })

    this.discoverService.getHighestRatedBooks({page: 1984, size: 5}).subscribe((response) => {
      this.highestRatedList = response.books;
    })
  }

  ngOnInit(): void {
  }

}
