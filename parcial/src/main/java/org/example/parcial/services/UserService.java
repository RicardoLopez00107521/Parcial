package org.example.parcial.services;

import org.example.parcial.domain.dtos.RegisterDTO;
import org.example.parcial.domain.entities.User;

import java.util.Date;
import java.util.UUID;

public interface UserService {
    User findUserByIdentifier(String email);
    User findByIdentity(UUID uuid);

    void registerUser(RegisterDTO info, Date birthDate);
    Date validDate (String birthDate);
}
