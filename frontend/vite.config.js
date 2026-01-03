import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
    // https://vite.dev/config/
    plugins: [vue()],
    server: {
        proxy: {
            // 프론트에서 /api로 시작하는 요청을 보내면 백엔드로 배달해줌
            '/api': {
                target: 'http://localhost:5050', // 👈 여기에 백엔드 주소/포트 입력
                changeOrigin: true,
                secure: false,
                // 필요에 따라 rewrite 설정 (현재 백엔드 주소에 /api가 포함되어 있으므로 그대로 둠)
                rewrite: (path) => path
            }
        }
    }
})