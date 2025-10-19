package life.icetea.test.springboot.controller;

import com.alibaba.fastjson2.JSONArray;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.service.Result;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.tool.BeforeToolExecution;
import dev.langchain4j.service.tool.ToolExecution;
import jakarta.annotation.Resource;
import life.icetea.test.highapi.ChatAssistant;
import life.icetea.test.highapi.SentimentAnalyzer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/test/ai")
public class TestAzureOpenAIController {

    @Resource
    private ChatModel openAiGPT4oChatModel;
    @Resource
    private ChatAssistant chatAssistant;
    @Resource
    private SentimentAnalyzer sentimentAnalyzer;

    // low-level model调用
    @RequestMapping("/gpt-4o")
    public String testGpt4o() {
        // 调用 Azure OpenAI GPT-4o 模型
        String response = openAiGPT4oChatModel.chat("你好");
        return response;
    }

    // 简单chat调用
    @RequestMapping("/friend-assistant")
    public String testFriendAssistant(String message) {
        // 调用 FriendAssistant 模型
        String response = chatAssistant.chatWithSystemMessage(message);
        return response;
    }

    // 获取原始返回数据
    @RequestMapping("/friend-assistant-outline")
    public Result testFriendAssistantOutline(String topic) {
        // 调用 FriendAssistant 模型
        Result<List<String>> response = chatAssistant.generateOutlineFor(topic);
        return response;
    }

    // 意图识别，格式化输出
    @RequestMapping("/friend-assistant-sentiment")
    public boolean testFriendAssistantSentiment(String text) {
        boolean response = sentimentAnalyzer.isPositive(text);
        return response;
    }

    // stream 流式调用
    @RequestMapping("/friend-assistant-stream")
    public String testFriendAssistantStream() {
        // 调用 FriendAssistant 模型
        TokenStream tokenStream = chatAssistant.chatByStream();

        CompletableFuture<ChatResponse> futureResponse = new CompletableFuture<>();

        // 处理 TokenStream 响应
        StringBuilder responseBuilder = new StringBuilder();
        tokenStream.onPartialResponse((String partialResponse) -> System.out.println(partialResponse))
//                .onPartialThinking((PartialThinking partialThinking) -> System.out.println(partialThinking))
//                .onRetrieved((List<Content> contents) -> System.out.println(contents))
                .onIntermediateResponse((ChatResponse intermediateResponse) -> System.out.println(intermediateResponse))
                // This will be invoked every time a new partial tool call (usually containing a single token of the tool's arguments) is available.
//                .onPartialToolCall((PartialToolCall partialToolCall) -> System.out.println(partialToolCall))
                // This will be invoked right before a tool is executed. BeforeToolExecution contains ToolExecutionRequest (e.g. tool name, tool arguments, etc.)
                .beforeToolExecution((BeforeToolExecution beforeToolExecution) -> System.out.println(beforeToolExecution))
                // This will be invoked right after a tool is executed. ToolExecution contains ToolExecutionRequest and tool execution result.
                .onToolExecuted((ToolExecution toolExecution) -> System.out.println(toolExecution))
                .onCompleteResponse((ChatResponse response) -> futureResponse.complete(response))
                .onError((Throwable error) -> futureResponse.completeExceptionally(error))
                .start();

        futureResponse.join();

        return responseBuilder.toString();
    }

    // 带memory conversation
    @RequestMapping("/friend-assistant-memory")
    public JSONArray testFriendAssistantMemory(@RequestParam(required = false) String message) {
        String userId = "icetea";
        String response = chatAssistant.chatWithMemory((message != null && !message.isEmpty()) ? message : "给我讲个笑话", userId);

        // 通过 ChatMemoryAccess 可以访问对应的memory聊天记录上下文并进行修改
        ChatMemory chatMemory = chatAssistant.getChatMemory(userId);
        List<String> list = chatMemory.messages().stream()
                .map(msg -> msg.toString())
                .toList();

        return JSONArray.from(list);
    }

}
