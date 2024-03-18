package gr.aueb.cf.schoolapppro.validator;

import gr.aueb.cf.schoolapppro.dto.MeetingInsertDTO;
import gr.aueb.cf.schoolapppro.dto.MeetingUpdateDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MeetingUpdateValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return MeetingUpdateDTO.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeetingUpdateDTO meetingUpdateDTO = (MeetingUpdateDTO) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "meetingRoom", "empty");
    }
}
