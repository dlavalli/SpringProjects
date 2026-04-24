package com.lavalliere.daniel.spring.springairag.services;

import com.lavalliere.daniel.spring.springairag.model.Answer;
import com.lavalliere.daniel.spring.springairag.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.document.Document;

import java.util.List;
import java.util.Map;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatModel chatModel;
    private final SimpleVectorStore vectorStore;

    // @Value("classpath:/templates/rag-prompt-template.st")
    @Value("classpath:/templates/rag-prompt-template-meta.st")
    private     Resource ragPromptTemplate;

    public OpenAIServiceImpl(
        @Qualifier("ollamaChatModel") ChatModel chatModel,
        SimpleVectorStore vectorStore
    ) {
        this.chatModel = chatModel;
        this.vectorStore = vectorStore;
    }

//    @Override
//    public Answer getAnswer(Question question) {
//        PromptTemplate promptTemplate = new PromptTemplate(question.question());
//        Prompt prompt = promptTemplate.create();
//        ChatResponse response = chatModel.call(prompt);
//        return new Answer(response.getResult().getOutput().getText());
//    }



    /*
      Question:   Why does querying a mistral model from a local docker image based ollama instance using Spring AI,
                  take about 45 seconds to reply when there is only 10 documents locally provisioned

      Answer:
                  The 45-second delay is usually not caused by having only 10 documents; it is more often caused by model load time,
                  CPU/RAM offloading, Docker I/O, or request-time timeouts in the local Ollama/Spring AI stack. With local Ollama,
                  the first request after a cold start can easily take tens of seconds because the model must be loaded into memory,
                  and Docker Desktop storage can make that noticeably slower.

                  Most likely causes Cold model load. Ollama may be loading the Mistral weights into RAM/VRAM on the first request,
                  which can take around 13–46 seconds depending on model size and hardware. CPU or RAM fallback. If VRAM is insufficient
                  or Ollama mis-estimates GPU memory, it can offload layers to system RAM or even CPU, which makes generation much slower.
                  Docker storage overhead. When Ollama runs in Docker, especially with volumes backed by slower host-mounted storage,
                  model loading can be much slower than expected.

                  Model eviction between calls. If keep_alive is not configured, Ollama may unload the model after inactivity and reload it
                  on the next request, recreating the long startup delay.

                  Timeout/conf mismatch. Spring AI and related clients can have request timeout settings that mask whether you are waiting
                  on retrieval, model loading, or generation.

      What to check first:
                  Measure the time for retrieval and generation separately, so you can see whether the 45 seconds is in vector search, prompt assembly, or LLM response time.
                  Check whether Ollama is reloading the model on every call; if yes, set a longer keep_alive or keep_alive: -1 for development.
                  Verify whether the model is actually using GPU acceleration, because CPU-only or heavy RAM offload can make a local Mistral model feel very slow.
                  If you are using Docker Desktop, test the same model outside Docker or with a Docker-managed volume to see whether I/O is the bottleneck.
                  Increase Spring AI/Ollama request timeouts only after you confirm the real bottleneck, because that hides the symptom rather than fixing the cause.
     */
    @Override
    public Answer getAnswer(Question question) {

        List<Document> documents = vectorStore.similaritySearch(
            SearchRequest.builder().query(
                question.question()
            ).topK(5)  // Restrict the search size: Note: depending on number of match, since only loading 5 here, the one seeked may not be loaded
             .build()
        );
        List<String> contentList = documents.stream().map(Document::getText).toList();

        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);
        Prompt prompt = promptTemplate.create(Map.of("input", question.question(), "documents",
            String.join("\n", contentList)));

        // contentList.forEach(System.out::println);

        // Long to execute. Takes around 45s locally to get the answer
        ChatResponse response = chatModel.call(prompt);

        Answer answer = new Answer(response.getResult().getOutput().getText());

        return answer;
    }
}
