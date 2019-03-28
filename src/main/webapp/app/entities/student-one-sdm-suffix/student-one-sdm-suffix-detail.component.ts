import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStudentOneSdmSuffix } from 'app/shared/model/student-one-sdm-suffix.model';

@Component({
    selector: 'jhi-student-one-sdm-suffix-detail',
    templateUrl: './student-one-sdm-suffix-detail.component.html'
})
export class StudentOneSdmSuffixDetailComponent implements OnInit {
    studentOne: IStudentOneSdmSuffix;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentOne }) => {
            this.studentOne = studentOne;
        });
    }

    previousState() {
        window.history.back();
    }
}
