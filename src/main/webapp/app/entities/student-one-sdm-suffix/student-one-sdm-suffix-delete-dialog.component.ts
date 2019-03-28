import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStudentOneSdmSuffix } from 'app/shared/model/student-one-sdm-suffix.model';
import { StudentOneSdmSuffixService } from './student-one-sdm-suffix.service';

@Component({
    selector: 'jhi-student-one-sdm-suffix-delete-dialog',
    templateUrl: './student-one-sdm-suffix-delete-dialog.component.html'
})
export class StudentOneSdmSuffixDeleteDialogComponent {
    studentOne: IStudentOneSdmSuffix;

    constructor(
        protected studentOneService: StudentOneSdmSuffixService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.studentOneService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'studentOneListModification',
                content: 'Deleted an studentOne'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-student-one-sdm-suffix-delete-popup',
    template: ''
})
export class StudentOneSdmSuffixDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentOne }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StudentOneSdmSuffixDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.studentOne = studentOne;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/student-one-sdm-suffix', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/student-one-sdm-suffix', { outlets: { popup: null } }]);
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
