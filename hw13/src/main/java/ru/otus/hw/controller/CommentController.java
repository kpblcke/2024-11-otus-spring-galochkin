package ru.otus.hw.controller;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.hw.converters.BookDtoConverter;
import ru.otus.hw.exceptions.ContentNotFoundException;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;

@Controller
@AllArgsConstructor
public class CommentController {

    private final BookService bookService;

    private final CommentService commentService;

    private final BookDtoConverter bookConverter;

    @GetMapping("/comment/{bookId}")
    public String comment(@PathVariable long bookId, Model model) {
        var book = bookService.getById(bookId);
        var comments = commentService.findByBookId(bookId);
        model.addAttribute("book", book);
        model.addAttribute("genres", book.getGenres());
        model.addAttribute("comments", comments);
        return "comment/comment";
    }
}
