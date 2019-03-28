/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TestauditTestModule } from '../../../test.module';
import { TeacherOneSdmSuffixUpdateComponent } from 'app/entities/teacher-one-sdm-suffix/teacher-one-sdm-suffix-update.component';
import { TeacherOneSdmSuffixService } from 'app/entities/teacher-one-sdm-suffix/teacher-one-sdm-suffix.service';
import { TeacherOneSdmSuffix } from 'app/shared/model/teacher-one-sdm-suffix.model';

describe('Component Tests', () => {
    describe('TeacherOneSdmSuffix Management Update Component', () => {
        let comp: TeacherOneSdmSuffixUpdateComponent;
        let fixture: ComponentFixture<TeacherOneSdmSuffixUpdateComponent>;
        let service: TeacherOneSdmSuffixService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TestauditTestModule],
                declarations: [TeacherOneSdmSuffixUpdateComponent]
            })
                .overrideTemplate(TeacherOneSdmSuffixUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TeacherOneSdmSuffixUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TeacherOneSdmSuffixService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TeacherOneSdmSuffix(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.teacherOne = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TeacherOneSdmSuffix();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.teacherOne = entity;
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
