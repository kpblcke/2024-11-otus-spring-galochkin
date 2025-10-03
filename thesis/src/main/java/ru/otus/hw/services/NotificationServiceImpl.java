package ru.otus.hw.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Subscription;
import ru.otus.hw.models.SubscriptionType;
import ru.otus.hw.models.Theme;
import ru.otus.hw.models.User;
import ru.otus.hw.repositories.SubscriptionRepository;
import ru.otus.hw.repositories.ThemeRepository;

@RequiredArgsConstructor
@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    private final SubscriptionRepository subscriptionRepository;

    private final ThemeRepository themeRepository;

    private final EmailService emailService;

    @Override
    public void notify(long id, SubscriptionType type) {
        switch (type) {
            case THEME -> {
                List<Subscription> subscribers = subscriptionRepository.findAllByThemeId(id);
                Theme theme = themeRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException("Theme not found"));
                User notifyMe = subscribers.stream()
                        .map(Subscription::getUser)
                        .findFirst()
                        .orElse(null);
                if (notifyMe != null) {
                    String topic = "New event in your liked theme " + theme.getName();
                    String description = theme.getDescription() + "\n\n "
                            + "go check it! " + "http://localhost:8080/theme/all";
                    emailService.sendSimpleMessage(notifyMe.getEmail(), topic, description);
                }
            }
        }
    }

    @Override
    public void notify(Iterable<Long> ids, SubscriptionType type) {
        ids.forEach(id -> notify(id, type));
    }
}
