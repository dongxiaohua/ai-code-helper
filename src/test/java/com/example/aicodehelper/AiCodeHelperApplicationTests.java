package com.example.aicodehelper;

import com.example.aicodehelper.ai.AiCodeHelper;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AiCodeHelperApplicationTests {

    @Resource
    private AiCodeHelper aiCodeHelper;

    @Test
    void chat() {
        aiCodeHelper.chat("我是Java程序员，dxh");
    }

    @Test
    void testChat() {
        UserMessage userMessage = UserMessage.from(
                TextContent.from("我是Java程序员，dxh"),
                ImageContent.from("https://www.codefather.cn/logo.png"));

        aiCodeHelper.chat(userMessage);
    }
}
