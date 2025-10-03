package ru.otus.hw.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.hw.converters.EventDtoConverter;
import ru.otus.hw.converters.SubscribleDtoConverter;
import ru.otus.hw.dto.EventDTO;
import ru.otus.hw.dto.EventShortDTO;
import ru.otus.hw.dto.EventJson;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Analization;
import ru.otus.hw.models.Event;
import ru.otus.hw.models.Image;
import ru.otus.hw.models.Subscription;
import ru.otus.hw.repositories.AnalizationRepository;
import ru.otus.hw.repositories.ImageRepository;
import ru.otus.hw.services.EventService;
import ru.otus.hw.services.ImageService;
import ru.otus.hw.services.JsonMappingService;
import ru.otus.hw.services.PlaceService;
import ru.otus.hw.services.SubscriptionService;
import ru.otus.hw.services.ThemeService;
import ru.otus.hw.utils.UserUtils;

@Controller
@AllArgsConstructor
public class EventController {

    private final EventService eventService;

    private final PlaceService placeService;

    private final ThemeService themeService;

    private final ImageService imageService;

    private final SubscriptionService subscriptionService;

    private final EventDtoConverter eventConverter;

    private final SubscribleDtoConverter subscribleConverter;

    private final JsonMappingService jsonMappingService;

    private final UserUtils userUtils;

    private final AnalizationRepository analizationRepository;

    private final ImageRepository imageRepository;

    @GetMapping("/")
    public String index(Model model) {
        return "redirect:/event/all";
    }

    @GetMapping("/event/all")
    public String getAllEvents(Model model) {
        List<Event> eventList = eventService.findAll();
        List<Long> subscribed = subscriptionService.getEventsSubscriptionByUser(userUtils.getUserId()).stream()
                .map(Subscription::getEvent)
                .map(Event::getId)
                .collect(Collectors.toList());
        List<EventDTO> events = eventConverter.modelsToDTO(eventList);
        subscribleConverter.modelsToDTO(events, subscribed);

        model.addAttribute("events", events);
        return "event";
    }

    @GetMapping("/event/place/{placeId}")
    public String getAllEventsByPlaceId(@PathVariable long placeId, Model model) {

        List<Event> eventList = eventService.findAllByPlaceId(placeId);
        List<EventDTO> events = eventConverter.modelsToDTO(eventList);
        model.addAttribute("events", events);
        return "event";
    }

    @PostMapping("/event/delete/{id}")
    public String deleteEvent(@PathVariable long id) {
        eventService.deleteById(id);
        return "redirect:/event/all";
    }

    @GetMapping("/event/create")
    public String createEvent(Model model) {
        var event = new EventShortDTO();
        model.addAttribute("event", event);
        model.addAttribute("themes", themeService.findAll());
        model.addAttribute("places", placeService.findAll());
        model.addAttribute("afisha", imageService.findAll());
        return "event/create";
    }

    @PostMapping("/event/create")
    public String createEvent(@Valid @ModelAttribute("event") EventShortDTO event,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("themes", themeService.findAll());
            model.addAttribute("places", placeService.findAll());
            return "event/edit";
        }

        eventService.insert(event);
        return "redirect:/event/all";
    }

    @PostMapping("/event/create/{analizationId}")
    public String createFromAnalization(@PathVariable long analizationId) {
        Analization analization = analizationRepository.findById(analizationId)
                .orElseThrow(() -> new EntityNotFoundException("Analization not found"));
        EventJson json = jsonMappingService.testMapStringToRequest(analization.getAnswer());
        String imageLink = imageRepository.findById(analization.getImage().getId())
                .map(Image::getName)
                .orElse(null);
        json.setImageLink(imageLink);

        eventService.insert(json);
        return "redirect:/event/all";
    }


}
