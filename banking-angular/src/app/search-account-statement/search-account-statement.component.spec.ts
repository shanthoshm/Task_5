import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchAccountStatementComponent } from './search-account-statement.component';

describe('SearchAccountStatementComponent', () => {
  let component: SearchAccountStatementComponent;
  let fixture: ComponentFixture<SearchAccountStatementComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SearchAccountStatementComponent]
    });
    fixture = TestBed.createComponent(SearchAccountStatementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
