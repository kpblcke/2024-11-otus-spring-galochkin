package ru.otus.hw.controller;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.hw.converters.BookDtoConverter;
import ru.otus.hw.dto.BookDTO;
import ru.otus.hw.dto.BookShortDTO;
import ru.otus.hw.models.Book;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

@Controller
@Log4j2
@AllArgsConstructor
public class BookController {

    private static final String ERROR_TEXT = "delay call failed error:{}";

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final BookDtoConverter bookConverter;

    private final CircuitBreaker circuitBreaker;

    @GetMapping("/")
    public String index(Model model) {
        return "redirect:/book/all";
    }

    @GetMapping("/book/all")
    public String getAllBooks(Model model) {
        List<Book> books = circuitBreaker.run(bookService::findAll,
                t -> {
                    logError(t.getMessage());
                    return Collections.emptyList();
                });
        List<BookDTO> booksDto = bookConverter.modelsToDTO(books);
        model.addAttribute("books", booksDto);
        return "book/book";
    }

    @GetMapping("/book/create")
    public String createBook(Model model) {
        var book = new BookShortDTO();
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "book/create";
    }

    @PostMapping("/book/create")
    public String createBook(@Valid @ModelAttribute("book") BookShortDTO book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("genres", genreService.findAll());
            return "book/edit";
        }

        bookService.insert(book);
        return "redirect:/book/all";
    }

    @PostMapping("/book/delete/{id}")
    public String deleteBook(@PathVariable long id) {
        bookService.deleteById(id);
        return "redirect:/book/all";
    }

    @GetMapping("/book/edit/{id}")
    public String editBook(@PathVariable long id, Model model) {
        Book book = circuitBreaker.run(() -> bookService.getById(id),
                t -> {
                    logError(t.getMessage());
                    return new Book();
                });
        model.addAttribute("book", bookConverter.modelToShortDTO(book));
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "book/edit";
    }

    @PostMapping("/book/edit")
    public String saveBook(@Valid @ModelAttribute("book") BookShortDTO book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("genres", genreService.findAll());
            return "book/edit";
        }

        bookService.update(book);
        return "redirect:/book/all";
    }

    private void logError(String message) {
        log.error(ERROR_TEXT, message);
    }
}
