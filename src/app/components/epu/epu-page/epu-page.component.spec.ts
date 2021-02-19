import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EpuPageComponent } from './epu-page.component';

describe('PageComponent', () => {
  let component: EpuPageComponent;
  let fixture: ComponentFixture<EpuPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EpuPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EpuPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
