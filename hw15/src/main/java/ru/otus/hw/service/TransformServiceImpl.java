package ru.otus.hw.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import ru.otus.hw.domain.Butterfly;
import ru.otus.hw.domain.Caterpillar;
import ru.otus.hw.domain.Cocoon;
import ru.otus.hw.domain.Color;
import ru.otus.hw.domain.Larva;
import ru.otus.hw.domain.Sex;

import java.util.List;

@Slf4j
public class TransformServiceImpl implements TransformService {

    private final List<String> butterflyForms;

    private final List<String> femaleNames;

    private final List<String> maleNames;

    public TransformServiceImpl(List<String> butterflyForms,
                                List<String> femaleNames,
                                List<String> maleNames) {
        this.butterflyForms = butterflyForms;
        this.femaleNames = femaleNames;
        this.maleNames = maleNames;
    }

    @Override
    public Caterpillar transformLarva(Larva larva) {
        delay();
        String newName = generateName(larva.getSex());
        log.info("The Caterpillar name:\n {}", newName);
        return new Caterpillar(newName, larva.getSex());
    }


    @Override
    public Cocoon transformCaterpillar(Caterpillar caterpillar) {
        delay();
        log.info("The Caterpillar born:\n {}", caterpillar);
        return new Cocoon(caterpillar, generateColor());
    }

    @Override
    public Butterfly transformCocoon(Cocoon cocoon) {
        delay();
        Butterfly butterfly = new Butterfly(
                cocoon.getCaterpillar().getName(),
                cocoon.getCaterpillar().getSex(),
                generateForm(), cocoon.getColor());
        log.info("The butterfly was born:\n {}", butterfly);
        return butterfly;
    }

    private String generateName(Sex sex) {
        return Sex.MALE.equals(sex) ? maleNames.get(RandomUtils.nextInt(0, maleNames.size()))
                : femaleNames.get(RandomUtils.nextInt(0, femaleNames.size()));
    }

    private String generateForm() {
        return butterflyForms.get(RandomUtils.nextInt(0, butterflyForms.size()));
    }

    private Color generateColor() {
        return Color.values()[RandomUtils.nextInt(0, Color.values().length)];
    }

    private void delay() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
