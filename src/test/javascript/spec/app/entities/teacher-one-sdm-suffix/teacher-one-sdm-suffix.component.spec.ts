/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { TestauditTestModule } from '../../../test.module';
import { TeacherOneSdmSuffixComponent } from 'app/entities/teacher-one-sdm-suffix/teacher-one-sdm-suffix.component';
import { TeacherOneSdmSuffixService } from 'app/entities/teacher-one-sdm-suffix/teacher-one-sdm-suffix.service';
import { TeacherOneSdmSuffix } from 'app/shared/model/teacher-one-sdm-suffix.model';

describe('Component Tests', () => {
    describe('TeacherOneSdmSuffix Management Component', () => {
        let comp: TeacherOneSdmSuffixComponent;
        let fixture: ComponentFixture<TeacherOneSdmSuffixComponent>;
        let service: TeacherOneSdmSuffixService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TestauditTestModule],
                declarations: [TeacherOneSdmSuffixComponent],
                providers: [
                    {
                        provide: ActivatedRoute,
                        useValue: {
                            data: {
                                subscribe: (fn: (value: Data) => void) =>
                                    fn({
                                        pagingParams: {
                                            predicate: 'id',
                                            reverse: false,
                                            page: 0
                                        }
                                    })
                            }
                        }
                    }
                ]
            })
                .overrideTemplate(TeacherOneSdmSuffixComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TeacherOneSdmSuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TeacherOneSdmSuffixService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TeacherOneSdmSuffix(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.teacherOnes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });

        it('should load a page', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TeacherOneSdmSuffix(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.loadPage(1);

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.teacherOnes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });

        it('should re-initialize the page', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TeacherOneSdmSuffix(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.loadPage(1);
            comp.reset();

            // THEN
            expect(comp.page).toEqual(0);
            expect(service.query).toHaveBeenCalledTimes(2);
            expect(comp.teacherOnes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
        it('should calculate the sort attribute for an id', () => {
            // WHEN
            const result = comp.sort();

            // THEN
            expect(result).toEqual(['id,asc']);
        });

        it('should calculate the sort attribute for a non-id attribute', () => {
            // GIVEN
            comp.predicate = 'name';

            // WHEN
            const result = comp.sort();

            // THEN
            expect(result).toEqual(['name,asc', 'id']);
        });
    });
});
