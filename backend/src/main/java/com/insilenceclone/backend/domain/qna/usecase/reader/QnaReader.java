package com.insilenceclone.backend.domain.qna.usecase.reader;

import com.insilenceclone.backend.domain.qna.controller.dto.request.QnaRequest;
import com.insilenceclone.backend.domain.qna.controller.dto.response.QnaListResponse;
import com.insilenceclone.backend.domain.qna.controller.dto.response.QnaResponse;
import com.insilenceclone.backend.domain.qna.entity.Qna;

import java.util.List;

public interface QnaReader {

    Qna readById(Long qnaId);

    List<Qna> readAllSorted();

    // QnaReaderImpl 내부
    Qna readEntityById(Long id);
}
