import {Component, Input, OnInit} from '@angular/core';
import {Agent, Book, Resource} from "../../data-access/api";


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

  getImageResource(resources: Resource[] | undefined): string {
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
