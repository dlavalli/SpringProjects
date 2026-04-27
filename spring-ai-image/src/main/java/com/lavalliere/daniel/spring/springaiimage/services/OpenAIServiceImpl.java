package com.lavalliere.daniel.spring.springaiimage.services;

import com.lavalliere.daniel.spring.springaiimage.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.content.Media;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ImageModel imageModel;

    private final ChatModel chatModel;

    @Override
    public String getDescription(MultipartFile file) {

        OpenAiChatOptions options = OpenAiChatOptions.builder()
            .model(OpenAiApi.ChatModel.GPT_4_O.getValue())
            .build();

        var userMessage = UserMessage.builder()
            .text("Explain what do you see in this picture?")
            .media(List.of(new Media(MimeTypeUtils.IMAGE_JPEG, file.getResource())))
            .build();

        ChatResponse response = chatModel.call(new Prompt(List.of(userMessage), options));

        return (response.getResult() != null ? response.getResult().getOutput().toString() : "No response returned by the OpenAI API");
    }

    @Override
    public byte[] getImage(Question question) {

        var options = OpenAiImageOptions.builder()
            .height(1024)
            .width(1792)
            .responseFormat("b64_json")
            .model("dall-e-3")
            .quality("hd") //default standard
            //.withStyle("natural") //default vivid
            .build();

        ImagePrompt imagePrompt = new ImagePrompt(question.question(), options);

        var imageResponse = imageModel.call(imagePrompt);

        return Base64.getDecoder().decode(imageResponse.getResult() != null ? imageResponse.getResult().getOutput().getB64Json() : "No response returned by the OpenAI API");
    }
}