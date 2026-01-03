import { createRouter, createWebHistory } from 'vue-router'
import { mainRoutes } from "./routes/main.routes.js"
import { memberRoutes } from "./routes/member.routes.js";
import { orderRoutes } from "./routes/order.routes.js";
import { cartRoutes } from './routes/cart.routes.js'
import { productRoutes } from './routes/product.routes.js'
import { getToken } from '../utils/token.js'

// TODO: router import

const router = createRouter({
    history: createWebHistory(),
    routes: [
        // TODO: routes 추가
        ...mainRoutes,
        ...memberRoutes,
        ...orderRoutes,
        ...cartRoutes,
        ...productRoutes,
    ]
})

// 🔐 전역 라우터 가드
router.beforeEach((to, from, next) => {
    const token = getToken()

    const requiresAuth = to.matched.some(
        route => route.meta.requiresAuth
    )

    const guestOnly = to.matched.some(
        route => route.meta.guestOnly
    )

    // 1️⃣ 로그인 필요한 페이지인데 토큰 없음
    if (requiresAuth && !token) {
        alert('로그인이 필요합니다.')
        next('/login')
        return
    }

    // 2️⃣ 이미 로그인 했는데 로그인/회원가입 페이지 접근
    if (guestOnly && token) {
        next('/') // 또는 '/cart'
        return
    }

    next()
})

export default router