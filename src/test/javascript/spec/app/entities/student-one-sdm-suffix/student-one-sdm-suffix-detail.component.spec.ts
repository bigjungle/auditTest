/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestauditTestModule } from '../../../test.module';
import { StudentOneSdmSuffixDetailComponent } from 'app/entities/student-one-sdm-suffix/student-one-sdm-suffix-detail.component';
import { StudentOneSdmSuffix } from 'app/shared/model/student-one-sdm-suffix.model';

describe('Component Tests', () => {
    describe('StudentOneSdmSuffix Management Detail Component', () => {
        let comp: StudentOneSdmSuffixDetailComponent;
        let fixture: ComponentFixture<StudentOneSdmSuffixDetailComponent>;
        const route = ({ data: of({ studentOne: new StudentOneSdmSuffix(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TestauditTestModule],
                declarations: [StudentOneSdmSuffixDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StudentOneSdmSuffixDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentOneSdmSuffixDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.studentOne).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
