package ru.otus.hw.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.models.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @EntityGraph("subscription-full-graph")
    List<Subscription> findAllByUserId(Long userId);

    @EntityGraph("subscription-event-graph")
    List<Subscription> findAllByUserIdAndEventIsNotNull(Long userId);

    @EntityGraph("subscription-theme-graph")
    List<Subscription> findAllByUserIdAndThemeIsNotNull(Long userId);

    @EntityGraph("subscription-place-graph")
    List<Subscription> findAllByUserIdAndPlaceIsNotNull(Long userId);

    @EntityGraph("subscription-user-graph")
    List<Subscription> findAllByThemeId(Long themeId);

    @EntityGraph("subscription-user-graph")
    @Query(value = "select s from Subscription as s where s.theme.id in :themeIds")
    List<Subscription> findAllByThemeIdsIn(@Param("themeIds") Iterable<Long> themeIds);

    @Modifying
    @Transactional
    @Query(value = "insert into subscriptions(event_id, user_id) values (:eventId, :userId)", nativeQuery = true)
    void subscribeToEvent(@Param("userId") long userId, @Param("eventId") long eventId);

    @Modifying
    @Transactional
    @Query(value = "delete from subscriptions where user_id = :userId and event_id = :eventId", nativeQuery = true)
    void deleteAllByUserIdAndEventId(@Param("userId") long userId, @Param("eventId") long eventId);

    @Modifying
    @Transactional
    @Query(value = "insert into subscriptions(theme_id, user_id) values (:themeId, :userId)", nativeQuery = true)
    void subscribeToThemeId(@Param("userId") long userId, @Param("themeId") long themeId);

    @Modifying
    @Transactional
    @Query(value = "delete from subscriptions where user_id = :userId and theme_id = :themeId", nativeQuery = true)
    void deleteAllByUserIdAndThemeId(@Param("userId") long userId, @Param("themeId") long themeId);

    @Modifying
    @Transactional
    @Query(value = "insert into subscriptions(place_id, user_id) values (:placeId, :userId)", nativeQuery = true)
    void subscribeToPlaceId(@Param("userId") long userId, @Param("placeId") long placeId);

    @Modifying
    @Transactional
    @Query(value = "delete from subscriptions where user_id = :userId and place_id = :placeId", nativeQuery = true)
    void deleteAllByUserIdAndPlaceId(@Param("userId") long userId, @Param("placeId") long placeId);
}
