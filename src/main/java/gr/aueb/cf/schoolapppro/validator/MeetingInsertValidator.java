package gr.aueb.cf.schoolapppro.validator;

import gr.aueb.cf.schoolapppro.dto.MeetingInsertDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MeetingInsertValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return MeetingInsertDTO.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeetingInsertDTO meetingInsertDTO = (MeetingInsertDTO) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "meetingRoom", "empty");
    }
}
