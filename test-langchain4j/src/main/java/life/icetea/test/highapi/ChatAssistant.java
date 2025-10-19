package life.icetea.test.highapi;

import dev.langchain4j.service.*;
import dev.langchain4j.service.memory.ChatMemoryAccess;

import java.util.List;

/**
 * 自定义chat接口，最终有langchain4j 进行实现
 * <p>
 * ChatMemoryAccess 可以访问对应的memory聊天记录上下文并进行修改
 */
public interface ChatAssistant extends ChatMemoryAccess {

    // 基础聊天
    String chat(String message);

    // 带系统提示词
    @SystemMessage("You are a good friend of mine. Answer the user's question.")
    String chatWithSystemMessage(String userMessage);

    // 返回原始信息
    @UserMessage("Generate an outline for the article on the following topic: {{it}}")
    Result<List<String>> generateOutlineFor(String topic);

    // stream 流式调用
    @UserMessage("给我讲一个笑话！")
    TokenStream chatByStream();

    // 使用memory记录上下文
    @SystemMessage("你是个讲笑话的高手，每次讲笑话都要讲的不一样。而且很有水准，每次都能把人逗笑")
    String chatWithMemory(@UserMessage String userMessage, @MemoryId String memoryId);
}
