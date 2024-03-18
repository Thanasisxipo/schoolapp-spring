package gr.aueb.cf.schoolapppro.validator;

import gr.aueb.cf.schoolapppro.dto.TeacherInsertDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class TeacherInsertValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return TeacherInsertDTO.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        TeacherInsertDTO teacherInsertDTO = (TeacherInsertDTO) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "empty");
        if (teacherInsertDTO.getFirstname().length() < 3 || teacherInsertDTO.getFirstname().length() > 50 ) {
            errors.reject("firstname", "size");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "empty");
        if (teacherInsertDTO.getLastname().length() < 3 || teacherInsertDTO.getLastname().length() > 50 ) {
            errors.reject("lastname", "size");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ssn", "empty");
        if (teacherInsertDTO.getSsn().length() != 9 ) {
            errors.reject("ssn", "size");
        }
    }
}
