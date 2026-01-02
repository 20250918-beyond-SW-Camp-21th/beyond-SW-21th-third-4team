package com.insilenceclone.backend.domain.qna.executor;

import com.insilenceclone.backend.domain.qna.controller.dto.request.QnaCreateRequest;
import com.insilenceclone.backend.domain.qna.controller.dto.response.QnaResponse;
import com.insilenceclone.backend.domain.qna.entity.Qna;
import com.insilenceclone.backend.domain.qna.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class QnaExecutorImpl implements QnaExecutor{

    private final QnaRepository repository;
    @Override
    public QnaResponse create(QnaCreateRequest req) {

        Qna qna = req.toEntity();

        Qna savedQna = repository.save(qna);

        return QnaResponse.from(savedQna);
    }
}
