import {Component, Input, OnInit} from '@angular/core';
import {BookApi} from "../../data-access/api";
import {faArrowRight, faChevronRight} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {

  arrowRightIcon = faChevronRight;

  @Input() bookList: BookApi[] | undefined;

  constructor() { }

  ngOnInit(): void {
  }

}
