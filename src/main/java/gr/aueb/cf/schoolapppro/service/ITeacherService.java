package gr.aueb.cf.schoolapppro.service;

import gr.aueb.cf.schoolapppro.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapppro.dto.TeacherUpdateDTO;
import gr.aueb.cf.schoolapppro.model.Teacher;
import gr.aueb.cf.schoolapppro.service.exceptions.EntityNotFoundException;

import java.util.List;

public interface ITeacherService {
    Teacher insertTeacher(TeacherInsertDTO dto) throws Exception;
    Teacher updateTeacher(TeacherUpdateDTO dto) throws EntityNotFoundException;
    Teacher  deleteTeacher(Long id) throws EntityNotFoundException;
    List<Teacher> getTeacherByLastname(String lastname) throws EntityNotFoundException;
    Teacher getTeacherById(Long id) throws EntityNotFoundException;
}
