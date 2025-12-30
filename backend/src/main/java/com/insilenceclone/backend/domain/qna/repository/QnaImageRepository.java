package com.insilenceclone.backend.domain.qna.repository;

import com.insilenceclone.backend.domain.qna.entity.QnaImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QnaImageRepository extends JpaRepository<QnaImage,Integer> {

}
