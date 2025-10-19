package life.icetea.test.highapi;

import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

public class AiServicesTest {

    public static void main(String[] args) {
        String apiKey = System.getenv("MY_BAILIAN_API_KEY");
        OpenAiChatModel chatModel = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("qwen3-max")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();

        // AiServices会创建对应的实现类，使用传入的模型chatModel来进行调用
        ChatAssistant chatAssistant = AiServices.create(ChatAssistant.class, chatModel);

        String chat = chatAssistant.chat("你好，你是谁？ 简短回复一下");

        System.out.println(chat);
    }


}
