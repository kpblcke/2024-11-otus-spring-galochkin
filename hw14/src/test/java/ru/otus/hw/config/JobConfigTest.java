package ru.otus.hw.config;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.models.jpa.AuthorJpa;
import ru.otus.hw.models.jpa.BookJpa;
import ru.otus.hw.models.jpa.CommentJpa;
import ru.otus.hw.models.jpa.GenreJpa;
import ru.otus.hw.repositories.jpa.AuthorRepositoryJpa;
import ru.otus.hw.repositories.jpa.BookRepositoryJpa;
import ru.otus.hw.repositories.jpa.CommentRepositoryJpa;
import ru.otus.hw.repositories.jpa.GenreRepositoryJpa;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SpringBatchTest
class JobConfigTest {

    private static final String IMPORT_USER_JOB_NAME = "migrateMongoToRelationDbJob";

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private AuthorRepositoryJpa authorRepositoryJpa;

    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    @Autowired
    private CommentRepositoryJpa commentRepositoryJpa;

    @Autowired
    private GenreRepositoryJpa genreRepositoryJpa;

    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void testJob() throws Exception {
        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(IMPORT_USER_JOB_NAME);

        JobParameters parameters = new JobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);

        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");

        List<AuthorJpa> authors = authorRepositoryJpa.findAll();
        assertThat(authors)
                .isNotEmpty()
                .isEqualTo(getExpectedAuthors());

        List<GenreJpa> genres = genreRepositoryJpa.findAll();
        assertThat(genres)
                .isNotEmpty()
                .isEqualTo(getExpectedGenres());

        List<BookJpa> books = bookRepositoryJpa.findAll();
        assertThat(books)
                .isNotEmpty()
                .isEqualTo(getExpectedBooks());

        List<CommentJpa> comments = commentRepositoryJpa.findAll();
        assertThat(comments)
                .isNotEmpty()
                .isEqualTo(getExpectedComments());
    }

    private List<AuthorJpa> getExpectedAuthors() {
        return List.of(
                new AuthorJpa(1L, "Author_1"),
                new AuthorJpa(2L, "Author_2"),
                new AuthorJpa(3L, "Author_3")
        );
    }

    private List<GenreJpa> getExpectedGenres() {
        return List.of(
                new GenreJpa(1L, "Genre_1"),
                new GenreJpa(2L, "Genre_2"),
                new GenreJpa(3L, "Genre_3"),
                new GenreJpa(4L, "Genre_4"),
                new GenreJpa(5L, "Genre_5"),
                new GenreJpa(6L, "Genre_6")
        );
    }

    private List<BookJpa> getExpectedBooks() {
        List<AuthorJpa> authors = getExpectedAuthors();
        List<GenreJpa> genres = getExpectedGenres();

        return List.of(
                new BookJpa(1L, "BookTitle_1", authors.get(0), List.of(genres.get(0), genres.get(1))),
                new BookJpa(2L, "BookTitle_2", authors.get(1), List.of(genres.get(2), genres.get(3))),
                new BookJpa(3L, "BookTitle_3", authors.get(2), List.of(genres.get(4), genres.get(5)))
        );
    }

    private List<CommentJpa> getExpectedComments() {
        List<BookJpa> books = getExpectedBooks();
        return List.of(
                new CommentJpa(1L, "First Comment", books.get(0)),
                new CommentJpa(2L, "Second Comment", books.get(0)),
                new CommentJpa(3L, "Third Comment", books.get(0)),
                new CommentJpa(4L, "First Comment", books.get(1)),
                new CommentJpa(5L, "Second Comment", books.get(1)),
                new CommentJpa(6L, "First Comment", books.get(2))
        );
    }
}