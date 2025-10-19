package life.icetea.test.imagecall;

import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.openai.OpenAiChatModel;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class ImageCallTest {

    /**
     * @param args
     * @retrun InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // chatmodel配置
        String apiKey = System.getenv("MY_BAILIAN_API_KEY");
        OpenAiChatModel chatModel = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("qwen-vl-max")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();

        // 图片转base64
        InputStream resourceAsStream = ImageCallTest.class.getResourceAsStream("~/test-img.jpg");
        BufferedInputStream bufferedInputStream = new BufferedInputStream(resourceAsStream);
        byte[] bytes = bufferedInputStream.readAllBytes();
        String base64Image = Base64.getEncoder().encodeToString(bytes);

        // 提示词指定
        UserMessage usermessage = UserMessage.from(
                TextContent.from("请分析一下下面图片："),
                ImageContent.from(base64Image, "image/jpg"));

        ChatResponse chatResponse = chatModel.chat(usermessage);
        System.out.println(chatResponse.aiMessage().text());
        /**
         * response demo
         * 这是一张充满秋日韵味的中国传统园林景观照片，展现了典型的中国古典建筑与自然美景的和谐融合。以下是对图片的详细分析：
         *
         * ### 1. **季节与氛围**
         * - **秋季特征**：画面中树木的叶子呈现出鲜艳的橙红、金黄等色彩，是典型的秋季景象。树叶部分飘落在水面上，形成“落叶浮水”的诗意画面。
         * - **天气晴朗**：天空湛蓝无云，阳光明媚，光线充足，增强了色彩的饱和度和整体画面的明亮感。
         *
         * ### 2. **建筑风格**
         * - **传统中式建筑**：背景中的建筑具有典型的中国古代宫殿或寺庙风格，红色墙体、灰色瓦顶、飞檐翘角，体现了中国传统建筑的庄重与典雅。
         * - **牌匾与文字**：右侧建筑上有一块红色横幅，上面写有汉字，虽然具体文字略模糊，但可推测为景区名称或宣传标语（如“颐和园”或类似景点）。
         *
         * ### 3. **桥梁设计**
         * - **石拱桥**：画面中央是一座精美的单孔石拱桥，桥身由白色石材构成，桥栏上有雕刻装饰，造型优美，具有典型的江南园林风格。
         * - **倒影效果**：桥与周围景物在水中形成清晰倒影，增强了画面的对称美感和宁静氛围。
         *
         * ### 4. **自然元素**
         * - **树木**：前景和中景有多棵高大的枫树或其他落叶乔木，枝叶繁茂，色彩浓烈，成为画面的视觉焦点。
         * - **水面**：池塘水面平静，漂浮着大量落叶，反射出天空、树木和桥梁的影像，营造出静谧而富有诗意的意境。
         *
         * ### 5. **人物活动**
         * - **游客**：桥上及岸边有一些游客在漫步、拍照，显示出这是一个受欢迎的旅游景点。人们的存在为画面增添了生活气息，也衬托出景色的吸引力。
         *
         * ### 6. **构图与色彩**
         * - **构图**：采用对称式构图，以桥为中心轴线，两侧建筑和树木相互呼应，形成平衡稳定的视觉效果。
         * - **色彩对比**：橙红、金黄的秋叶与蓝天、白桥、红墙形成强烈对比，视觉冲击力强，同时又不失和谐统一。
         *
         * ### 7. **可能的地点**
         * 根据建筑风格、植被类型以及整体布局，这张照片很可能拍摄于中国北京的**颐和园**或**香山公园**等著名皇家园林或风景区，这些地方在秋季尤为著名，是观赏红叶的热门地点。
         *
         * ### 总结
         * 这张照片完美捕捉了中国传统园林在秋季的独特魅力，将自然之美与人文景观融为一体，展现了中华文化中“天人合一”的审美理念。无论是色彩、光影还是构图，都极具艺术感染力，令人感受到秋天的绚烂与宁静。
         */

    }

}
