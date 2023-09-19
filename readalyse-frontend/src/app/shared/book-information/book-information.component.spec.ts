import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookInformationComponent } from './book-information.component';

describe('BookInformationComponent', () => {
  let component: BookInformationComponent;
  let fixture: ComponentFixture<BookInformationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BookInformationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BookInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
