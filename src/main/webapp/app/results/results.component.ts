import {Component, OnInit} from '@angular/core';
import {Principal} from '../shared';
import {User} from '../shared/user/user.model';
import {School} from '../entities/school';
import {Classroom} from '../entities/classroom';
import {MatDialog} from '@angular/material';
import {Results} from './results.model';
import {Services} from '../services';

@Component({
    selector: 'jhi-results',
    templateUrl: './results.component.html'
})
export class ResultsComponent implements OnInit {
    currentUser: any;

    schools: School[] = [];
    classrooms: Classroom[] = [];
    users: User[] = [];
    results: Results = new Results();

    schoolIndexSelected;
    classroomIndexSelected;
    userIndexSelected;

    constructor(
        private principal: Principal,
        private services: Services,
        public dialog: MatDialog,
    ) {}

    ngOnInit(): void {
        this.loadCurrentUser();
    }

    /**
     * Load Current User logged in
     */
    private loadCurrentUser(): void {
        this.principal.identity().then((currentUser) => {
            this.currentUser = currentUser;
            // As soon as we know current User
            // We loaded Schools
            this.getSchools(currentUser);
        });
    }

    private getSchools(account): void {
        this.services.getSchools(account.id)
            .subscribe((schools) => {
                this.schools = schools;
            }, (error) => {
                console.log(JSON.parse(error.body).message);
            });
    }

    public getClassroomsTeacher(): void {
        this.services.getClassrooms(this.currentUser.id, this.schoolIndexSelected)
            .subscribe((classrooms) => {
                this.reset();
                this.classrooms = classrooms;
            }, (error) => {
                console.log(JSON.parse(error.body).message);
            });
    }

    /**
     * Resets the selected indexes
     */
    private reset(): void {
        this.classroomIndexSelected = undefined;
        this.userIndexSelected = undefined;
    }

    public getStudentsByTeacher(): void {
        this.services.getStudents(this.currentUser.id, this.schoolIndexSelected, this.classroomIndexSelected)
            .subscribe((users) => {
                this.users = users;
            }, (error) => {
                console.log(JSON.parse(error.body).message);
            });
    }

    public getStudentByTeacher(): void {
        this.services.getResultsByStudent(this.userIndexSelected)
            .subscribe((results) => {
                this.results = results;
            }, (error) => {
                console.log(JSON.parse(error).message);
            });
    }

}
