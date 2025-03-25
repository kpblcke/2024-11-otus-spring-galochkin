package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileReader;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;

import java.util.List;
import ru.otus.hw.exceptions.QuestionReadException;

@Component
@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private static final int LINE_SKIP_COUNT = 1;

    private static final char SEPARATOR = ';';

    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        List<QuestionDto> questionsDto;
        URL resource = getClass().getClassLoader().getResource(fileNameProvider.getTestFileName());
        try (FileReader reader = new FileReader(resource.getFile())) {
            questionsDto = new CsvToBeanBuilder(reader)
                    .withType(QuestionDto.class)
                    .withSkipLines(LINE_SKIP_COUNT)
                    .withSeparator(SEPARATOR)
                    .build()
                    .parse();
        } catch (Exception e) {
            throw new QuestionReadException(e.getMessage(), e);
        }
        return questionsDto.stream().map(QuestionDto::toDomainObject).toList();
    }
}
