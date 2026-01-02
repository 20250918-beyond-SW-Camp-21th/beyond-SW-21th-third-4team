// src/router/routes/cart.routes.js
import CartView from '../../views/cart/CartView.vue'

export const cartRoutes = [
    {
        path: '/cart',
        name: 'cart',
        component: CartView,
        meta: { requiresAuth: true },
    },
]
