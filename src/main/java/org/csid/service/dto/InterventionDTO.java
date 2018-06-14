package org.csid.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Intervention entity.
 */
public class InterventionDTO implements Serializable {

    private Long id;

    @NotNull
    private String type;

    @NotNull
    private String yearPeriod;

    @NotNull
    private ZonedDateTime startDate;

    @NotNull
    private ZonedDateTime endDate;

    private Long moduleId;

    private Set<TeacherDTO> teachers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYearPeriod() {
        return yearPeriod;
    }

    public void setYearPeriod(String yearPeriod) {
        this.yearPeriod = yearPeriod;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Set<TeacherDTO> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<TeacherDTO> teachers) {
        this.teachers = teachers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InterventionDTO interventionDTO = (InterventionDTO) o;
        if(interventionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), interventionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InterventionDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", yearPeriod='" + getYearPeriod() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
