import {Component, Input, OnInit} from '@angular/core';
import { faStar as farStar } from '@fortawesome/free-regular-svg-icons';
import { faStar as fasStar } from '@fortawesome/free-solid-svg-icons';
import {SizeProp} from "@fortawesome/fontawesome-svg-core";

@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.css']
})
export class RatingComponent implements OnInit {

  emptyStar = farStar;
  fullStar = fasStar;
  @Input() rating: number | undefined = 0;
  @Input() size: SizeProp = "lg";

  constructor() { }

  ngOnInit(): void {
  }

}
