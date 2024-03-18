package gr.aueb.cf.schoolapppro.validator;

import gr.aueb.cf.schoolapppro.dto.CityInsertDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CityInsertValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return CityInsertDTO.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        CityInsertDTO cityInsertDTO = (CityInsertDTO) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "empty");
        if (cityInsertDTO.getCity().length() < 3 || cityInsertDTO.getCity().length() > 50) {
            errors.reject("city", "size");
        }
    }
}
