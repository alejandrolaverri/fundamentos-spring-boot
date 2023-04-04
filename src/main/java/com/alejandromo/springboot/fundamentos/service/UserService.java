package com.alejandromo.springboot.fundamentos.service;

import com.alejandromo.springboot.fundamentos.entity.User;
import com.alejandromo.springboot.fundamentos.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final Log LOG = LogFactory.getLog(UserService.class);
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveTransactional(List<User> users) {
        users.stream()
             .peek(user -> LOG.info("Usuario insertado: " + user))
             .forEach(userRepository::save);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User save(User newUser) {
        return userRepository.save(newUser);
    }

    public void delete(Long id) {
        userRepository.delete(new User(id));
    }

    public User update(User newUser, Long id) {
        return userRepository.findById(id)
                .map(
                        user -> {
                            user.setName(newUser.getName());
                            user.setEmail(newUser.getEmail());
                            user.setBirthDate(newUser.getBirthDate());
                            return userRepository.save(user);
                        }
                ).get();
    }
}
