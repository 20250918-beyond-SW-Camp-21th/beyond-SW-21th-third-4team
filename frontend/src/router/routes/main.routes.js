import HomeView from "../../views/main/HomeView.vue";
import ProductListView from "../../views/product/ProductListView.vue";

export const mainRoutes = [
    {
        path: '/',
        name: 'Home',
        component: HomeView,
    },

    {
        path: '/product/list',     // 주소창에 이렇게 치면
        name: 'ProductList',       // (이름은 자유)
        component: ProductListView // 이 화면을 보여줘라
    },

]