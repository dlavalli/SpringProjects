package com.lavalliere.daniel.spring7aisample3.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Slf4j
@Configuration
public class VectorStoreConfig {
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
    public SimpleVectorStore simpleVectorStore(
        EmbeddingModel embeddingModel,
        VectorStoreProperties vectorStoreProperties
    ) {
        log.info("Default embedding model is: {}", embeddingModel.toString());
        SimpleVectorStore store = SimpleVectorStore.builder(embeddingModel).build();

        File vectorStoreFile = new File(vectorStoreProperties.getVectorStorePath());

        if (vectorStoreFile.exists()) {
            log.info("Using existing local vector store");
            store.load(vectorStoreFile);
        } else {
            log.info("Loading documents into local vector store");
            vectorStoreProperties.getDocumentsToLoad().forEach(document -> {
                log.info("Loading document: " + document.getFilename());
                var pages = new PagePdfDocumentReader(document).get();
                store.add(pages);
                log.info("Loaded document added to local vector store");
            });

            store.save(vectorStoreFile);
        }

        return store;
    }
}
