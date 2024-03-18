package gr.aueb.cf.schoolapppro.service;

import gr.aueb.cf.schoolapppro.dto.SpecialityInsertDTO;
import gr.aueb.cf.schoolapppro.dto.SpecialityUpdateDTO;
import gr.aueb.cf.schoolapppro.mapper.Mapper;
import gr.aueb.cf.schoolapppro.model.Speciality;
import gr.aueb.cf.schoolapppro.repository.SpecialityRepository;
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
public class SpecialityServiceImpl implements ISpecialityService {

    private final SpecialityRepository specialityRepository;

    @Transactional
    @Override
    public Speciality insertSpeciality(SpecialityInsertDTO dto) throws Exception {
        Speciality speciality = null;
        try {
            speciality = specialityRepository.save(Mapper.mapToSpeciality(dto));
            if (speciality.getId() == null) {
                throw new Exception("Insert error");
            }
            log.info("Insert success for speciality with id " + speciality.getId());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
        return speciality;
    }

    @Transactional
    @Override
    public Speciality updateSpeciality(SpecialityUpdateDTO dto) throws EntityNotFoundException {
        Speciality speciality = null;
        Speciality updatedSpeciality = null;
        try {
            speciality = specialityRepository.findSpecialityById(dto.getId());
            if (speciality == null ) {
                throw new EntityNotFoundException(Speciality.class, dto.getId());
            }
            updatedSpeciality = specialityRepository.save(Mapper.mapToSpeciality(dto));
            log.info("Speciality with id " + updatedSpeciality.getId() + " was updated");
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return updatedSpeciality;
    }

    @Transactional
    @Override
    public Speciality deleteSpeciality(Long id) throws EntityNotFoundException {
        Speciality speciality = null;
        try {
            speciality = specialityRepository.findSpecialityById(id);
            if (speciality == null ) {
                throw new EntityNotFoundException(Speciality.class, id);
            }
            specialityRepository.deleteById(id);
            log.info("Speciality with id " + speciality.getId() + " was deleted");
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return speciality;
    }

    @Transactional
    @Override
    public List<Speciality> getSpecialityBySpeciality(String speciality) throws EntityNotFoundException {
        List<Speciality> specialities = new ArrayList<>();
        try {
            specialities = specialityRepository.findBySpecialityStartingWith(speciality);
            if (specialities.isEmpty()) {
                throw new EntityNotFoundException(Speciality.class, 0L);
            }
            log.info("Specialities with speciality starting with " + speciality + " were found");
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return specialities;
    }

    @Transactional
    @Override
    public Speciality getSpecialityById(Long id) throws EntityNotFoundException {
        Speciality speciality = null;
        try {
            speciality = specialityRepository.findSpecialityById(id);
            if (speciality == null ) {
                throw new EntityNotFoundException(Speciality.class, id);
            }
            log.info("Speciality with id  " + id + " was found");
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return speciality;
    }
}
