import {Component, Input, OnInit} from '@angular/core';
import {Book} from "../../data-access/api";

@Component({
  selector: 'app-book-information',
  templateUrl: './book-information.component.html',
  styleUrls: ['./book-information.component.css']
})
export class BookInformationComponent implements OnInit {

  book: Book = {};

  constructor() { }

  ngOnInit(): void {
  }

}
