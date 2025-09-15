package ru.otus.hw.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Larva {

    private Sex sex;

    @Override
    public String toString() {
        return "Larva{" +
                "sex=" + sex +
                '}';
    }
}
