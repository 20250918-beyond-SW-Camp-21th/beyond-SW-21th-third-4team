import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router' // 👈 1. 라우터 가져오기

const app = createApp(App)

app.use(router) // 👈 2. 앱에 라우터 장착!
app.mount('#app')