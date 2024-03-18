package gr.aueb.cf.schoolapppro.repository;

import gr.aueb.cf.schoolapppro.model.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecialityRepository extends JpaRepository<Speciality, Long> {
    List<Speciality> findBySpecialityStartingWith(String speciality);
    Speciality findSpecialityById(Long id);
}
