import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStudentOneSdmSuffix } from 'app/shared/model/student-one-sdm-suffix.model';

type EntityResponseType = HttpResponse<IStudentOneSdmSuffix>;
type EntityArrayResponseType = HttpResponse<IStudentOneSdmSuffix[]>;

@Injectable({ providedIn: 'root' })
export class StudentOneSdmSuffixService {
    public resourceUrl = SERVER_API_URL + 'api/student-ones';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/student-ones';

    constructor(protected http: HttpClient) {}

    create(studentOne: IStudentOneSdmSuffix): Observable<EntityResponseType> {
        return this.http.post<IStudentOneSdmSuffix>(this.resourceUrl, studentOne, { observe: 'response' });
    }

    update(studentOne: IStudentOneSdmSuffix): Observable<EntityResponseType> {
        return this.http.put<IStudentOneSdmSuffix>(this.resourceUrl, studentOne, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IStudentOneSdmSuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStudentOneSdmSuffix[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStudentOneSdmSuffix[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
