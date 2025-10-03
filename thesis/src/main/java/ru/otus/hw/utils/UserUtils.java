package ru.otus.hw.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.User;
import ru.otus.hw.services.UserService;


@Service
@RequiredArgsConstructor
public class UserUtils {

    private final UserService userService;

    public long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("User not found"));

        return user.getId();
    }

}
