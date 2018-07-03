INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('00000000000000', 'jhipster', 'config/liquibase/changelog/00000000000000_initial_schema.xml', '2018-06-26 02:39:46.420815', 1, 'EXECUTED', '7:a6235f40597a13436aa36c6d61db2269', 'createSequence sequenceName=hibernate_sequence', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('00000000000001', 'jhipster', 'config/liquibase/changelog/00000000000000_initial_schema.xml', '2018-06-26 02:39:47.288840', 2, 'EXECUTED', '7:f54b6b1951612f9533bb49e8102b4526', 'createTable tableName=jhi_user; createIndex indexName=idx_user_login, tableName=jhi_user; createIndex indexName=idx_user_email, tableName=jhi_user; createTable tableName=jhi_authority; createTable tableName=jhi_user_authority; addPrimaryKey tableN...', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180609180001-1', 'jhipster', 'config/liquibase/changelog/20180609180001_added_entity_Student.xml', '2018-06-26 02:39:47.410926', 3, 'EXECUTED', '7:893bbe9c59c238d1f7579c3abf6bf43f', 'createTable tableName=student', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180609180002-1', 'jhipster', 'config/liquibase/changelog/20180609180002_added_entity_Manager.xml', '2018-06-26 02:39:47.527427', 4, 'EXECUTED', '7:3f1c61459616413c1ab07a6853901159', 'createTable tableName=manager', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180609180003-1', 'jhipster', 'config/liquibase/changelog/20180609180003_added_entity_Teacher.xml', '2018-06-26 02:39:47.651717', 5, 'EXECUTED', '7:83dd2de0cf1aa957d3c55ee29effab65', 'createTable tableName=teacher', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180609180004-1', 'jhipster', 'config/liquibase/changelog/20180609180004_added_entity_Absence.xml', '2018-06-26 02:39:47.785928', 6, 'EXECUTED', '7:410f2d3a458f0c2f6f5101d871a2bc23', 'createTable tableName=absence; dropDefaultValue columnName=start_date, tableName=absence; dropDefaultValue columnName=end_date, tableName=absence; createTable tableName=absence_student; addPrimaryKey tableName=absence_student', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180609180005-1', 'jhipster', 'config/liquibase/changelog/20180609180005_added_entity_DelayStudent.xml', '2018-06-26 02:39:47.918224', 7, 'EXECUTED', '7:2ee84a83fb6cf3273aa9aef53c54b4fe', 'createTable tableName=delay_student; dropDefaultValue columnName=start_date, tableName=delay_student; dropDefaultValue columnName=end_date, tableName=delay_student; createTable tableName=delay_student_student; addPrimaryKey tableName=delay_student...', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180609180006-1', 'jhipster', 'config/liquibase/changelog/20180609180006_added_entity_Intervention.xml', '2018-06-26 02:39:48.050568', 8, 'EXECUTED', '7:54a96a3f4a487a9be7de983bf21dc6e5', 'createTable tableName=intervention; dropDefaultValue columnName=start_date, tableName=intervention; dropDefaultValue columnName=end_date, tableName=intervention; createTable tableName=intervention_teacher; addPrimaryKey tableName=intervention_teacher', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180609180007-1', 'jhipster', 'config/liquibase/changelog/20180609180007_added_entity_Module.xml', '2018-06-26 02:39:48.168136', 9, 'EXECUTED', '7:5abb2b0c5bc22835d6aa28ac9d03f7cc', 'createTable tableName=module', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180609180008-1', 'jhipster', 'config/liquibase/changelog/20180609180008_added_entity_Evaluation.xml', '2018-06-26 02:39:48.258976', 10, 'EXECUTED', '7:c9cd853a952cdd5c1348cc61d216b205', 'createTable tableName=evaluation; dropDefaultValue columnName=evaluation_date, tableName=evaluation', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180609180009-1', 'jhipster', 'config/liquibase/changelog/20180609180009_added_entity_SchoolReport.xml', '2018-06-26 02:39:48.375035', 11, 'EXECUTED', '7:76a7bdd281e58fbd29b57faae231360e', 'createTable tableName=school_report', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180609180010-1', 'jhipster', 'config/liquibase/changelog/20180609180010_added_entity_School.xml', '2018-06-26 02:39:48.483990', 12, 'EXECUTED', '7:502e61428b1cac3e58e090af6564498a', 'createTable tableName=school', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180609180011-1', 'jhipster', 'config/liquibase/changelog/20180609180011_added_entity_Classroom.xml', '2018-06-26 02:39:48.617226', 13, 'EXECUTED', '7:b5e24b0157856354843e7b073b3126df', 'createTable tableName=classroom', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180609180012-1', 'jhipster', 'config/liquibase/changelog/20180609180012_added_entity_SchoolYear.xml', '2018-06-26 02:39:48.717339', 14, 'EXECUTED', '7:663101b5dcbbd03b32fe68373decf46f', 'createTable tableName=school_year', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180609180013-1', 'jhipster', 'config/liquibase/changelog/20180609180013_added_entity_Document.xml', '2018-06-26 02:39:48.833942', 15, 'EXECUTED', '7:64975c87b8913239918b2e7a7c545dd4', 'createTable tableName=document', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180609180014-1', 'jhipster', 'config/liquibase/changelog/20180609180014_added_entity_AssignmentModule.xml', '2018-06-26 02:39:49.002555', 16, 'EXECUTED', '7:17dc5c7c8d783ae110a49c123aafd6d7', 'createTable tableName=assignment_module; createTable tableName=assignment_module_teacher; addPrimaryKey tableName=assignment_module_teacher; createTable tableName=assignment_module_module; addPrimaryKey tableName=assignment_module_module', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180609180015-1', 'jhipster', 'config/liquibase/changelog/20180609180015_added_entity_Inscription.xml', '2018-06-26 02:39:49.133537', 17, 'EXECUTED', '7:89678d7cff8131ccce34b042db970cb6', 'createTable tableName=inscription; createTable tableName=inscription_student; addPrimaryKey tableName=inscription_student', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180624003141-1', 'jhipster', 'config/liquibase/changelog/20180624003141_added_entity_YearPeriod.xml', '2018-06-26 02:39:49.217415', 18, 'EXECUTED', '7:77b5c8d99ce04a3c34a4b82add364aa5', 'createTable tableName=year_period', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180624034159-1', 'jhipster', 'config/liquibase/changelog/20180624034159_added_entity_AssignmentManager.xml', '2018-06-26 02:39:49.366487', 19, 'EXECUTED', '7:ae66495e38af96f81ed879c2095fed30', 'createTable tableName=assignment_manager; createTable tableName=assignment_manager_manager; addPrimaryKey tableName=assignment_manager_manager', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180624134201-1', 'jhipster', 'config/liquibase/changelog/20180624134201_added_entity_AssignmentYearPeriod.xml', '2018-06-26 02:39:49.558139', 20, 'EXECUTED', '7:5d3bc92f42c66e7072884a46d604d5fa', 'createTable tableName=assignment_year_period; createTable tableName=assignment_year_period_year_period; addPrimaryKey tableName=assignment_year_period_year_period; createTable tableName=assignment_year_period_classroom; addPrimaryKey tableName=ass...', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180609180001-2', 'jhipster', 'config/liquibase/changelog/20180609180001_added_entity_constraints_Student.xml', '2018-06-26 02:39:49.591974', 21, 'EXECUTED', '7:50f9def1ce475c90565ea791c7d13af9', 'addForeignKeyConstraint baseTableName=student, constraintName=fk_student_user_id, referencedTableName=jhi_user', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180609180002-2', 'jhipster', 'config/liquibase/changelog/20180609180002_added_entity_constraints_Manager.xml', '2018-06-26 02:39:49.624922', 22, 'EXECUTED', '7:e29201c63f4ebc698a68086c09c14fec', 'addForeignKeyConstraint baseTableName=manager, constraintName=fk_manager_user_id, referencedTableName=jhi_user', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180609180003-2', 'jhipster', 'config/liquibase/changelog/20180609180003_added_entity_constraints_Teacher.xml', '2018-06-26 02:39:49.667794', 23, 'EXECUTED', '7:5198f7631819015463847ea5c2613bea', 'addForeignKeyConstraint baseTableName=teacher, constraintName=fk_teacher_user_id, referencedTableName=jhi_user; addForeignKeyConstraint baseTableName=teacher, constraintName=fk_teacher_special_module_id, referencedTableName=module', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180609180004-2', 'jhipster', 'config/liquibase/changelog/20180609180004_added_entity_constraints_Absence.xml', '2018-06-26 02:39:49.717717', 24, 'EXECUTED', '7:76e77607d2a0c13fcea21ff3d0240a1a', 'addForeignKeyConstraint baseTableName=absence, constraintName=fk_absence_module_id, referencedTableName=module; addForeignKeyConstraint baseTableName=absence_student, constraintName=fk_absence_student_absences_id, referencedTableName=absence; addF...', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180609180005-2', 'jhipster', 'config/liquibase/changelog/20180609180005_added_entity_constraints_DelayStudent.xml', '2018-06-26 02:39:49.786086', 25, 'EXECUTED', '7:f63d175f2dacc85fe22bb2a0483535dc', 'addForeignKeyConstraint baseTableName=delay_student, constraintName=fk_delay_student_module_id, referencedTableName=module; addForeignKeyConstraint baseTableName=delay_student_student, constraintName=fk_delay_student_student_delay_students_id, ref...', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180609180006-2', 'jhipster', 'config/liquibase/changelog/20180609180006_added_entity_constraints_Intervention.xml', '2018-06-26 02:39:49.836520', 26, 'EXECUTED', '7:8283355582989fc6a3de59989977f634', 'addForeignKeyConstraint baseTableName=intervention, constraintName=fk_intervention_module_id, referencedTableName=module; addForeignKeyConstraint baseTableName=intervention_teacher, constraintName=fk_intervention_teacher_interventions_id, referenc...', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180609180008-2', 'jhipster', 'config/liquibase/changelog/20180609180008_added_entity_constraints_Evaluation.xml', '2018-06-26 02:39:49.892058', 27, 'EXECUTED', '7:72f3b921be5cf8d9c9c0ac05f702dcaf', 'addForeignKeyConstraint baseTableName=evaluation, constraintName=fk_evaluation_student_id, referencedTableName=student; addForeignKeyConstraint baseTableName=evaluation, constraintName=fk_evaluation_module_id, referencedTableName=module; addForeig...', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180609180013-2', 'jhipster', 'config/liquibase/changelog/20180609180013_added_entity_constraints_Document.xml', '2018-06-26 02:39:49.925471', 28, 'EXECUTED', '7:991e79aee04f8632eec97cc62c54cdfb', 'addForeignKeyConstraint baseTableName=document, constraintName=fk_document_student_id, referencedTableName=student', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180609180014-2', 'jhipster', 'config/liquibase/changelog/20180609180014_added_entity_constraints_AssignmentModule.xml', '2018-06-26 02:39:50.002548', 29, 'EXECUTED', '7:360e9980ec6bf274c956312cbab09155', 'addForeignKeyConstraint baseTableName=assignment_module, constraintName=fk_assignment_module_classroom_id, referencedTableName=classroom; addForeignKeyConstraint baseTableName=assignment_module, constraintName=fk_assignment_module_school_id, refer...', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180609180015-2', 'jhipster', 'config/liquibase/changelog/20180609180015_added_entity_constraints_Inscription.xml', '2018-06-26 02:39:50.060375', 30, 'EXECUTED', '7:17d813a74c5f3df2fad29385ef4a0ec6', 'addForeignKeyConstraint baseTableName=inscription, constraintName=fk_inscription_school_id, referencedTableName=school; addForeignKeyConstraint baseTableName=inscription, constraintName=fk_inscription_classroom_id, referencedTableName=classroom; a...', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180609180009-2', 'jhipster', 'config/liquibase/changelog/20180609180009_added_entity_constraints_SchoolReport.xml', '2018-06-26 02:39:50.110181', 31, 'EXECUTED', '7:f300b0a044c00592daf6a581187e768f', 'addForeignKeyConstraint baseTableName=school_report, constraintName=fk_school_report_year_period_id, referencedTableName=year_period; addForeignKeyConstraint baseTableName=school_report, constraintName=fk_school_report_student_id, referencedTableN...', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180624034159-2', 'jhipster', 'config/liquibase/changelog/20180624034159_added_entity_constraints_AssignmentManager.xml', '2018-06-26 02:39:50.168744', 32, 'EXECUTED', '7:3bb26e01b787c338816115ac8f27dae6', 'addForeignKeyConstraint baseTableName=assignment_manager, constraintName=fk_assignment_manager_school_year_id, referencedTableName=school_year; addForeignKeyConstraint baseTableName=assignment_manager, constraintName=fk_assignment_manager_school_i...', '', null, '3.5.3', null, null, '9973586218');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20180624134201-2', 'jhipster', 'config/liquibase/changelog/20180624134201_added_entity_constraints_AssignmentYearPeriod.xml', '2018-06-26 02:39:50.238228', 33, 'EXECUTED', '7:cd5d03e7f4cc0fdcef3f890ea5c3486e', 'addForeignKeyConstraint baseTableName=assignment_year_period, constraintName=fk_assignment_year_period_school_id, referencedTableName=school; addForeignKeyConstraint baseTableName=assignment_year_period_year_period, constraintName=fk_assignment_ye...', '', null, '3.5.3', null, null, '9973586218');