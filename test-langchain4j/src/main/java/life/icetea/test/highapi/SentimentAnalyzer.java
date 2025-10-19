package life.icetea.test.highapi;

import dev.langchain4j.service.UserMessage;

public interface SentimentAnalyzer {

    @UserMessage("Does {{it}} has a positive sentiment?， ")
    boolean isPositive(String text);

}