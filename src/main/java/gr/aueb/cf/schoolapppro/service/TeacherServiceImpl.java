package gr.aueb.cf.schoolapppro.service;

import gr.aueb.cf.schoolapppro.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapppro.dto.TeacherUpdateDTO;
import gr.aueb.cf.schoolapppro.mapper.Mapper;
import gr.aueb.cf.schoolapppro.model.Teacher;
import gr.aueb.cf.schoolapppro.repository.TeacherRepository;
import gr.aueb.cf.schoolapppro.service.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeacherServiceImpl implements ITeacherService {

    private final TeacherRepository teacherRepository;

    @Transactional
    @Override
    public Teacher insertTeacher(TeacherInsertDTO dto) throws Exception {
        Teacher teacher = null;
        try {
            teacher = teacherRepository.save(Mapper.mapToTeacher(dto));
            if (teacher.getId() == null) {
                throw new Exception("Insert error");
            }
            log.info("Insert success for teacher with id " + teacher.getId());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
        return teacher;
    }

    @Transactional
    @Override
    public Teacher updateTeacher(TeacherUpdateDTO dto) throws EntityNotFoundException {
        Teacher teacher = null;
        Teacher updatedTeacher = null;
        try {
            teacher = teacherRepository.findTeacherById(dto.getId());
            if (teacher == null ) {
                throw new EntityNotFoundException(Teacher.class, dto.getId());
            }
            updatedTeacher = teacherRepository.save(Mapper.mapToTeacher(dto));
            log.info("Teacher with id " + updatedTeacher.getId() + " was updated");
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return updatedTeacher;
    }

    @Transactional
    @Override
    public Teacher deleteTeacher(Long id) throws EntityNotFoundException {
        Teacher teacher = null;
        try {
            teacher = teacherRepository.findTeacherById(id);
            if (teacher == null ) {
                throw new EntityNotFoundException(Teacher.class, id);
            }
            teacherRepository.deleteById(id);
            log.info("Teacher with id " + teacher.getId() + " was deleted");
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return teacher;
    }

    @Transactional
    @Override
    public List<Teacher> getTeacherByLastname(String lastname) throws EntityNotFoundException {
        List<Teacher> teachers = new ArrayList<>();
        try {
            teachers = teacherRepository.findByLastnameStartingWith(lastname);
            if (teachers.isEmpty()) {
                throw new EntityNotFoundException(Teacher.class, 0L);
            }
            log.info("Teachers with lastname starting with " + lastname + " were found");
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return teachers;
    }

    @Transactional
    @Override
    public Teacher getTeacherById(Long id) throws EntityNotFoundException {
        Teacher teacher = null;
        try {
            teacher = teacherRepository.findTeacherById(id);
            if (teacher == null ) {
                throw new EntityNotFoundException(Teacher.class, id);
            }
            log.info("Teacher with id  " + id + " was found");
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return teacher;
    }

}
