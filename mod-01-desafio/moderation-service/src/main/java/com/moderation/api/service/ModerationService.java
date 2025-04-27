package com.moderation.api.service;

import com.moderation.api.model.ModerationRequestDto;
import com.moderation.api.model.ModerationResponseDto;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ModerationService {

    private static final List<String> BANNED_WORDS = List.of("ódio", "xingamento", "php");

    public ModerationResponseDto moderate(ModerationRequestDto request) {
        String text = request.getText().toLowerCase();

        Set<String> bannedWordsFound = new HashSet<>();
        for (String word : BANNED_WORDS) {
            if (text.contains(word.toLowerCase())) {
                bannedWordsFound.add(word);
            }
        }

        if (!bannedWordsFound.isEmpty()) {
            return ModerationResponseDto.builder()
                    .approved(false)
                    .reason("Contém palavras proibidas: " + bannedWordsFound)
                    .build();
        }

        return ModerationResponseDto.builder()
                .approved(true)
                .reason("Comentário aprovado não palavras proibidas")
                .build();
    }
}
