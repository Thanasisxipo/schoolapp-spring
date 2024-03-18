package gr.aueb.cf.schoolapppro.validator;

import gr.aueb.cf.schoolapppro.dto.SpecialityInsertDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SpecialityInsertValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return SpecialityInsertDTO.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        SpecialityInsertDTO specialityInsertDTO = (SpecialityInsertDTO) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "speciality", "empty");
        if (specialityInsertDTO.getSpeciality().length() < 5 || specialityInsertDTO.getSpeciality().length() > 50) {
            errors.reject("speciality", "size");
        }
    }
}
