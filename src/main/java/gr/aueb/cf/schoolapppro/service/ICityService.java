package gr.aueb.cf.schoolapppro.service;

import gr.aueb.cf.schoolapppro.dto.CityInsertDTO;
import gr.aueb.cf.schoolapppro.dto.CityUpdateDTO;
import gr.aueb.cf.schoolapppro.model.City;
import gr.aueb.cf.schoolapppro.service.exceptions.EntityNotFoundException;

import java.util.List;

public interface ICityService {
    City insertCity(CityInsertDTO dto) throws Exception;
    City updateCity(CityUpdateDTO dto) throws EntityNotFoundException;
    City deleteCity(Long id) throws EntityNotFoundException;
    List<City> getCityByCity(String city) throws EntityNotFoundException;
    City getCityById(Long id) throws EntityNotFoundException;
}
