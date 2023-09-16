import { Component, OnInit } from '@angular/core';
import {BookApi} from "../data-access/api";
import {faChevronRight} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-discover',
  templateUrl: './discover.component.html',
  styleUrls: ['./discover.component.css']
})
export class DiscoverComponent implements OnInit {

  arrowRightIcon = faChevronRight;


  bookList: BookApi[];
  constructor() {
    this.bookList = [
      {
      id: 1,
      title: "Title",
      description: "Description",
      downloads: "123"
      },
      {
        id: 2,
        title: "Title 2",
        description: "Description 2",
        downloads: "123"
      },
      {
        id: 3,
        title: "Title 3",
        description: "Description 3",
        downloads: "123"
      },
      {
        id: 4,
        title: "Title 4",
        description: "Description 4",
        downloads: "123"
      },
      {
        id: 5,
        title: "Title 5",
        description: "Description 5",
        downloads: "123"
      },
      {
        id: 6,
        title: "Title 6",
        description: "Description 6",
        downloads: "123"
      },
    ]
  }

  ngOnInit(): void {
  }

}
