package org.csid.repository;

import org.csid.domain.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;


/**
 * Spring Data JPA repository for the Evaluation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    @Query("select evaluation from Evaluation evaluation where evaluation.student.id=:idStudent and evaluation.evaluationDate>=:dateTimePeriod")
    List<Evaluation> getEvaluationByStudentAndPeriod(@Param("idStudent") final Long idStudent, @Param("dateTimePeriod") final ZonedDateTime dateTimePeriod);

}
