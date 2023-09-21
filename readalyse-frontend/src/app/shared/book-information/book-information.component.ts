import {Component, OnInit} from '@angular/core';
import {Agent, Book, BookService, Pagination, Resource, Review, ReviewService} from "../../data-access/api";
import {ActivatedRoute, Router} from "@angular/router";
import {NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {ReviewModalComponent} from "../review-modal/review-modal.component";

@Component({
  selector: 'app-book-information',
  templateUrl: './book-information.component.html',
  styleUrls: ['./book-information.component.css']
})
export class BookInformationComponent implements OnInit {

  book: Book = {};
  bookId: number | undefined;
  title: string | undefined;
  userReview: Review | undefined;
  bookReviews: Review[] | undefined;

  reviewPagination: Pagination | undefined;
  modalRef: NgbModalRef | undefined;

  constructor(private route: ActivatedRoute,
              private bookService: BookService,
              private router: Router,
              private reviewService: ReviewService,
              private ngbModal: NgbModal) { }

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.bookId = params['bookId'];
      if(this.bookId !== undefined)
      this.bookService.getBookById(this.bookId).subscribe((response) => {
        this.book = response;
        if (this.book.id != null) {
          this.reviewService.getReviewOfUser(this.book.id).subscribe((response) => {
            this.userReview = response;
          })

          let pagination : Pagination = {
            page: 0,
            size: 5
          }

          this.reviewService.getReviewsByBookId(this.book.id, pagination).subscribe((response) => {
            this.bookReviews = response.reviews;
            this.reviewPagination = response.pagination;
          })
        }
      })
    })
  }

  getImageResource(resources: Array<Resource> | undefined) {
    let mediumImage = '';
    let smallImage = '';
    resources?.forEach((resource) => {
      if (resource?.type === "image/jpeg" && resource.url?.endsWith("cover.medium.jpg")) {
        mediumImage = resource.url;
      }
      if (resource.type === "image/jpeg" && resource.url?.endsWith("cover.small.jpg")) {
        smallImage = resource.url;
      }
    })
    return mediumImage.length > 0 ? mediumImage : smallImage;
  }

  getCreator(agents: Array<Agent> | undefined): string {
    let creators = '';
    agents?.forEach((agent) => {
      if (agent.type?.name === "Creator") {
        creators = creators + agent.person?.name + ", "
      }
    })
    return creators.substr(0, creators.lastIndexOf(","));
  }

  navigateToReadingPage() {
    let resourceUrl: string | undefined;
    this.book.resources?.forEach(resource => {
      if (resource.url?.endsWith(".html") || resource.url?.endsWith(".html.images")) {
        resourceUrl = resource.url;
      }
    })
    if (resourceUrl !== undefined) {
      window.location.href = resourceUrl;
    }
  }

  openModal() {
    this.modalRef = this.ngbModal.open(ReviewModalComponent, {
      centered: true
    })
    this.modalRef.componentInstance.bookId = this.bookId;
  }
}
