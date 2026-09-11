package com.lavalliere.daniel.spring7aisample3.service;

import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class AIChatService {

    private final SimpleVectorStore vectorStore;
    private final ChatClient chatClient;
    private final ChatMemory chatMemory;


    public String queryVectorStoreString(
        String question,
        String cid
    ) {

        // Persist the provided user message
        chatMemory.add(cid, new UserMessage(question));

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
        String prompt = """
        You are answering questions using only the context provided from the popular Pickering is Springfield book;
        If the answer is not in the context, say "I don't know, given the pages of the book I've read.
        Maybe ask me a different question ?"
        
        CONTEXT:
        %s
        
        QUESTION:
        %s
        """.formatted(augmentedContext, question);

        // log.info("Question prompt: " + prompt);

        // Retrieve the history associated with the provided cid
        var cidHistory = chatMemory.get(cid);

        log.info("cidHistory: cid: {} History: {}", cid, cidHistory);

        var generatedContent = chatClient.prompt(prompt).messages(cidHistory).call().content();

        // Persist the generated assistant message
        chatMemory.add(cid, new AssistantMessage(generatedContent));

        // Return the generated content
        return generatedContent;
    }
}
