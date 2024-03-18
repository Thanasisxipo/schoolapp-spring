package gr.aueb.cf.schoolapppro.dto;

import gr.aueb.cf.schoolapppro.model.Student;
import gr.aueb.cf.schoolapppro.model.Teacher;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserInsertDTO {

    private String username;
    private String password;
    private Teacher teacher;
    private Student student;
}
