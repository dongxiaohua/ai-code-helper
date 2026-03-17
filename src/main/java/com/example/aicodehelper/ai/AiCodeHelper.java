package com.example.aicodehelper.ai;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AiCodeHelper {

    @Resource
    private ChatModel qwenChatModel;

    // 系统提示词
    private static final String SYSTEM_PROMPT = """
            你是编程领域的小助手，帮助用户解答编程学习相关的问题，并给出建议。
            """;

    /**
     * AI对话 - ChatModel
     * @param message
     * @return
     */
    public String chat(String message) {
        // 系统提示词
        SystemMessage systemMessage = SystemMessage.from(SYSTEM_PROMPT);

        UserMessage userMessage = UserMessage.from(message);
        // ChatResponse chatResponse = qwenChatModel.chat(userMessage);
        ChatResponse chatResponse = qwenChatModel.chat(systemMessage, userMessage);
        AiMessage aiMessage = chatResponse.aiMessage();
        log.info("AI 输出：" + aiMessage.toString());
        return aiMessage.text();
    }


    /**
     * 多模态 - 用户消息多样化，消息、图片、视频
     * qwen不支持图片的多模态，想处理图片，需要选择支持多模态的大模型
     *
     * @param userMessage
     * @return
     */
    public String chat(UserMessage userMessage) {
        ChatResponse chatResponse = qwenChatModel.chat(userMessage);
        AiMessage aiMessage = chatResponse.aiMessage();
        log.info("AI 输出：" + aiMessage.toString());
        return aiMessage.text();
    }

}
