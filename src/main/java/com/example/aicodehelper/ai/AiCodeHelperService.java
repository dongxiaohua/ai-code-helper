package com.example.aicodehelper.ai;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

import java.util.List;

/**
 * AIService
 *
 * @author luoxiaolong
 */
//@AiService
public interface AiCodeHelperService {


    /**
     *
     * @param memoryId 会话id，为了会话隔离
     * @param userMessage
     * @return
     */
    @SystemMessage(fromResource = "static/system-prompt.txt")
//    String chat(@MemoryId int memoryId, String userMessage);
    String chat(String userMessage);


    /**
     * 结构化输出 - record
     * @param userMessage
     * @return
     */
    @SystemMessage(fromResource = "static/system-prompt.txt")
     Report chatForReport(String userMessage);

    record Report(String name, List<String> suggestionList) {}

    /**
     *  简单对话 RAG来源
     * @param userMessage
     * @return
     */
    @SystemMessage(fromResource = "static/system-prompt.txt")
    Result<String> chatWithRag(String userMessage);


    /**
     * 流式输出
     * @param message
     * @return
     */
    @SystemMessage(fromResource = "static/system-prompt.txt")
    Flux<String> chatStream(int memoryId, String message);

}
