import {Component, Input, OnInit} from '@angular/core';
import {Book} from "../../data-access/api";
import {faChevronRight} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {

  arrowRightIcon = faChevronRight;

  @Input() bookList: Book[] | undefined;

  constructor() { }

  ngOnInit(): void {
  }

}
