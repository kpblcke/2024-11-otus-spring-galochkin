package ru.otus.hw.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Caterpillar {

    private String name;

    private Sex sex;

    @Override
    public String toString() {
        return "Caterpillar sex=%s, name=%s".formatted(sex, name);
    }
}
