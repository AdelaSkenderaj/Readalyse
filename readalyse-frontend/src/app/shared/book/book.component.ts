import {Component, Input, OnInit} from '@angular/core';
import {BookApi} from "../../data-access/api";

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit {

  @Input() book: BookApi | undefined;

  constructor() { }

  ngOnInit(): void {
  }

}
