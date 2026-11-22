package com.saj.service;


import dev.langchain4j.service.spring.AiService;

@AiService
public interface GeneralAssistant {

    String chat(String message);
}