package ru.otus.hw.controller;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.hw.converters.PlaceDtoConverter;
import ru.otus.hw.converters.SubscribleDtoConverter;
import ru.otus.hw.dto.PlaceDTO;
import ru.otus.hw.models.Place;
import ru.otus.hw.models.Subscription;
import ru.otus.hw.services.PlaceService;
import ru.otus.hw.services.SubscriptionService;
import ru.otus.hw.utils.UserUtils;

@Controller
@AllArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    private final PlaceDtoConverter placeConverter;

    private final SubscriptionService subscriptionService;

    private final SubscribleDtoConverter subscribleConverter;

    private final UserUtils userUtils;

    @GetMapping("/place/all")
    public String getAllEvents(Model model) {
        List<PlaceDTO> places = placeConverter.modelsToDTO(placeService.findAll());
        List<Long> subscribed = subscriptionService.getPlaceSubscriptionByUser(userUtils.getUserId()).stream()
                .map(Subscription::getPlace)
                .map(Place::getId)
                .collect(Collectors.toList());
        subscribleConverter.modelsToDTO(places, subscribed);
        model.addAttribute("places", places);
        return "place";
    }

    @PostMapping("/place/delete/{id}")
    public String deletePlace(@PathVariable long id) {
        placeService.deleteById(id);
        return "redirect:/event/all";
    }


}
