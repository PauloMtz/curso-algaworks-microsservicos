package com.comment.api.client;

import com.comment.api.model.ModerationRequest;
import com.comment.api.model.ModerationResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface ModerationClient {

    @PostExchange("/api/moderate")
    ModerationResponse moderateComment(@RequestBody ModerationRequest request);
}
