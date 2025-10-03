package ru.otus.hw.controller;


import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.hw.dto.CommentDTO;
import ru.otus.hw.dto.CommentShortDTO;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.CommentService;
import ru.otus.hw.services.EventService;
import ru.otus.hw.utils.UserUtils;

@Controller
@AllArgsConstructor
public class CommentController {

    private final EventService eventService;

    private final CommentService commentService;

    private final UserUtils userUtils;

    @GetMapping("/comment/{eventId}")
    public String allCommentByEvent(@PathVariable long eventId, Model model) {
        var event = eventService.getById(eventId);
        List<CommentDTO> comments = commentService.findByEventId(eventId);
        model.addAttribute("event", event);
        model.addAttribute("comments", comments);
        return "comment";
    }

    @PostMapping("/comment/delete/{id}")
    public String deleteComment(@PathVariable long id) {
        CommentDTO comment = commentService.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Comment not found"));
        commentService.deleteById(id);
        return "redirect:/comment/" + comment.getEventId();
    }

    @GetMapping("/comment/create")
    public String createBook(Model model) {
        var comment = new CommentShortDTO();
        model.addAttribute("comment", comment);
        return "comment/create";
    }

    @PostMapping("/comment/create")
    public String newComment(@Valid @ModelAttribute("comment") CommentShortDTO comment,
            BindingResult bindingResult, Model model) {
        commentService.insert(comment.getText(), comment.getEventId(), userUtils.getUserId());
        return "redirect:/comment/" + comment.getEventId();
    }

}
