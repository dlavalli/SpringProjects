package com.lavalliere.daniel.spring.springairag.config;

import com.lavalliere.daniel.spring.springairag.utils.CsvDocumentReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.management.ModelManagementOptions;
import org.springframework.ai.ollama.management.PullModelStrategy;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.File;
import java.time.Duration;
import java.util.List;

@Slf4j
@Configuration
public class VectorStoreConfig {

    @Autowired
    private OllamaApi ollamaChatClient;

    @Bean
    @Primary
    public EmbeddingModel customOllamaEmbeddingModel() {
        return OllamaEmbeddingModel.builder()
            .ollamaApi(ollamaChatClient)
            .modelManagementOptions(new ModelManagementOptions(
                PullModelStrategy.WHEN_MISSING,
                List.of("pplx-embed-v1-0.6b","pplx-embed-v1-4b"),
                Duration.ofMinutes(5L), 0)
            )
            .build();
    }

    @Bean
    public SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel, VectorStoreProperties vectorStoreProperties) {
        log.info("Default embedding model is: {}", embeddingModel.toString());
        SimpleVectorStore store = SimpleVectorStore.builder(embeddingModel).build();

        File vectorStoreFile = new File(vectorStoreProperties.getVectorStorePath());

        if (vectorStoreFile.exists()) {
            store.load(vectorStoreFile);
        } else {
            log.debug("Loading documents into vector store");
            vectorStoreProperties.getDocumentsToLoad().forEach(document -> {
                log.debug("Loading document: " + document.getFilename());
                CsvDocumentReader documentReader = new CsvDocumentReader(document);
                List<Document> docs = documentReader.get();
                TextSplitter textSplitter = new TokenTextSplitter();
                List<Document> splitDocs = textSplitter.apply(docs);
                store.add(splitDocs);
            });

            store.save(vectorStoreFile);
        }

        return store;
    }
}
