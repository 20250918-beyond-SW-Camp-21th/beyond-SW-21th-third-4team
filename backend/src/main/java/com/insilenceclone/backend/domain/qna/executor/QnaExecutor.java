package com.insilenceclone.backend.domain.qna.executor;

import com.insilenceclone.backend.domain.qna.controller.dto.request.QnaCreateRequest;
import com.insilenceclone.backend.domain.qna.controller.dto.response.QnaResponse;

public interface QnaExecutor {

    QnaResponse create(QnaCreateRequest req);
}
