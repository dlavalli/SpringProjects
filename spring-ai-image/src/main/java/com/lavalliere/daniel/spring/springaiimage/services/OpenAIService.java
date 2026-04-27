package com.lavalliere.daniel.spring.springaiimage.services;

import com.lavalliere.daniel.spring.springaiimage.model.Question;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface OpenAIService {
    byte[] getImage(Question question);

    String getDescription(MultipartFile file) throws IOException;
}
