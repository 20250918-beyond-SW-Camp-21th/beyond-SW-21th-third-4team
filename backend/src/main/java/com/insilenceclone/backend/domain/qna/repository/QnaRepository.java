package com.insilenceclone.backend.domain.qna.repository;

import com.insilenceclone.backend.domain.qna.controller.dto.response.QnaListResponse;
import com.insilenceclone.backend.domain.qna.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QnaRepository extends JpaRepository<Qna,Long> {

    List<QnaListResponse> findAllByOrderByTypeAscIdDesc();

    Long findUserIdByQnaId(Long userId);
}
