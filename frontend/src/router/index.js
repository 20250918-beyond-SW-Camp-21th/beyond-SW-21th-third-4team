import { createRouter, createWebHistory } from 'vue-router'
import {mainRoutes} from "./routes/main.routes.js"
import {memberRoutes} from "./routes/member.routes.js";
// TODO: router import

const router = createRouter({
    history: createWebHistory(),
    routes: [
        // TODO: routes 추가
        ...memberRoutes,
        ...memberRoutes
    ]
})

export default router
