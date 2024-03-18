package gr.aueb.cf.schoolapppro.repository;

import gr.aueb.cf.schoolapppro.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByCityStartingWith(String city);
    City findCityById(Long id);
}
