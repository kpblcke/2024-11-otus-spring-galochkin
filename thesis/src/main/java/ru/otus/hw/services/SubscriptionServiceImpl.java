package ru.otus.hw.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.Subscription;
import ru.otus.hw.repositories.SubscriptionRepository;

@RequiredArgsConstructor
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public List<Subscription> getByUser(long userId) {
        return subscriptionRepository.findAllByUserId(userId);
    }

    @Override
    public List<Subscription> getEventsSubscriptionByUser(long userId) {
        return subscriptionRepository.findAllByUserIdAndEventIsNotNull(userId);
    }

    @Override
    public List<Subscription> getThemeSubscriptionByUser(long userId) {
        return subscriptionRepository.findAllByUserIdAndThemeIsNotNull(userId);
    }

    @Override
    public List<Subscription> getPlaceSubscriptionByUser(long userId) {
        return subscriptionRepository.findAllByUserIdAndPlaceIsNotNull(userId);
    }

    @Override
    public void unsubscribeById(long id) {
        subscriptionRepository.deleteById(id);
    }

    @Override
    public void unsubscribeByUserIdAndEventId(long userId, long eventId) {
        subscriptionRepository.deleteAllByUserIdAndEventId(userId, eventId);
    }

    @Override
    public void subscribeToEventId(long userId, long eventId) {
        subscriptionRepository.subscribeToEvent(userId, eventId);
    }

    @Override
    public void unsubscribeByUserIdAndThemeId(long userId, long themeId) {
        subscriptionRepository.deleteAllByUserIdAndThemeId(userId, themeId);
    }

    @Override
    public void subscribeToThemeId(long userId, long themeId) {
        subscriptionRepository.subscribeToThemeId(userId, themeId);
    }

    @Override
    public void unsubscribeByUserIdAndPlaceId(long userId, long placeId) {
        subscriptionRepository.deleteAllByUserIdAndPlaceId(userId, placeId);
    }

    @Override
    public void subscribeToPlaceId(long userId, long placeId) {
        subscriptionRepository.subscribeToPlaceId(userId, placeId);
    }
}
