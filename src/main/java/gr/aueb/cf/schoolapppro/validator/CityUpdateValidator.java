package gr.aueb.cf.schoolapppro.validator;

import gr.aueb.cf.schoolapppro.dto.CityUpdateDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CityUpdateValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return CityUpdateDTO.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        CityUpdateDTO cityUpdateDTO = (CityUpdateDTO) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "empty");
        if (cityUpdateDTO.getCity().length() < 3 || cityUpdateDTO.getCity().length() > 50) {
            errors.reject("city", "size");
        }
    }
}
