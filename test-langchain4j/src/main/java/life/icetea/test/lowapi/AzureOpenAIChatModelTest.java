package life.icetea.test.lowapi;

import dev.langchain4j.model.azure.AzureOpenAiChatModel;

public class AzureOpenAIChatModelTest {

    public static void main(String[] args) {
        // 获取环境变量
        String apiKey = System.getenv("AZURE_OPENAI_API_KEY");

        AzureOpenAiChatModel chatModel = AzureOpenAiChatModel.builder()
                .apiKey(apiKey)
                .endpoint("https://hackthon-open-ai-japan-east.openai.azure.com/")
                .deploymentName("gpt-4o")
                .serviceVersion("2025-01-01-preview")
                .build();

        String response = chatModel.chat("你好,回复简短一下。");

        System.out.println(response);
    }

}