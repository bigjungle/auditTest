import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ITeacherOneSdmSuffix } from 'app/shared/model/teacher-one-sdm-suffix.model';
import { TeacherOneSdmSuffixService } from './teacher-one-sdm-suffix.service';

@Component({
    selector: 'jhi-teacher-one-sdm-suffix-update',
    templateUrl: './teacher-one-sdm-suffix-update.component.html'
})
export class TeacherOneSdmSuffixUpdateComponent implements OnInit {
    teacherOne: ITeacherOneSdmSuffix;
    isSaving: boolean;

    constructor(protected teacherOneService: TeacherOneSdmSuffixService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ teacherOne }) => {
            this.teacherOne = teacherOne;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.teacherOne.id !== undefined) {
            this.subscribeToSaveResponse(this.teacherOneService.update(this.teacherOne));
        } else {
            this.subscribeToSaveResponse(this.teacherOneService.create(this.teacherOne));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITeacherOneSdmSuffix>>) {
        result.subscribe((res: HttpResponse<ITeacherOneSdmSuffix>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
