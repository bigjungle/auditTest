/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TestauditTestModule } from '../../../test.module';
import { TeacherOneSdmSuffixDeleteDialogComponent } from 'app/entities/teacher-one-sdm-suffix/teacher-one-sdm-suffix-delete-dialog.component';
import { TeacherOneSdmSuffixService } from 'app/entities/teacher-one-sdm-suffix/teacher-one-sdm-suffix.service';

describe('Component Tests', () => {
    describe('TeacherOneSdmSuffix Management Delete Component', () => {
        let comp: TeacherOneSdmSuffixDeleteDialogComponent;
        let fixture: ComponentFixture<TeacherOneSdmSuffixDeleteDialogComponent>;
        let service: TeacherOneSdmSuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TestauditTestModule],
                declarations: [TeacherOneSdmSuffixDeleteDialogComponent]
            })
                .overrideTemplate(TeacherOneSdmSuffixDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TeacherOneSdmSuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TeacherOneSdmSuffixService);
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
