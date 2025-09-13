package ru.otus.hw.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Butterfly {

    private String name;

    private Sex sex;

    private String form;

    private Color color;

    @Override
    public String toString() {
        return "%s%s \u001B[0m \n Butterfly %s".formatted(color.ansiCode, form, name);
    }
}
