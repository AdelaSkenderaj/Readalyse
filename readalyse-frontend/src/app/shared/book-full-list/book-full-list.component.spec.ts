import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookFullListComponent } from './book-full-list.component';

describe('BookFullListComponent', () => {
  let component: BookFullListComponent;
  let fixture: ComponentFixture<BookFullListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BookFullListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BookFullListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
