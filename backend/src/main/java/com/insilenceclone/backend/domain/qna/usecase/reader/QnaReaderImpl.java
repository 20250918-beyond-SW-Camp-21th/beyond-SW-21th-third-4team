package com.insilenceclone.backend.domain.qna.usecase.reader;

import com.insilenceclone.backend.common.exception.BusinessException;
import com.insilenceclone.backend.common.exception.ErrorCode;
import com.insilenceclone.backend.domain.qna.controller.dto.response.QnaListResponse;
import com.insilenceclone.backend.domain.qna.entity.Qna;
import com.insilenceclone.backend.domain.qna.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QnaReaderImpl implements QnaReader {

    private final QnaRepository repository;

    @Override
    public Qna readById(Long qnaId) {

        return repository.findById(qnaId)
                .orElseThrow(() -> new BusinessException(ErrorCode.QNA_NOT_FOUND));
    }

    @Override
    public List<Qna> readAllSorted() {
        return repository.findAllByOrderByTypeAscIdDesc();
    }

    // QnaReaderImpl 내부
    @Override
    public Qna readEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.QNA_NOT_FOUND));
    }
}
