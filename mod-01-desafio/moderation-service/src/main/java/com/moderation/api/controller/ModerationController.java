package com.moderation.api.controller;

import com.moderation.api.model.ModerationRequestDto;
import com.moderation.api.model.ModerationResponseDto;
import com.moderation.api.service.ModerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/moderate")
@RequiredArgsConstructor
public class ModerationController {

    private final ModerationService moderationService;

    @PostMapping
    public ModerationResponseDto moderate(@RequestBody ModerationRequestDto request) {
        return moderationService.moderate(request);
    }
}
