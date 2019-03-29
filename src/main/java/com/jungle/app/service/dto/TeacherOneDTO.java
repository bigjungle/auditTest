package com.jungle.app.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TeacherOne entity.
 */
public class TeacherOneDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TeacherOneDTO teacherOneDTO = (TeacherOneDTO) o;
        if (teacherOneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), teacherOneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TeacherOneDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
