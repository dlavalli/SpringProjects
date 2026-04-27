package com.lavalliere.daniel.spring.spring_ai_text_to_speech.services;

import com.lavalliere.daniel.spring.spring_ai_text_to_speech.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.audio.tts.TextToSpeechPrompt;
import org.springframework.ai.audio.tts.TextToSpeechResponse;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OpenAIServiceImpl implements OpenAIService {
    private final OpenAiAudioSpeechModel speechModel;

    @Override
    public byte[] getSpeech(Question question) {
        OpenAiAudioSpeechOptions speechOptions = OpenAiAudioSpeechOptions.builder()
            .voice(OpenAiAudioApi.SpeechRequest.Voice.ALLOY)
            .speed(1.0)
            .responseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
            .model(OpenAiAudioApi.TtsModel.TTS_1.value)
            .build();

        TextToSpeechPrompt speechPrompt = new TextToSpeechPrompt(question.question(),
            speechOptions);

        TextToSpeechResponse response = speechModel.call(speechPrompt);

        return response.getResult().getOutput();
    }
}
