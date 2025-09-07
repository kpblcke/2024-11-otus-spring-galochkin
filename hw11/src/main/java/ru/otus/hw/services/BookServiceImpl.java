package ru.otus.hw.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.converters.BookDtoConverter;
import ru.otus.hw.dto.BookDTO;
import ru.otus.hw.dto.BookShortDTO;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import static org.springframework.util.CollectionUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final BookDtoConverter bookDtoConverter;

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDTO> findById(long id) {
        return bookRepository.findById(id).map(bookDtoConverter::modelToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> findAll() {
        return bookDtoConverter.modelsToDTO(bookRepository.findAll());
    }

    @Transactional
    @Override
    public BookDTO insert(BookShortDTO bookShortDTO) {
        var author = getAuthorById(bookShortDTO.getAuthorId());
        var genres = getGenresByIds(bookShortDTO.getGenresIds());
        var book = new Book(0, bookShortDTO.getTitle(), author, genres);
        return bookDtoConverter.modelToDTO(bookRepository.save(book));
    }

    @Transactional
    @Override
    public BookDTO update(BookShortDTO bookDTO) {
        var book = getBookById(bookDTO.getId());
        var author = getAuthorById(bookDTO.getAuthorId());
        var genres = getGenresByIds(bookDTO.getGenresIds());
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(author);
        book.setGenres(genres);
        return bookDtoConverter.modelToDTO(bookRepository.save(book));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    private Book save(long id, String title, long authorId, Set<Long> genresIds) {
        if (CollectionUtils.isEmpty(genresIds)) {
            throw new IllegalArgumentException("Genres ids must not be null");
        }

        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(authorId)));
        var genres = genreRepository.findAllByIdIn(genresIds);
        if (CollectionUtils.isEmpty(genres) || genresIds.size() != genres.size()) {
            throw new EntityNotFoundException("One or all genres with ids %s not found".formatted(genresIds));
        }

        var book = new Book(id, title, author, genres);
        return bookRepository.save(book);
    }

    private Book getBookById(long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));
    }

    private Author getAuthorById(long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(authorId)));
    }

    private List<Genre> getGenresByIds(Set<Long> genresIds) {
        var genres = genreRepository.findAllByIdIn(genresIds);
        if (isEmpty(genres) || genresIds.size() != genres.size()) {
            throw new EntityNotFoundException("One or all genres with ids %s not found".formatted(genresIds));
        }
        return genres;
    }
}
