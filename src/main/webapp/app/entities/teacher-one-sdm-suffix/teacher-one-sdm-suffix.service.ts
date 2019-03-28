import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITeacherOneSdmSuffix } from 'app/shared/model/teacher-one-sdm-suffix.model';

type EntityResponseType = HttpResponse<ITeacherOneSdmSuffix>;
type EntityArrayResponseType = HttpResponse<ITeacherOneSdmSuffix[]>;

@Injectable({ providedIn: 'root' })
export class TeacherOneSdmSuffixService {
    public resourceUrl = SERVER_API_URL + 'api/teacher-ones';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/teacher-ones';

    constructor(protected http: HttpClient) {}

    create(teacherOne: ITeacherOneSdmSuffix): Observable<EntityResponseType> {
        return this.http.post<ITeacherOneSdmSuffix>(this.resourceUrl, teacherOne, { observe: 'response' });
    }

    update(teacherOne: ITeacherOneSdmSuffix): Observable<EntityResponseType> {
        return this.http.put<ITeacherOneSdmSuffix>(this.resourceUrl, teacherOne, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITeacherOneSdmSuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITeacherOneSdmSuffix[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITeacherOneSdmSuffix[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
