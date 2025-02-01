import {Component, OnInit} from '@angular/core';
import {
  Agent,
  Book,
  BookService,
  LibraryService,
  Pagination,
  Resource,
  Review,
  ReviewService, StatusEnum
} from "../../data-access/api";
import {ActivatedRoute, Router} from "@angular/router";
import {NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {ReviewModalComponent} from "../review-modal/review-modal.component";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {faHeart as fullHeart} from "@fortawesome/free-solid-svg-icons";
import {faHeart as emptyHeart} from "@fortawesome/free-regular-svg-icons"
import {angularFontawesomeVersion} from "@fortawesome/angular-fontawesome/schematics/ng-add/versions";

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
  avgRating: number = 0;
  nrRatings: number = 0;
  nrReviews: number = 0;

  isFavoriteBook: boolean = false;

  fullHeart = fullHeart;
  emptyHeart = emptyHeart;

  bookStatus: string | undefined = ""
  options: string[] = ["CURRENTLY_READING", "READ", "WANT_TO_READ"]
  public form: FormGroup = this.formBuilder.group({
    bookStatus: new FormControl('')
  });

  constructor(private route: ActivatedRoute,
              private bookService: BookService,
              private router: Router,
              private reviewService: ReviewService,
              private ngbModal: NgbModal,
              private formBuilder: FormBuilder,
              private libraryService: LibraryService) { }

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.bookId = params['bookId'];
      if(this.bookId !== undefined)
      this.bookService.getBookById(this.bookId).subscribe((response) => {
        this.book = response;
        if (this.book.id != null) {
          this.loadUserReview();

          let pagination : Pagination = {
            page: 0,
            size: 5
          }

          this.form.patchValue({
            bookStatus: this.bookStatus
          })

          this.checkIsFavorite();

          this.reviewService.getReviewsByBookId(this.book.id, pagination).subscribe((response) => {
            this.bookReviews = response.reviews;
            this.reviewPagination = response.pagination;
            this.calculate();
          })
        }
      })
    })
  }

  private loadUserReview() {
    if (this.book.id != null) {
      this.reviewService.getReviewOfUser(this.book.id).subscribe((response) => {
        this.userReview = response;
      })
    }
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
    this.modalRef.componentInstance.reviewSaved.subscribe(() => {
      this.loadUserReview();
      this.calculate();
    })
  }


  private calculate() {
    let allReviews: Review[] = [];
      // @ts-ignore
    allReviews.push(...this.bookReviews)
    if (this.userReview) {
      allReviews.push(this.userReview)
    }
    let rating: number = 0;
    allReviews.forEach((review) => {
      if (review.rating != undefined) {
        rating += review.rating;
      }
      if (review.comment !== null) {
        this.nrReviews++;
      }
    })
    this.avgRating = rating / allReviews.length;
    this.nrRatings = allReviews.length;
  }

  getAvgRatings(): number {
    return this.avgRating;
  }

  getNrRatings(): number {
    return this.nrRatings;
  }

  getNrReviews(): number {
    return this.nrReviews;
  }

  updateStatus() {
    if (this.bookId != null) {
      this.bookStatus = this.form.get('bookStatus')?.value;
      this.libraryService.updateStatus(this.bookId, this.bookStatus).subscribe((response) => {
        this.bookStatus = response.status
      })
    }
  }

  checkIsFavorite() {
    if (this.bookId != null) {
      this.libraryService.checkIfFavorite(this.bookId).subscribe((response) => {
        this.isFavoriteBook = response.favorite;
      })
    }
  }

  changeFavoriteStatus() {
    if (this.bookId != null) {
      this.libraryService.updateFavorite(this.bookId).subscribe((response) => {
        this.isFavoriteBook = !this.isFavoriteBook;
      })
    }
  }
}
