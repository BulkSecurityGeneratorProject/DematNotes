import { BaseEntity } from './../../shared';

export class SchoolMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public wording?: string,
        public phoneNumber?: string,
        public address?: string,
        public city?: string,
        public postalCode?: string,
        public schoolLevel?: string,
    ) {
    }
}
