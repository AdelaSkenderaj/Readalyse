import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DiscoverFullComponent } from './discover-full.component';

describe('DiscoverFullComponent', () => {
  let component: DiscoverFullComponent;
  let fixture: ComponentFixture<DiscoverFullComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DiscoverFullComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DiscoverFullComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
