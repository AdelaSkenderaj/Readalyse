import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Review, ReviewService} from "../../data-access/api";
import { faStar as farStar } from '@fortawesome/free-regular-svg-icons';
import { faStar as fasStar } from '@fortawesome/free-solid-svg-icons';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-review-modal',
  templateUrl: './review-modal.component.html',
  styleUrls: ['./review-modal.component.css']
})
export class ReviewModalComponent implements OnInit {

  @Input() bookId: number | undefined;
  @Output() reviewSaved: EventEmitter<void> = new EventEmitter<void>();
  rating: number = 0;
  comment: string | undefined;

  emptyStar = farStar;
  fullStar = fasStar;

  constructor(private activeModal: NgbActiveModal, private reviewService: ReviewService) { }

  ngOnInit(): void {
  }

  addRating(number: number) {
    this.rating = number;
  }

  onClose() {
    this.activeModal.close();
  }

  onSave() {
    let review: Review = {
      rating: this.rating,
      comment: this.comment,
      bookId: this.bookId
    }
    this.reviewService.createReview(review).subscribe((response) => {
      this.reviewSaved.emit();
      this.activeModal.close();
    })
  }
}
