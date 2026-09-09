package com.lavalliere.daniel.springaisample2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class AIChatService {

    public enum Voice { PIRATE, INSPIRATIONAL, TECH_BRO, IMPOSTER, MONK }
    public record TweetVariant(String tweet, Voice voice){}
    public record NewTweets(List<TweetVariant> tweets){}

    private final ChatClient chatClient;

    /*
    // First version of the prompt
    public static final String systemTemplate = """
        You are a tweet engine that does not use em-dashes and prefers to end sentences rather than use semi-colon.
        
        Goal:
        Given one of ORIGINAL_TWEET, produce five rewritten tweets, each in a different voice:
        1) PIRATE
        2) INSPIRATIONAL SPEAKER
        3) TECH_BRO
        4) SELF-DOUBTER with IMPOSTER syndrome
        5) MONK
        
        For the topicHint, use that information to steer the tweet about that topic
        
        Modernization:
        Refresh dated concepts to the degree specified by the MODERNIZATION_LEVEL

        Hard rules:
        - Output MUST contain exactly five tweets starting with the voice selected, one per voice and nothing else
        - Add emojis sporadically inside the text each tweet, per the EMOJI_LEVEL (low=1 or 2 emojis, med=3-6, high=8-10)
        """;
     */

    // Second version of the prompt
    public static final String systemTemplate = """
        You are a tweet rewrite engine that does not use em-dashes and prefers to stop a sentence with a period than use semi-colons.
        
        Goal:
        Given one of ORIGINAL_TWEET, produce five rewritten tweets, each in a different voice:
        1) PIRATE
        2) INSPIRATIONAL SPEAKER
        3) TECH_BRO
        4) SELF-DOUBTER with IMPOSTER syndrome
        5) MONK
        
        For the topicHint, use that information to steer the tweet about that topic
        
        Modernization:
        Apply MODERNIZATION_LEVEL to refresh dated concepts:
        - LOW: light refresh, minimal new references
        - MED: modern framing, may include  AI/ML/GenAI naturally
        - EXTREME: strongly modernize with AL/ML, Generative AI, Agentic AI and Cloud-native thinking while keeping the original meaning
        
        Hard rules:
        - Output MUST contain exactly five tweets, one per voice and nothing else
        - Each tweet must be a single tweet-style line, max 280 characters
        - Preserve the original intent and viewpoint, modernize examples/phrasing as needed
        - Integrate and inject emojis sporadically in to the text of each tweet per the EMOJI_LEVEL (low=1 or 2 emojis, med=3-6, high=8-10)
        - Never group more than 3 emojis together, never put subsequent emojis group next to another one but instead spread them out at the start, end and most importantly throughout each tweet
        - Do not invent personal claims (no fake achievements, job titles, customers or metrics)
        - Keep it readable and punchy, friendly and maybe sometimes funny
        
        Voice definitions:
        - PIRATE: pirate vibe, nautical metaphors, playful, occasional "arr"
        - INSPIRATIONAL: uplifting keynote speaker energy, encouraging, positive
        - TECH_BRO: startup/VC vibe, "ship/scale/iterate/10x" but readable
        - IMPOSTER: self-doubting individual with imposter syndrome but insightful, humble, ends hopeful 
        - MONK: calm, minimal, reflective, zen
        
        Output format:
        Return exactly 5 lines, in this exact order:
        - PIRATE 
        - INSPIRATIONAL
        - TECH_BRO
        - IMPOSTER 
        - MONK
        """;

    public static final String userTemplate = """
       ORIGINAL_TWEET:
       {postText}

       TOPIC_HINT:
       {topicInt}

       EMOJI_LEVEL:
       {emojiLevel}

       MODERNIZATION_LEVEL:
       {modernizationLevel}
       """;

    public NewTweets sendPrompt(
        String originalTweet,
        String topicInt,
        String emojiLevel,
        String modernizationLevel
        ) {
        // https://docs.spring.io/spring-ai/docs/current/api/org/springframework/ai/chat/prompt/PromptTemplate.html
        PromptTemplate promptTemplate = new PromptTemplate(userTemplate);
        String userPrompt = promptTemplate.render(Map.of(
            "postText", originalTweet,
            "topicInt", topicInt,
            "emojiLevel", emojiLevel,
            "modernizationLevel", modernizationLevel
        ));

        // Print the provided input
        // IO.println(userPrompt);
        log.info("Sending user prompt {}", userPrompt);

        // Used to format the output from standard 5 lines of text to a JSON formatted response ready to be sent
        BeanOutputConverter<NewTweets> converter  = new BeanOutputConverter<>(NewTweets.class);
        log.info("Converted user prompt {}", converter.getFormat());

        // In this example you  are setting the User message part of the prompt
        return chatClient.prompt().user(userPrompt).call().entity(converter);
    }


}
