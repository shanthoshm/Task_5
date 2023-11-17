import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteAccountComponentComponent } from './delete-account-component.component';

describe('DeleteAccountComponentComponent', () => {
  let component: DeleteAccountComponentComponent;
  let fixture: ComponentFixture<DeleteAccountComponentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeleteAccountComponentComponent]
    });
    fixture = TestBed.createComponent(DeleteAccountComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
