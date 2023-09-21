import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Review} from "../../data-access/api";

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css']
})
export class ReviewComponent implements OnInit, OnChanges {

  @Input() review: Review | undefined = {};

  constructor() { }

  ngOnInit(): void {
  }

  ngOnChanges(simpleChanges: SimpleChanges) {
    console.log(simpleChanges)
  }

}
