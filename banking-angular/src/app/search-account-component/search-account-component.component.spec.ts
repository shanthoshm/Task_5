import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchAccountComponentComponent } from './search-account-component.component';

describe('SearchAccountComponentComponent', () => {
  let component: SearchAccountComponentComponent;
  let fixture: ComponentFixture<SearchAccountComponentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SearchAccountComponentComponent]
    });
    fixture = TestBed.createComponent(SearchAccountComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
