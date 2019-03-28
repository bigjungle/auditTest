import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITeacherOneSdmSuffix } from 'app/shared/model/teacher-one-sdm-suffix.model';

@Component({
    selector: 'jhi-teacher-one-sdm-suffix-detail',
    templateUrl: './teacher-one-sdm-suffix-detail.component.html'
})
export class TeacherOneSdmSuffixDetailComponent implements OnInit {
    teacherOne: ITeacherOneSdmSuffix;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ teacherOne }) => {
            this.teacherOne = teacherOne;
        });
    }

    previousState() {
        window.history.back();
    }
}
