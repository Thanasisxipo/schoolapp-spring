package gr.aueb.cf.schoolapppro.dto;

import gr.aueb.cf.schoolapppro.model.Student;
import gr.aueb.cf.schoolapppro.model.Teacher;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MeetingUpdateDTO extends BaseDTO {

    private String meetingRoom;
    private Date meetingDate;
    private Teacher teacher;
    private Student student;

    public MeetingUpdateDTO(@NotNull Long id, String meetingRoom, Date meetingDate, Teacher teacher, Student student) {
        this.setId(id);
        this.meetingRoom = meetingRoom;
        this.meetingDate = meetingDate;
        this.teacher = teacher;
        this.student = student;
    }
}
