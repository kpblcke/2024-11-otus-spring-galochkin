package ru.otus.hw.rest.controller;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.rest.response.CommentResponse;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;

@RestController
@AllArgsConstructor
public class CommentRestController {

    private final BookService bookService;

    private final CommentService commentService;

    @GetMapping("/api/v1/comments/{bookId}")
    public CommentResponse commentByBook(@PathVariable long bookId) {
        var book = bookService.getById(bookId);
        var comments = commentService.findByBookId(bookId);
        return new CommentResponse(book.getId(), book.getTitle(), book.getAuthor().getFullName(), comments);
    }
}
