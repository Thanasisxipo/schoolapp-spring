package gr.aueb.cf.schoolapppro.service;

import gr.aueb.cf.schoolapppro.dto.StudentInsertDTO;
import gr.aueb.cf.schoolapppro.dto.StudentUpdateDTO;
import gr.aueb.cf.schoolapppro.mapper.Mapper;
import gr.aueb.cf.schoolapppro.model.Student;
import gr.aueb.cf.schoolapppro.repository.StudentRepository;
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
public class StudentServiceImpl implements IStudentService {

    private final StudentRepository studentRepository;

    @Transactional
    @Override
    public Student insertStudent(StudentInsertDTO dto) throws Exception {
        Student student = null;
        try {
            student = studentRepository.save(Mapper.mapToStudent(dto));
            if (student.getId() == null) {
                throw new Exception("Insert error");
            }
            log.info("Insert success for student with id " + student.getId());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
        return student;
    }

    @Transactional
    @Override
    public Student updateStudent(StudentUpdateDTO dto) throws EntityNotFoundException {
        Student student = null;
        Student updatedStudent = null;
        try {
            student = studentRepository.findStudentById(dto.getId());
            if (student == null ) {
                throw new EntityNotFoundException(Student.class, dto.getId());
            }
            updatedStudent = studentRepository.save(Mapper.mapToStudent(dto));
            log.info("Student with id " + updatedStudent.getId() + " was updated");
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return updatedStudent;
    }

    @Transactional
    @Override
    public Student deleteStudent(Long id) throws EntityNotFoundException {
        Student student = null;
        try {
            student = studentRepository.findStudentById(id);
            if (student == null ) {
                throw new EntityNotFoundException(Student.class, id);
            }
            studentRepository.deleteById(id);
            log.info("Student with id " + student.getId() + " was deleted");
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return student;
    }

    @Transactional
    @Override
    public List<Student> getStudentByLastname(String lastname) throws EntityNotFoundException {
        List<Student> students = new ArrayList<>();
        try {
            students = studentRepository.findByLastnameStartingWith(lastname);
            if (students.isEmpty()) {
                throw new EntityNotFoundException(Student.class, 0L);
            }
            log.info("Students with lastname starting with " + lastname + " were found");
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return students;
    }

    @Transactional
    @Override
    public Student getById(Long id) throws EntityNotFoundException {
        Student student = null;
        try {
            student = studentRepository.findStudentById(id);
            if (student == null ) {
                throw new EntityNotFoundException(Student.class, id);
            }
            log.info("Student with id  " + id + " was found");
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return student;
    }
}
