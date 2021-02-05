import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EpuComponent } from './epu.component';

describe('EpuComponent', () => {
  let component: EpuComponent;
  let fixture: ComponentFixture<EpuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EpuComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EpuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
