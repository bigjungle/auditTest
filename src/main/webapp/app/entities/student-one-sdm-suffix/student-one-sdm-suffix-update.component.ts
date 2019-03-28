import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IStudentOneSdmSuffix } from 'app/shared/model/student-one-sdm-suffix.model';
import { StudentOneSdmSuffixService } from './student-one-sdm-suffix.service';

@Component({
    selector: 'jhi-student-one-sdm-suffix-update',
    templateUrl: './student-one-sdm-suffix-update.component.html'
})
export class StudentOneSdmSuffixUpdateComponent implements OnInit {
    studentOne: IStudentOneSdmSuffix;
    isSaving: boolean;

    constructor(protected studentOneService: StudentOneSdmSuffixService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ studentOne }) => {
            this.studentOne = studentOne;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.studentOne.id !== undefined) {
            this.subscribeToSaveResponse(this.studentOneService.update(this.studentOne));
        } else {
            this.subscribeToSaveResponse(this.studentOneService.create(this.studentOne));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudentOneSdmSuffix>>) {
        result.subscribe((res: HttpResponse<IStudentOneSdmSuffix>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
