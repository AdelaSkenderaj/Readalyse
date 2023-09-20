import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DiscoverFullInformationComponent } from './discover-full-information.component';

describe('DiscoverFullInformationComponent', () => {
  let component: DiscoverFullInformationComponent;
  let fixture: ComponentFixture<DiscoverFullInformationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DiscoverFullInformationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DiscoverFullInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
