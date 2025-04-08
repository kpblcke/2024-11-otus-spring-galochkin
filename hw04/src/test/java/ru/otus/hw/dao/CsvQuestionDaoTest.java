package ru.otus.hw.dao;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.domain.Question;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("DAO CsvQuestionDao")
class CsvQuestionDaoTest {

    @MockBean
    private AppProperties appProperties;
    @Autowired
    private CsvQuestionDao csvQuestionDao;

    @Test
    @DisplayName("Получение данных из ДАО")
    void readCsvFile() {
        when(appProperties.getTestFileName()).thenReturn("test-questions.csv");
        List<Question> questions = csvQuestionDao.findAll();

        assertAll(
                () -> assertEquals(5, questions.size()),
                () -> questions.forEach(question -> assertNotNull(question.text())),
                () -> questions.forEach(question -> assertNotNull(question.answers()))
        );
    }

}