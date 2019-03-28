export interface ITeacherOneSdmSuffix {
    id?: number;
    name?: string;
}

export class TeacherOneSdmSuffix implements ITeacherOneSdmSuffix {
    constructor(public id?: number, public name?: string) {}
}
