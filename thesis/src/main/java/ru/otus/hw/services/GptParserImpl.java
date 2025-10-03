package ru.otus.hw.services;

import chat.giga.client.GigaChatClient;
import chat.giga.model.ModelName;
import chat.giga.model.completion.ChatMessage;
import chat.giga.model.completion.ChatMessageRole;
import chat.giga.model.completion.CompletionRequest;
import chat.giga.model.completion.CompletionResponse;
import chat.giga.model.file.FileResponse;
import chat.giga.model.file.UploadFileRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Analization;
import ru.otus.hw.models.Image;
import ru.otus.hw.repositories.AnalizationRepository;
import ru.otus.hw.repositories.ImageRepository;

@Service
@RequiredArgsConstructor
public class GptParserImpl implements GptParser {

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${gpt.active}")
    private boolean gptActive;

    private final GigaChatClient chatClient;

    private final ImageRepository imageRepository;

    private final AnalizationRepository analizationRepository;

    private final String gptMessage = "Я прислал тебе афишу мероприятия, "
            + "разбери что на ней написано "
            + "и отправь результат в формате json в файле должно быть описано:\n"
            + "название мероприятия (title), "
            + "дата и время проведения в формате yyyy-MM-ddTHH:mm:ss (startTime), "
            + "описание (description), "
            + "стоимость только цифры (price)";

    @Override
    public void processImage(long imageId) {
        if (!gptActive) {
            return;
        }

        Image image = imageRepository.findById(imageId).orElseThrow(
                () -> new EntityNotFoundException("Image not found"));
        FileResponse fileResponse = getUploadFile(image);

        var gptJson = chatClient.completions(CompletionRequest.builder()
                .model(ModelName.GIGA_CHAT_PRO_2)
                .message(ChatMessage.builder()
                        .content(gptMessage)
                        .attachment(fileResponse.id().toString())
                        .role(ChatMessageRole.USER)
                        .build())
                .build());

        processJson(gptJson, fileResponse, image);
    }

    private void processJson(CompletionResponse gptJson, FileResponse fileResponse, Image image) {
        if (gptJson != null) {
            String answer = gptJson.choices().get(0).message().content();
            int firstTrim = answer.indexOf('{');
            int secondTrim = answer.lastIndexOf('}') + 1;
            String trimJson = answer.substring(firstTrim, secondTrim);
            Analization analization = Analization.builder()
                    .processed(true)
                    .image(image)
                    .answer(trimJson)
                    .build();

            analizationRepository.save(analization);
        }
    }

    private FileResponse getUploadFile(Image image) {
        byte[] fileContent = null;
        try {
            fileContent = Files.readAllBytes(Path.of(uploadPath + "/" + image.getName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return chatClient.uploadFile(UploadFileRequest.builder()
                    .file(fileContent)
                    .mimeType(image.getMimeType())
                    .fileName(image.getName())
                    .purpose("general")
                    .build());
    }

}
