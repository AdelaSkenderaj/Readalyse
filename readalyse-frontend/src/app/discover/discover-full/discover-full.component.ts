import { Component, OnInit } from '@angular/core';
import {BookApi} from "../../data-access/api";
import {ActivatedRoute, Route} from "@angular/router";

@Component({
  selector: 'app-discover-full',
  templateUrl: './discover-full.component.html',
  styleUrls: ['./discover-full.component.css']
})
export class DiscoverFullComponent implements OnInit {
  title: string = '';
  bookList: BookApi[];

  constructor(private route: ActivatedRoute) {
    this.route.params.subscribe((params) => {
      this.title = params['listName'];
      this.title = this.title?.charAt(0).toUpperCase() + this.title?.slice(1);
    })
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
    {
      id: 7,
      title: "Title 6",
      description: "Description 6",
      downloads: "123"
    },
    {
      id: 8,
      title: "Title 6",
      description: "Description 6",
      downloads: "123"
    },
    {
      id: 9,
      title: "Title 6",
      description: "Description 6",
      downloads: "123"
    },
    {
      id: 10,
      title: "Title 6",
      description: "Description 6",
      downloads: "123"
    },
    {
      id: 11,
      title: "Title 6",
      description: "Description 6",
      downloads: "123"
    },
    {
      id: 12,
      title: "Title 6",
      description: "Description 6",
      downloads: "123"
    },
    {
      id: 13,
      title: "Title 6",
      description: "Description 6",
      downloads: "123"
    },

  ]}

  ngOnInit(): void {
  }


}
