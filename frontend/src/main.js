import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router' // 👈 1. 라우터 가져오기
import axios from 'axios' // 👈 추가

// 모든 axios 요청에 쿠키(세션)를 포함시킴
axios.defaults.baseURL = 'http://localhost:8080';
axios.defaults.withCredentials = true; // 👈 이거 없으면 백엔드에서 로그인 인식 못함

const app = createApp(App)

app.use(router) // 👈 2. 앱에 라우터 장착!
app.mount('#app')