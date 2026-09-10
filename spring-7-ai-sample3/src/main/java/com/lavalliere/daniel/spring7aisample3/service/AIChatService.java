package com.lavalliere.daniel.spring7aisample3.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class AIChatService {

    private final SimpleVectorStore vectorStore;
    private final ChatClient chatClient;


    public String queryVectorStoreString(String question) {

        // Get OpenAI to generate a query that the vector store database can work with
        var retrievalQuery = SearchRequest.builder()
            .query(question)
            .topK(5)  // This is an instruction to the vector database to dictate how many
                      // nearest-neighbor documents should be returned during the similarity search.
                      // This NOT a call to OpenAI's LLM generation parameter top-k (not supported by most models)
                      // here, OpenAI simply create the actual query to send to the vector database
            .build();

        var retrievedPages = vectorStore.similaritySearch(retrievalQuery);
        String augmentedContext = retrievedPages.stream()
            .limit(5)
            .map(Document::getText)
            .collect(Collectors.joining(", Page from the book: "));

        // log.info("Augmented context: " + augmentedContext);

        String questionPrompt = """
        You are answering questions using only the context provided from the popular Pickering is Springfield book;
        If the answer is not in the context, say "I don't know, given the pages of the book I've read.
        Maybe ask me a different question ?"
        
        CONTEXT:
        %s
        
        QUESTION:
        %s
        """.formatted(augmentedContext, question);

        // log.info("Question prompt: " + questionPrompt);

        // Return the generated content
        return chatClient.prompt(questionPrompt).call().content();
    }
}
