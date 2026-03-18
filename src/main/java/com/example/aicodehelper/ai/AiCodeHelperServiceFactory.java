package com.example.aicodehelper.ai;

import com.example.aicodehelper.ai.tools.InterviewQuestTool;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 手动声明bean
 * AiService创建器
 */
@Configuration
public class AiCodeHelperServiceFactory {

    @Resource
//    private ChatModel qwenChatModel;
    private ChatModel myQwenChatModel;

    //引入自定义RAG
    @Resource
    private ContentRetriever contentRetriever;

    // 引入mcp工具
    @Resource
    private McpToolProvider mcpToolProvider;

    // 引入流式输出
    @Resource
    private StreamingChatModel qwenStreamingChatModel;

    /**
     * 调用AIService.create创建AI service的实现类，背后原理利用了Java反射机制创建了一个实现接口的代理对象，
     * 代理对象负责输入输出的转换。
     * @return
     */
    @Bean
    public AiCodeHelperService aiCodeHelperService() {
        // 会话记忆:https://docs.langchain4j.dev/tutorials/chat-memory
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

        // 构造AIService
//        return AiServices.create(AiCodeHelperService.class, qwenChatModel);
        return AiServices.builder(AiCodeHelperService.class)
                .chatModel(myQwenChatModel)
                .streamingChatModel(qwenStreamingChatModel) // 流式输出
                .chatMemory(chatMemory)
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(10)) // 会话记忆配置根据会话id实现会话隔离
                .contentRetriever(contentRetriever) // RAG 检索增强生成
                .tools(new InterviewQuestTool()) // 工具调用
                .toolProvider(mcpToolProvider) // MCP工具调用
                .build();
    }
}
