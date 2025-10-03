package ru.otus.hw.services;

import ru.otus.hw.models.SubscriptionType;

public interface NotificationService {

    void notify(long id, SubscriptionType type);

    void notify(Iterable<Long> ids, SubscriptionType type);

}
