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

    /*
       Note that this requires a locally running instance (docker image through compose.yml)
       AND was missing mistral model when trying to execute POST localhost:8080/ask
       with the Content-Type: application/json  and the following JSON body
       { "question": "What is the movie Spider-Man: No Way Home about?"}

       SO add to setup a docker volume for ollama and the from the image run:   ollama pull mistral
       (
        possibly also ollama pull llama3.2  or could add the following to the compose.yml file:
        entrypoint: ["/bin/bash", "-c", "ollama serve & sleep 5 && ollama pull mistral && wait"]
       )
     */
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

    /**
     * Typical response for a single line
     * {"data":[{"embedding":"9usAB....==","index":0,"object":"embedding"}],"model":"pplx-embed-v1-0.6b","object":"list","usage":{"cost":{"currency":"USD","input_cost":0.000002092,"total_cost":0.000002092},"prompt_tokens":523,"total_tokens":523}}
     * For 2 lines back to back:
     * {"error":{"message":"model must be one of: pplx-embed-v1-0.6b pplx-embed-v1-4b","type":"invalid_request","code":400}}
     *
     * @param embeddingModel
     * @param vectorStoreProperties
     * @return
     */

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
