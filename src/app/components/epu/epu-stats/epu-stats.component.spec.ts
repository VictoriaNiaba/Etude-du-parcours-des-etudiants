import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EpuStatsComponent } from './epu-stats.component';

describe('StatsComponent', () => {
  let component: EpuStatsComponent;
  let fixture: ComponentFixture<EpuStatsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EpuStatsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EpuStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
