// src/router/routes/qna.routes.js

export const qnaRoutes = [
    {
        path: '/qna',
        name: 'QnaMain',
        component: () => import('../../views/qna/Qna.vue'), // 실제 경로에 맞게 수정
        meta: {
            requiresAuth: false // 목록은 비로그인도 볼 수 있게 설정 (필요시 true로 변경)
        }
    },
    {
        path: '/notice',
        name: 'NoticeMain',
        component: () => import('../../views/qna/Qna.vue'),
        props: {isNoticeMode: true} // 공지사항 전용 모드
    }
]