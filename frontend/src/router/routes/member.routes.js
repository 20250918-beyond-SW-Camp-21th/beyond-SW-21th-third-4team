import LoginView from "../../views/member/LoginView.vue"

export const memberRoutes = [
    {
        path: '/login',
        name: 'Login',
        component: LoginView,
    },
    // 회원가입 만들면 여기 추가
    // {
    //   path: '/signup',
    //   name: 'Signup',
    //   component: () => import('@/views/member/SignupPage.vue'),
    // },
]