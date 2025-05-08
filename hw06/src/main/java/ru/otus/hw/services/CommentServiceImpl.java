package ru.otus.hw.services;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.converters.CommentDtoConverter;
import ru.otus.hw.dto.CommentDTO;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    private final CommentDtoConverter commentDtoConverter;

    @Override
    @Transactional(readOnly = true)
    public Optional<CommentDTO> findById(long id) {
        return commentRepository.findById(id).map(commentDtoConverter::modelToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDTO> findByBookId(long bookId) {
        return commentDtoConverter.modelsToDTO(commentRepository.findByBookId(bookId));
    }

    @Override
    @Transactional
    public CommentDTO insert(String text, long bookId) {
        return commentDtoConverter.modelToDTO(save(0, text, bookId));
    }

    @Override
    @Transactional
    public CommentDTO update(long id, String text, long bookId) {
        return commentDtoConverter.modelToDTO(save(id, text, bookId));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    private Comment save(long id, String text, long bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));
        var comment = new Comment(id, book, text);
        return commentRepository.save(comment);
    }
}
