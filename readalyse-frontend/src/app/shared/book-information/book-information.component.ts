import {Component, Input, OnInit} from '@angular/core';
import {Agent, Book, BookService, Resource} from "../../data-access/api";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-book-information',
  templateUrl: './book-information.component.html',
  styleUrls: ['./book-information.component.css']
})
export class BookInformationComponent implements OnInit {

  book: Book = {};
  private bookId: number | undefined;
  private title: string | undefined;

  constructor(private route: ActivatedRoute, private bookService: BookService) { }

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.bookId = params['bookId'];
      if(this.bookId !== undefined)
      this.bookService.getBookById(this.bookId).subscribe((response) => {
        this.book = response;
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
}
