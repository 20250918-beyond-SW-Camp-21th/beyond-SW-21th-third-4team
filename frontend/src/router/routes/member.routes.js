import LoginView from "../../views/member/LoginView.vue"
import SignupTermsView from "../../views/member/SignupTermsView.vue";
import SignupView from "../../views/member/SignupView.vue";
import MyPage from "../../views/member/mypage/MyPage.vue";
import OrderListPage from "../../views/member/mypage/OrderListPage.vue";
import ProfileEditPage from "../../views/member/mypage/ProfileEditPage.vue";

export const memberRoutes = [
    // 로그인
    {
        path: '/login',
        name: 'Login',
        component: LoginView,
        meta: { guestOnly: true },
    },
    // 회원가입 1단계: 약관
    {
        path: '/signup/terms',
        name: 'SignupTerms',
        component: SignupTermsView,
        meta: { guestOnly: true },
    },

    // 회원가입 2단계: 정보 입력
    {
        path: '/signup',
        name: 'Signup',
        component: SignupView,
        meta: { guestOnly: true },
    },

    // 마이페이지
    {
        path: '/mypage',
        name: 'MyPage',
        component: MyPage,
        meta: { requiresAuth: true },
    },

    // 주문조회
    {
        path: '/mypage/orders',
        name: 'OrderList',
        component: OrderListPage,
        meta: { requiresAuth: true },
    },

    // 회원정보 수정
    {
        path: '/mypage/profile',
        name: 'ProfileEdit',
        component: ProfileEditPage,
        meta: { requiresAuth: true },
    },
]