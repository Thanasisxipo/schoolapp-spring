package gr.aueb.cf.schoolapppro.dto;

import gr.aueb.cf.schoolapppro.model.City;
import gr.aueb.cf.schoolapppro.model.Meeting;
import gr.aueb.cf.schoolapppro.model.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentInsertDTO {

    private String firstname;
    private String lastname;
    private String gender;
    private Date birthDate;
    private City city;
    private User user;
    private Set<Meeting> meetings;
}
