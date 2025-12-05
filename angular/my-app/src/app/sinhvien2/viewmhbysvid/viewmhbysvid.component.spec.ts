import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewmhbysvidComponent } from './viewmhbysvid.component';

describe('ViewmhbysvidComponent', () => {
  let component: ViewmhbysvidComponent;
  let fixture: ComponentFixture<ViewmhbysvidComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViewmhbysvidComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ViewmhbysvidComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
