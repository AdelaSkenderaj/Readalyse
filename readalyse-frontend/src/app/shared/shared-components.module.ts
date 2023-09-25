import {NgModule} from "@angular/core";
import {BookComponent} from "./book/book.component";
import {BookListComponent} from "./book-list/book-list.component";
import {NgForOf, NgIf} from "@angular/common";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import { BookFullListComponent } from './book-full-list/book-full-list.component';
import {RouterLink} from "@angular/router";
import {BookInformationComponent} from "./book-information/book-information.component";
import { ReviewComponent } from './review/review.component';
import { RatingComponent } from './rating/rating.component';
import { ReviewModalComponent } from './review-modal/review-modal.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
    imports: [
        NgForOf,
        FontAwesomeModule,
        NgIf,
        RouterLink,
        FormsModule,
        ReactiveFormsModule
    ],
  declarations: [BookComponent, BookListComponent, BookFullListComponent, BookInformationComponent, ReviewComponent, RatingComponent, ReviewModalComponent],
  exports: [BookComponent, BookListComponent, BookInformationComponent],
  providers: []
})

export class SharedComponentsModule {}
