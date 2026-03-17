package com.example.aicodehelper.ai.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 定义工具
 * 当前类为搜索指定网址获取结果
 * 还可以定义诸如文件处理、文件读写、PDF生成、调用终端、输出图表等工具
 */
@Slf4j
public class InterviewQuestTool {

    /**
     * 获取面试题
     * @param keyword
     * @return
     */
    @Tool(name = "interviewQuestionSearch", value = """
            Retrieves relevant interview questions from mianshiya.com based on a keyword.
            Use this tool when the user asks for interview questions about specific technologies,
            programming concepts, or job-related topics. The input should be a clear search term.
            """)
    public String searchInterviewQuestions(@P(value = "the keyword search") String keyword) {
        List<String> questions = new ArrayList<>();
        // 构建搜索url
        String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
        // 发送请求并解析页面
        Document doc;
        try {
            doc = Jsoup.connect("https://www.mianshiya.com/search/all?searchText=" + encodedKeyword)
                    .userAgent("Mozilla.5.0")
                    .timeout(5000)
                    .get();
        } catch (IOException e) {
            log.error("请求失败", e);
            throw new RuntimeException(e);
        }
        // 提取
        Elements questionsElements = doc.select(".ant-table-cell > a");
        questionsElements.forEach(element -> questions.add(element.text()));
        return String.join("\n", questions);
    }
}
