package ru.otus.hw.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.hw.models.Image;
import ru.otus.hw.services.GptParser;
import ru.otus.hw.services.ImageServiceImpl;

@Controller
@RequiredArgsConstructor
public class ImageController {

    @Value("${upload.path}")
    private String uploadPath;

    private final GptParser gptParser;

    private final ImageServiceImpl imageService;

    @GetMapping("/image/all")
    public String getAllImages(Model model) {

        List<Image> images = imageService.findAll();

        model.addAttribute("images", images);
        model.addAttribute("uploadPath", uploadPath);
        return "image";
    }

    @PostMapping("/image/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file,
            Model model) throws IOException {
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file");
            return "index";
        }

        // Создаем директорию если не существует
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        String fileName = UUID.randomUUID() + "." +
                StringUtils.getFilenameExtension(file.getOriginalFilename());
        file.transferTo(new File(uploadPath + "/" + fileName));

        // Сохраняем в базу данных
        Image image = imageService.save(Image.builder()
                .filePath(file.getOriginalFilename())
                .name(fileName)
                .mimeType(file.getContentType())
                .build());
        gptParser.processImage(image.getId());
        return "redirect:/image/all";
    }
}