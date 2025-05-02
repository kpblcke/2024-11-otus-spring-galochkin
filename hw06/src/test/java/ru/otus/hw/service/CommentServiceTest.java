package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.converters.AuthorConverter;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.converters.BookDtoConverterImpl;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.converters.CommentDtoConverterImpl;
import ru.otus.hw.converters.GenreConverter;
import ru.otus.hw.repositories.JpaAuthorRepository;
import ru.otus.hw.repositories.JpaBookRepository;
import ru.otus.hw.repositories.JpaCommentRepository;
import ru.otus.hw.repositories.JpaGenreRepository;
import ru.otus.hw.services.BookServiceImpl;
import ru.otus.hw.services.CommentService;
import ru.otus.hw.services.CommentServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для работы с комментариями ")
@DataJpaTest
@Import({BookDtoConverterImpl.class, BookConverter.class,
        CommentDtoConverterImpl.class, CommentConverter.class,
        CommentServiceImpl.class, BookServiceImpl.class,
        JpaCommentRepository.class,
        AuthorConverter.class, GenreConverter.class,
        JpaBookRepository.class, JpaAuthorRepository.class,
        JpaGenreRepository.class,})
@Transactional(propagation = Propagation.NEVER)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @DisplayName("должен найти комментарий по id")
    @Test
    public void shouldFindCommentById() {
        var actualComment = commentService.findById(1L);

        assertThat(actualComment).isPresent();
        assertThat(actualComment.get().getId()).isEqualTo(1L);
        assertThat(actualComment.get().getText()).isEqualTo("First comment");
    }

    @DisplayName("должен найти комментарий по id книги")
    @Test
    public void shouldFindCommentByBookId() {
        var actualComments = commentService.findByBookId(1L);

        assertThat(actualComments)
                .isNotEmpty()
                .hasSize(3)
                .allMatch(b -> b.getId() != 0L);
    }

    @DisplayName("должен добавлять новый комментарий")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void shouldAddNewComment() {
        var insertedComment = commentService.insert("Such a nice book", 1L);
        var dbComment = commentService.findById(insertedComment.getId());

        assertThat(dbComment).isPresent();
        assertThat(dbComment.get().getId()).isEqualTo(insertedComment.getId());
        assertThat(dbComment.get().getText()).isEqualTo(insertedComment.getText());
    }

    @DisplayName("должен обновлять комментарий")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void shouldUpdateComment() {
        var oldComment = commentService.findById(1L);
        var updatedComment = commentService.update(oldComment.get().getId(), "Such a nice book_2", 1L);
        var dbComment = commentService.findById(1L);

        assertThat(dbComment).isPresent();
        assertThat(dbComment.get().getId()).isEqualTo(updatedComment.getId());
        assertThat(dbComment.get().getText()).isEqualTo(updatedComment.getText());
    }

    @DisplayName("должен удалять комментарий по id")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void shouldDeleteBookById() {
        commentService.deleteById(1L);
        var actualComment = commentService.findById(1L);

        assertThat(actualComment).isEmpty();
    }

}
