package com.insilenceclone.backend.domain.qna.controller;

import com.insilenceclone.backend.common.response.ApiResponse;
import com.insilenceclone.backend.domain.qna.controller.dto.request.QnaAnswerRequest;
import com.insilenceclone.backend.domain.qna.controller.dto.request.QnaCreateRequest;
import com.insilenceclone.backend.domain.qna.controller.dto.request.QnaRequest;
import com.insilenceclone.backend.domain.qna.controller.dto.response.QnaListResponse;
import com.insilenceclone.backend.domain.qna.controller.dto.response.QnaResponse;
import com.insilenceclone.backend.domain.qna.service.QnaService;
import com.insilenceclone.backend.domain.user.security.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/qnas")
public class QnaController {

    private final QnaService service;

    // 게시글 목록 조회
    @GetMapping
    public ApiResponse<List<QnaListResponse>> getQanList() {
        return ApiResponse.success(service.getQnaList());
    }

    // 게시글 단건 조회
    @GetMapping("/{qnaId}")
    public ApiResponse<QnaResponse> getQnaById(
            @PathVariable Long qnaId,
            @AuthenticationPrincipal CustomUser user
    ) {
        return ApiResponse.success(service.getQna(new QnaRequest(qnaId, user.getId())));
    }

    // 게시글 작성
    @PostMapping
    public ApiResponse<QnaResponse> createQna(
            @RequestBody @Validated QnaCreateRequest req,
            @AuthenticationPrincipal CustomUser user
    ) {
        return ApiResponse.success(service.createQna(req, user.getId()));
    }

    // 답변 등록
    @PostMapping("/{qnaId}/answer")
    public ApiResponse<Void> answer(@RequestBody @Validated QnaAnswerRequest req) {

        service.answerQna(req);
        return ApiResponse.success();
    }
}
