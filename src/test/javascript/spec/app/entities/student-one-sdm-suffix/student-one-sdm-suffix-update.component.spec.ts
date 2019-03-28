/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TestauditTestModule } from '../../../test.module';
import { StudentOneSdmSuffixUpdateComponent } from 'app/entities/student-one-sdm-suffix/student-one-sdm-suffix-update.component';
import { StudentOneSdmSuffixService } from 'app/entities/student-one-sdm-suffix/student-one-sdm-suffix.service';
import { StudentOneSdmSuffix } from 'app/shared/model/student-one-sdm-suffix.model';

describe('Component Tests', () => {
    describe('StudentOneSdmSuffix Management Update Component', () => {
        let comp: StudentOneSdmSuffixUpdateComponent;
        let fixture: ComponentFixture<StudentOneSdmSuffixUpdateComponent>;
        let service: StudentOneSdmSuffixService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TestauditTestModule],
                declarations: [StudentOneSdmSuffixUpdateComponent]
            })
                .overrideTemplate(StudentOneSdmSuffixUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StudentOneSdmSuffixUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentOneSdmSuffixService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new StudentOneSdmSuffix(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.studentOne = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new StudentOneSdmSuffix();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.studentOne = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
