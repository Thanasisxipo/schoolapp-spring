package gr.aueb.cf.schoolapppro.dto;

import gr.aueb.cf.schoolapppro.model.Teacher;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SpecialityUpdateDTO extends BaseDTO {

    private String speciality;
    private Set<Teacher> teachers;

    public SpecialityUpdateDTO(@NotNull Long id, String speciality, Set<Teacher> teachers) {
        this.setId(id);
        this.speciality = speciality;
        this.teachers = teachers;
    }
}
