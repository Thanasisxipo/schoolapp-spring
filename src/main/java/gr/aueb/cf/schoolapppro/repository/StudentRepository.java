package gr.aueb.cf.schoolapppro.repository;

import gr.aueb.cf.schoolapppro.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByLastnameStartingWith(String lastname);
    Student findStudentById(Long id);
}
