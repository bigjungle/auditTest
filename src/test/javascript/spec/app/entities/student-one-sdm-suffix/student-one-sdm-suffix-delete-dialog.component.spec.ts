/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TestauditTestModule } from '../../../test.module';
import { StudentOneSdmSuffixDeleteDialogComponent } from 'app/entities/student-one-sdm-suffix/student-one-sdm-suffix-delete-dialog.component';
import { StudentOneSdmSuffixService } from 'app/entities/student-one-sdm-suffix/student-one-sdm-suffix.service';

describe('Component Tests', () => {
    describe('StudentOneSdmSuffix Management Delete Component', () => {
        let comp: StudentOneSdmSuffixDeleteDialogComponent;
        let fixture: ComponentFixture<StudentOneSdmSuffixDeleteDialogComponent>;
        let service: StudentOneSdmSuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TestauditTestModule],
                declarations: [StudentOneSdmSuffixDeleteDialogComponent]
            })
                .overrideTemplate(StudentOneSdmSuffixDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentOneSdmSuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentOneSdmSuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
