import LoginView from "../../views/member/LoginView.vue"
import SignupTermsView from "../../views/member/SignupTermsView.vue";
import SignupView from "../../views/member/SignupView.vue";

export const memberRoutes = [
    // 로그인
    {
        path: '/login',
        name: 'Login',
        component: LoginView,
    },
    // 회원가입 1단계: 약관
    {
        path: '/signup/terms',
        name: 'SignupTerms',
        component: SignupTermsView,
    },

    // 회원가입 2단계: 정보 입력
    {
        path: '/signup',
        name: 'Signup',
        component: SignupView,
    },
]