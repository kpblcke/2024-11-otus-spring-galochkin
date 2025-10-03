package ru.otus.hw.services;

import java.util.List;
import java.util.Optional;
import ru.otus.hw.dto.NewUserDTO;
import ru.otus.hw.models.User;

public interface UserService {

    void registerNewUser(NewUserDTO newUser);

    Optional<User> findByUsername(String username);

    User getById(long id);

    List<User> findAll();

    void deleteById(long id);
}
