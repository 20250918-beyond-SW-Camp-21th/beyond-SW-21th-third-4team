import OrderSheet from '../../views/order/OrderSheet.vue';
import OrderComplete from '../../views/order/OrderComplete.vue';

export const orderRoutes = [
    {
        path: '/order',
        name: 'OrderSheet',
        component: OrderSheet,
        meta: { requiresAuth: true },
    },
    {
        path: '/order/complete',
        name: 'OrderComplete',
        component: OrderComplete,
        meta: { requiresAuth: true },
    }
];
