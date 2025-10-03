package ru.otus.hw.controller;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.hw.converters.SubscribleDtoConverter;
import ru.otus.hw.converters.ThemeDtoConverter;
import ru.otus.hw.dto.ThemeDTO;
import ru.otus.hw.models.Subscription;
import ru.otus.hw.models.Theme;
import ru.otus.hw.services.SubscriptionService;
import ru.otus.hw.services.ThemeService;
import ru.otus.hw.utils.UserUtils;

@Controller
@AllArgsConstructor
public class ThemeController {

    private final ThemeService themeService;

    private final ThemeDtoConverter themeConverter;

    private final SubscriptionService subscriptionService;

    private final SubscribleDtoConverter subscribleConverter;

    private final UserUtils userUtils;

    @GetMapping("/theme/all")
    public String getAllThemes(Model model) {
        List<ThemeDTO> themes = themeConverter.modelsToDTO(themeService.findAll());
        List<Long> subscribed = subscriptionService.getThemeSubscriptionByUser(userUtils.getUserId()).stream()
                .map(Subscription::getTheme)
                .map(Theme::getId)
                .collect(Collectors.toList());

        subscribleConverter.modelsToDTO(themes, subscribed);
        model.addAttribute("themes", themes);
        return "theme";
    }

    @PostMapping("/theme/delete/{id}")
    public String deletePlace(@PathVariable long id) {
        themeService.deleteById(id);
        return "redirect:/theme/all";
    }


}
