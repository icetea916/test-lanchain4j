package life.icetea.test.springboot.config;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.azure.AzureOpenAiChatModel;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import life.icetea.test.highapi.ChatAssistant;
import life.icetea.test.highapi.SentimentAnalyzer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIModelConfig {

    @Bean
    public ChatModel openAiGPT4oChatModel() {
        String apiKey = System.getenv("MY_BAILIAN_API_KEY");
        return AzureOpenAiChatModel.builder()
                .endpoint("https://hackthon-open-ai-japan-east.openai.azure.com")
                .apiKey(apiKey)
                .deploymentName("gpt-4o")
                .serviceVersion("2025-01-01-preview")
                .build();
    }


    @Bean
    public ChatModel simpleChatModel() {
        String apiKey = System.getenv("MY_BAILIAN_API_KEY");
        return OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("qwen3-max")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();
    }

    @Bean
    public StreamingChatModel simpleStreamingChatModel() {
        return OpenAiStreamingChatModel.builder()
                .apiKey(System.getenv("MY_BAILIAN_API_KEY"))
                .modelName("qwen3-max")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();
    }

    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        return (userId) -> {
            return MessageWindowChatMemory.builder()
                    .id(userId)
                    .maxMessages(100)
                    .build();
        };
    }

    @Bean
    public ChatAssistant friendAssistant() {
        return AiServices.builder(ChatAssistant.class)
                .chatModel(simpleChatModel())
                // 配置stream model才AiServices可以使用stream功能
                .streamingChatModel(simpleStreamingChatModel())
                // 配置chat memory, 这样配置所有的AIService都会共用memory，如果想配置一个用户一个的话可以使用ChatMemoryProvider
//                .chatMemory(MessageWindowChatMemory.withMaxMessages(100))
                // 不同用户用自己的memory
                .chatMemoryProvider(chatMemoryProvider())
                // 修改chat request参数
                .chatRequestTransformer(chatRequest -> {
                    // 此处在发给LLM之前可以重写 chatRequest，例如设置 maxOutputTokens等，或者添加system message
                    return chatRequest;
                })
                .build();
    }

    @Bean
    public SentimentAnalyzer sentimentAnalyzer() {
        return AiServices.builder(SentimentAnalyzer.class)
                .chatModel(simpleChatModel())
                .build();
    }

}
