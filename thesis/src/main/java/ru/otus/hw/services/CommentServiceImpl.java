package ru.otus.hw.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.converters.CommentDtoConverter;
import ru.otus.hw.dto.CommentDTO;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.EventRepository;
import ru.otus.hw.repositories.UserRepository;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final EventRepository eventRepository;

    private final CommentDtoConverter commentDtoConverter;

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<CommentDTO> findById(long id) {
        return commentRepository.findById(id).map(commentDtoConverter::modelToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDTO> findByEventId(long eventId) {
        return commentDtoConverter.modelsToDTO(commentRepository.findByEventId(eventId));
    }

    @Override
    @Transactional
    public CommentDTO insert(String text, long eventId, long userId) {
        return commentDtoConverter.modelToDTO(save(0, text, eventId, userId));
    }

    @Override
    @Transactional
    public CommentDTO update(long id, String text, long eventId, long userId) {
        return commentDtoConverter.modelToDTO(save(id, text, eventId, userId));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    private Comment save(long id, String text, long eventId, long userId) {
        var event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event with id %d not found".formatted(eventId)));
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id %d not found".formatted(userId)));
        var comment = new Comment(id, event, text, Timestamp.from(Instant.now()), user);
        return commentRepository.save(comment);
    }
}
