package gr.aueb.cf.schoolapppro.repository;

import gr.aueb.cf.schoolapppro.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findByLastnameStartingWith(String lastname);
    Teacher findTeacherById(Long id);
}
