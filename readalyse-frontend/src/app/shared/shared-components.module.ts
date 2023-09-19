import {NgModule} from "@angular/core";
import {BookComponent} from "./book/book.component";
import {BookListComponent} from "./book-list/book-list.component";
import {NgForOf, NgIf} from "@angular/common";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import { BookFullListComponent } from './book-full-list/book-full-list.component';
import { BookInformationComponent } from './book-information/book-information.component';
import {RouterLink} from "@angular/router";

@NgModule({
    imports: [
        NgForOf,
        FontAwesomeModule,
        NgIf,
        RouterLink
    ],
  declarations: [BookComponent, BookListComponent, BookFullListComponent, BookInformationComponent],
  exports: [BookComponent, BookListComponent],
  providers: []
})

export class SharedComponentsModule {}
