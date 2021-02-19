import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EpuGrapheComponent } from './epu-graphe.component';

describe('GrapheComponent', () => {
  let component: EpuGrapheComponent;
  let fixture: ComponentFixture<EpuGrapheComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EpuGrapheComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EpuGrapheComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
