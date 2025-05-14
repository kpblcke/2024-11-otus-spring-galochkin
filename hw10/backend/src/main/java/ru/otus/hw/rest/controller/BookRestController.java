package ru.otus.hw.rest.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.BookDTO;
import ru.otus.hw.dto.BookShortDTO;
import ru.otus.hw.rest.response.ValidationResponse;
import ru.otus.hw.services.BookService;

@RestController
@AllArgsConstructor
public class BookRestController {

    private final BookService bookService;

    @GetMapping(value = "/api/v1/books", params = "authorId")
    public ResponseEntity<List<BookDTO>> getByAuthorId(@RequestParam("authorId") long authorId) {
        var books = bookService.findAllByAuthorId(authorId);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/api/v1/books")
    public List<BookDTO> getAllBooks() {
        List<BookDTO> books = bookService.findAll();
        return books;
    }

    @PostMapping("/api/v1/books")
    public ResponseEntity<ValidationResponse> createBook(@RequestBody @Valid BookShortDTO book,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return errorRespone(bindingResult);
        }
        bookService.insert(book);
        return new ResponseEntity<>(new ValidationResponse(false, null), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/v1/books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable long id) {
        bookService.deleteById(id);
    }

    @GetMapping("/api/v1/books/{id}")
    public BookDTO getBook(@PathVariable long id) {
        return bookService.getById(id);
    }

    @PatchMapping("/api/v1/books")
    public ResponseEntity<ValidationResponse> updateBook(@RequestBody @Valid BookShortDTO book,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return errorRespone(bindingResult);
        }

        bookService.update(book);
        return new ResponseEntity<>(new ValidationResponse(false, null), HttpStatus.ACCEPTED);
    }


    private ResponseEntity<ValidationResponse> errorRespone(BindingResult bindingResult) {
        ValidationResponse response = new ValidationResponse();
        Map<String, String> errorMap = bindingResult.getFieldErrors().stream().collect(
                Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage,
                        (oldValue, newValue) -> newValue));
        response.setHasError(true);
        response.setBadFields(errorMap);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
