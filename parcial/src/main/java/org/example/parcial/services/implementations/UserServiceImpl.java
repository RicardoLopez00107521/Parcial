package org.example.parcial.services.implementations;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.example.parcial.domain.dtos.GeneralResponse;
import org.example.parcial.domain.dtos.RegisterDTO;
import org.example.parcial.domain.entities.User;
import org.example.parcial.repositories.UserRepository;
import org.example.parcial.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User findUserByIdentifier(String email) {
        return userRepository
                .findByEmail(email)
                .orElse(null);
    }

    @Override
    public User findByIdentity(UUID uuid) {
        return userRepository
                .findByUserId(uuid)
                .orElse(null);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void registerUser(RegisterDTO info, Date birthDate) {

        User user = new User();
        user.setName(info.getName());
        user.setEmail(info.getEmail());
        user.setFecha_nac(birthDate);
        user.setPassword(info.getPassword());

        userRepository.save(user);
    }

    @Override
    public Date validDate(String birthDate) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(birthDate);
        } catch (ParseException ex) {
            return null;
        }
    }
}
