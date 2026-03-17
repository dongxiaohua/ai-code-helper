package com.example.aicodehelper.ai.guardrail;


import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.guardrail.InputGuardrail;
import dev.langchain4j.guardrail.InputGuardrailResult;
import dev.langchain4j.service.guardrail.InputGuardrails;

import java.util.Set;

@InputGuardrails({SafeInputGuardrail.class})
public class SafeInputGuardrail implements InputGuardrail {

    // 敏感词集合
    private static final Set<String> SENSITIVE_WORDS = Set.of("password", "credit card", "social security number");

    @Override
    public InputGuardrailResult validate(UserMessage userMessage) {
        String inputText = userMessage.singleText().toLowerCase();
        String[] words = inputText.split("\\s+");
        for (String word : words) {
            if (SENSITIVE_WORDS.contains(word)) {
                return fatal("敏感词：" + word);
            }
        }
        return success();
    }

}
