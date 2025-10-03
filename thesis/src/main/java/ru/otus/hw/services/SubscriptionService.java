package ru.otus.hw.services;

import java.util.List;
import ru.otus.hw.models.Subscription;

public interface SubscriptionService {

    List<Subscription> getByUser(long userId);

    List<Subscription> getEventsSubscriptionByUser(long userId);

    List<Subscription> getThemeSubscriptionByUser(long userId);

    List<Subscription> getPlaceSubscriptionByUser(long userId);

    void unsubscribeById(long id);

    void unsubscribeByUserIdAndEventId(long userId, long eventId);

    void subscribeToEventId(long userId, long eventId);

    void unsubscribeByUserIdAndThemeId(long userId, long themeId);

    void subscribeToThemeId(long userId, long themeId);

    void unsubscribeByUserIdAndPlaceId(long userId, long placeId);

    void subscribeToPlaceId(long userId, long placeId);

}
