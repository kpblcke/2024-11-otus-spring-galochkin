package ru.otus.hw.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.hw.converters.SubscriptionDtoConverter;
import ru.otus.hw.dto.SubscriptionDTO;
import ru.otus.hw.services.SubscriptionService;
import ru.otus.hw.utils.UserUtils;

@Controller
@AllArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    private final SubscriptionDtoConverter subscriptionConverter;

    private final UserUtils userUtils;

    @GetMapping("/subscription/user")
    public String getAllSubscriptions(Model model) {
        List<SubscriptionDTO> subscriptions = subscriptionConverter.modelsToDTO(
                subscriptionService.getByUser(userUtils.getUserId()));
        model.addAttribute("subscriptions", subscriptions);
        return "subscription";
    }

    @PostMapping("/subscription/delete/{id}")
    public String unsubscribe(@PathVariable long id) {
        subscriptionService.unsubscribeById(id);
        return "redirect:/subscription/user";
    }

    @PostMapping("/subscription/event/{eventId}/delete")
    public String unsubscribeByEvent(@PathVariable long eventId) {
        subscriptionService.unsubscribeByUserIdAndEventId(userUtils.getUserId(), eventId);
        return "redirect:/event/all";
    }

    @PostMapping("/subscription/event/{eventId}")
    public String subscribeEvent(@PathVariable long eventId) {
        subscriptionService.subscribeToEventId(userUtils.getUserId(), eventId);
        return "redirect:/event/all";
    }

    @PostMapping("/subscription/theme/{themeId}/delete")
    public String unsubscribeByTheme(@PathVariable long themeId) {
        subscriptionService.unsubscribeByUserIdAndThemeId(userUtils.getUserId(), themeId);
        return "redirect:/theme/all";
    }

    @PostMapping("/subscription/theme/{themeId}")
    public String subscribeTheme(@PathVariable long themeId) {
        subscriptionService.subscribeToThemeId(userUtils.getUserId(), themeId);
        return "redirect:/theme/all";
    }

    @PostMapping("/subscription/place/{placeId}/delete")
    public String unsubscribeByPlace(@PathVariable long placeId) {
        subscriptionService.unsubscribeByUserIdAndPlaceId(userUtils.getUserId(), placeId);
        return "redirect:/place/all";
    }

    @PostMapping("/subscription/place/{placeId}")
    public String subscribePlace(@PathVariable long placeId) {
        subscriptionService.subscribeToPlaceId(userUtils.getUserId(), placeId);
        return "redirect:/place/all";
    }
}
