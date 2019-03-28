export interface IStudentOneSdmSuffix {
    id?: number;
    name?: string;
}

export class StudentOneSdmSuffix implements IStudentOneSdmSuffix {
    constructor(public id?: number, public name?: string) {}
}
