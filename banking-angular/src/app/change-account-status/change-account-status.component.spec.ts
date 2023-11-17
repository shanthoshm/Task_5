import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangeAccountStatusComponent } from './change-account-status.component';

describe('ChangeAccountStatusComponent', () => {
  let component: ChangeAccountStatusComponent;
  let fixture: ComponentFixture<ChangeAccountStatusComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ChangeAccountStatusComponent]
    });
    fixture = TestBed.createComponent(ChangeAccountStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
