import { createRouter, createWebHistory } from 'vue-router'
import { mainRoutes } from "./routes/main.routes.js"
import { memberRoutes } from "./routes/member.routes.js";
import { orderRoutes } from "./routes/order.routes.js";
import { cartRoutes } from './routes/cart.routes.js'

// TODO: router import

const router = createRouter({
    history: createWebHistory(),
    routes: [
        // TODO: routes 추가
        ...mainRoutes,
        ...memberRoutes,
        ...orderRoutes,
        ...cartRoutes,
    ]
})

export default router
