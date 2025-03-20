package ru.otus.hw.service;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис TestServiceImpl")
class TestServiceImplTest {
    @Mock
    private IOService ioService;
    @Mock
    private QuestionDao questionDao;
    @InjectMocks
    private TestServiceImpl testService;

    @Test
    void executeTestWithError() {
        when(questionDao.findAll()).thenThrow(QuestionReadException.class);
        assertThrows(QuestionReadException.class, () -> testService.executeTest());
    }

    @Test
    void executeTestWithOneQuestion() {
        String questionText = "Left is correct";
        List<Question> oneQuestion = List.of(new Question(questionText, List.of(new Answer("Left", true), new Answer("Right", false))));
        when(questionDao.findAll()).thenReturn(oneQuestion);
        testService.executeTest();
        verify(ioService).printFormattedLine("Question: %s", questionText);
    }
}