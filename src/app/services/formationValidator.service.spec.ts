import { TestBed } from '@angular/core/testing';

import { FormationValidatorService } from './formationValidator.service';

describe('FormationService', () => {
  let service: FormationValidatorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FormationValidatorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
