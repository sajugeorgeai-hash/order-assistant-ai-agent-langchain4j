package com.saj.controller;

import com.saj.service.GeneralAssistant;
import com.saj.service.OrderAiAssistant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai-direct")
public class AiSupportController {

    private final GeneralAssistant generalAssistant;
    private final OrderAiAssistant orderAiAssistant;

    public AiSupportController(
            GeneralAssistant generalAssistant,
            OrderAiAssistant orderAiAssistant) {
        this.generalAssistant = generalAssistant;
        this.orderAiAssistant = orderAiAssistant;
    }

    @GetMapping
    public String chat(
            @RequestParam String message) {
        return generalAssistant.chat(message);
    }

    @GetMapping("/order-support")
    public String orderSupport(
            @RequestParam String message) {
        return orderAiAssistant.chat(message);
    }

}
