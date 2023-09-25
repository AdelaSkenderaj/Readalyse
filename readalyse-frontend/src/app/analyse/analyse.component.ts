import { Component, OnInit } from '@angular/core';
import {AnalyseService, Book, ReadabilityScores} from "../data-access/api";

@Component({
  selector: 'app-analyse',
  templateUrl: './analyse.component.html',
  styleUrls: ['./analyse.component.css']
})
export class AnalyseComponent implements OnInit {
  book: Book = {};
  inputText: any;

  readabilityScores: ReadabilityScores = {};

  constructor(private analysisService: AnalyseService) { }

  ngOnInit(): void {
  }

  analyse() {
    this.analysisService.analyseText({text: this.inputText}).subscribe((response)=> {
      this.readabilityScores = response;
    })
  }
}
