import {Component, Input, OnInit} from '@angular/core';
import {Book} from "../../data-access/api";


@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit {

  @Input() book: Book = {};

  constructor() { }

  ngOnInit(): void {
  }

  getImageResource(resources: object[] | undefined): string {
    let mediumImage = '';
    let smallImage = '';
    if (resources) {
      resources?.forEach((resource) => {
        // @ts-ignore
        if (resource.type === "image/jpeg" && resource.url.endsWith("cover.medium.jpg")) {
          // @ts-ignore
          mediumImage = resource.url;
        }
        // @ts-ignore
        if (resource.type === "image/jpeg" && resource.url.endsWith("cover.small.jpg")) {
          // @ts-ignore
          smallImage = resource.url;
        }
      })
    }
    return mediumImage.length > 0 ? mediumImage : smallImage;
  }

}
