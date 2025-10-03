package ru.otus.hw.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Entity
@Builder
@Table(name = "subscriptions")
@NamedEntityGraph(name = "subscription-full-graph", attributeNodes = {@NamedAttributeNode("user"),
        @NamedAttributeNode("place"), @NamedAttributeNode("event"), @NamedAttributeNode("theme")})
@NamedEntityGraph(name = "subscription-event-graph", attributeNodes = {@NamedAttributeNode("event")})
@NamedEntityGraph(name = "subscription-theme-graph", attributeNodes = {@NamedAttributeNode("theme")})
@NamedEntityGraph(name = "subscription-place-graph", attributeNodes = {@NamedAttributeNode("place")})
@NamedEntityGraph(name = "subscription-user-graph", attributeNodes = {@NamedAttributeNode("user")})
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "event_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "place_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "theme_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Theme theme;
}
