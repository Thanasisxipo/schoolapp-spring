package gr.aueb.cf.schoolapppro.service;

import gr.aueb.cf.schoolapppro.dto.UserInsertDTO;
import gr.aueb.cf.schoolapppro.dto.UserUpdateDTO;
import gr.aueb.cf.schoolapppro.mapper.Mapper;
import gr.aueb.cf.schoolapppro.model.User;
import gr.aueb.cf.schoolapppro.repository.UserRepository;
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
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public User insertUser(UserInsertDTO dto) throws Exception {
        User user = null;
        try {
            user = userRepository.save(Mapper.mapToUser(dto));
            if (user.getId() == null) {
                throw new Exception("Insert error");
            }
            log.info("Insert success for user with id " + user.getId());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
        return user;
    }

    @Transactional
    @Override
    public User updateUser(UserUpdateDTO dto) throws EntityNotFoundException {
        User user = null;
        User updatedUser = null;
        try {
            user = userRepository.findUserById(dto.getId());
            if (user == null ) {
                throw new EntityNotFoundException(User.class, dto.getId());
            }
            updatedUser = userRepository.save(Mapper.mapToUser(dto));
            log.info("User with id " + updatedUser.getId() + " was updated");
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return updatedUser;
    }

    @Transactional
    @Override
    public User deleteUser(Long id) throws EntityNotFoundException {
        User user = null;
        try {
            user = userRepository.findUserById(id);
            if (user == null ) {
                throw new EntityNotFoundException(User.class, id);
            }
            userRepository.deleteById(id);
            log.info("User with id " + user.getId() + " was deleted");
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return user;
    }

    @Transactional
    @Override
    public List<User> getUserByUsername(String username) throws EntityNotFoundException {
        List<User> users = new ArrayList<>();
        try {
            users = userRepository.findByUsernameStartingWith(username);
            if (users.isEmpty()) {
                throw new EntityNotFoundException(User.class, 0L);
            }
            log.info("Users with username starting with " + username + " were found");
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return users;
    }

    @Transactional
    @Override
    public User getUserById(Long id) throws EntityNotFoundException {
        User user = null;
        try {
            user = userRepository.findUserById(id);
            if (user == null ) {
                throw new EntityNotFoundException(User.class, id);
            }
            log.info("User with id  " + id + " was found");
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return user;
    }
}
