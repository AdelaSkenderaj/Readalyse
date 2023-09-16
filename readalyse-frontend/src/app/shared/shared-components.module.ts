import {NgModule} from "@angular/core";
import {BookComponent} from "./book/book.component";
import {BookListComponent} from "./book-list/book-list.component";
import {NgForOf} from "@angular/common";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import { BookFullListComponent } from './book-full-list/book-full-list.component';

@NgModule({
    imports: [
        NgForOf,
        FontAwesomeModule
    ],
  declarations: [BookComponent, BookListComponent, BookFullListComponent],
  exports: [BookComponent, BookListComponent],
  providers: []
})

export class SharedComponentsModule {}
