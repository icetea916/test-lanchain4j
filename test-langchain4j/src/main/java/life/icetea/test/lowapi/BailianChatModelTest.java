package life.icetea.test.lowapi;

import dev.langchain4j.model.openai.OpenAiChatModel;

/**
 * 阿里百炼 model
 */
public class BailianChatModelTest {

    public static void main(String[] args) {
        String apiKey = System.getenv("MY_BAILIAN_API_KEY");
        OpenAiChatModel chatModel = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("qwen3-max")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();

        String response = chatModel.chat("你好,回复简短一下。");

        System.out.println(response);
    }

}