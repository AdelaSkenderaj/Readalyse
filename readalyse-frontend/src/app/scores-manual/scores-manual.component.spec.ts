import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScoresManualComponent } from './scores-manual.component';

describe('ScoresManualComponent', () => {
  let component: ScoresManualComponent;
  let fixture: ComponentFixture<ScoresManualComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ScoresManualComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ScoresManualComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
