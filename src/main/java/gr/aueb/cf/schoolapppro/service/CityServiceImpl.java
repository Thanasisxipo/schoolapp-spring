package gr.aueb.cf.schoolapppro.service;

import gr.aueb.cf.schoolapppro.dto.CityInsertDTO;
import gr.aueb.cf.schoolapppro.dto.CityUpdateDTO;
import gr.aueb.cf.schoolapppro.mapper.Mapper;
import gr.aueb.cf.schoolapppro.model.City;
import gr.aueb.cf.schoolapppro.repository.CityRepository;
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
public class CityServiceImpl implements ICityService {

    private final CityRepository cityRepository;

    @Transactional
    @Override
    public City insertCity(CityInsertDTO dto) throws Exception {
        City city = null;
        try {
            city = cityRepository.save(Mapper.mapToCity(dto));
            if (city.getId() == null) {
                throw new Exception("Insert error");
            }
            log.info("Insert success for city with id " + city.getId());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
        return city;
    }

    @Transactional
    @Override
    public City updateCity(CityUpdateDTO dto) throws EntityNotFoundException {
        City city = null;
        City updatedCity = null;
        try {
            city = cityRepository.findCityById(dto.getId());
            if (city == null ) {
                throw new EntityNotFoundException(City.class, dto.getId());
            }
            updatedCity = cityRepository.save(Mapper.mapToCity(dto));
            log.info("City with id " + updatedCity.getId() + " was updated");
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return updatedCity;
    }

    @Transactional
    @Override
    public City deleteCity(Long id) throws EntityNotFoundException {
        City city = null;
        try {
            city = cityRepository.findCityById(id);
            if (city == null ) {
                throw new EntityNotFoundException(City.class, id);
            }
            cityRepository.deleteById(id);
            log.info("City with id " + city.getId() + " was deleted");
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return city;
    }

    @Transactional
    @Override
    public List<City> getCityByCity(String city) throws EntityNotFoundException {
        List<City> cities = new ArrayList<>();
        try {
            cities = cityRepository.findByCityStartingWith(city);
            if (cities.isEmpty()) {
                throw new EntityNotFoundException(City.class, 0L);
            }
            log.info("Cities with lastname starting with " + city + " were found");
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return cities;
    }

    @Transactional
    @Override
    public City getCityById(Long id) throws EntityNotFoundException {
        City city = null;
        try {
            city = cityRepository.findCityById(id);
            if (city == null ) {
                throw new EntityNotFoundException(City.class, id);
            }
            log.info("City with id  " + id + " was found");
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return city;
    }
}
