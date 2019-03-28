import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITeacherOneSdmSuffix } from 'app/shared/model/teacher-one-sdm-suffix.model';
import { TeacherOneSdmSuffixService } from './teacher-one-sdm-suffix.service';

@Component({
    selector: 'jhi-teacher-one-sdm-suffix-delete-dialog',
    templateUrl: './teacher-one-sdm-suffix-delete-dialog.component.html'
})
export class TeacherOneSdmSuffixDeleteDialogComponent {
    teacherOne: ITeacherOneSdmSuffix;

    constructor(
        protected teacherOneService: TeacherOneSdmSuffixService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.teacherOneService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'teacherOneListModification',
                content: 'Deleted an teacherOne'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-teacher-one-sdm-suffix-delete-popup',
    template: ''
})
export class TeacherOneSdmSuffixDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ teacherOne }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TeacherOneSdmSuffixDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.teacherOne = teacherOne;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/teacher-one-sdm-suffix', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/teacher-one-sdm-suffix', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
