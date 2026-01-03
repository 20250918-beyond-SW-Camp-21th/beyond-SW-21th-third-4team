import ProductDetail from '../../views/product/ProductDetail.vue';

export const productRoutes = [
    {
        path: '/product/:productId',
        name: 'ProductDetail',
        component: ProductDetail,
    },
];
