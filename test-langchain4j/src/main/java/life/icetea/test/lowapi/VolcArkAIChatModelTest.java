package life.icetea.test.lowapi;

import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;

/**
 * 简单调用 火山ark model
 */
public class VolcArkAIChatModelTest {

    public static void main(String[] args) {
        String apiKey = System.getenv("VOLCARK_API_KEY");
        OpenAiChatModel chatModel = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("doubao-seed-1-6-250615")
                .baseUrl("https://ark.cn-beijing.volces.com/api/v3")
                .build();

        String response = chatModel.chat("你好,回复简短一下。");
        System.out.println(response);

        // 流式响应
        StreamingChatModel streamingChatModel = OpenAiStreamingChatModel.builder()
                .apiKey(apiKey)
                .modelName("doubao-seed-1-6-250615")
                .baseUrl("https://ark.cn-beijing.volces.com/api/v3")
                .build();
//        streamingChatModel.chat("你好。", onPartialResponse(System.out::print));


    }

}