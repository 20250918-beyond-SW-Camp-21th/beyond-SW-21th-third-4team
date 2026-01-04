import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router'
import axios from 'axios'

// 모든 axios 요청에 쿠키(세션)를 포함시킴
axios.defaults.baseURL = 'http://localhost:8080';
axios.defaults.withCredentials = true; // 백엔드 로그인 정보 전달

const app = createApp(App)

app.use(router)
app.mount('#app')