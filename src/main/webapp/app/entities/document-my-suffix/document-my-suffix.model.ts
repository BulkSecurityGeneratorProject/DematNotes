import { BaseEntity } from './../../shared';

export class DocumentMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public entitled?: string,
        public type?: string,
        public visible?: boolean,
        public url?: string,
        public studentId?: number,
    ) {
        this.visible = false;
    }
}
