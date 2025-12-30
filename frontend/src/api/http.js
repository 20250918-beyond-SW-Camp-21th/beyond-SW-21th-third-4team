import axios from 'axios'
import { getToken, removeToken} from "../utils/token.js"

const API_PREFIX = '/api/v1'

const http = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL,
    headers: { 'Content-Type' : 'application/json'},
    timeout: 10000,
})

http.interceptors.request.use((config) => {
    const url = config.url || ''

    // 1. prefix 자동 적용 (중복 방지)
    if (!url.startsWith(API_PREFIX)) {
        config.url = `${API_PREFIX}${url.startsWith("/") ? '' : '/'}${url}`
    }
    // 2) 토큰 자동 첨부
    const token = getToken()
    if (token) {
        config.headers.Authorization = `Bearer ${token}`
    }

    return config
})

// 응답 인터셉터: 401이면 토큰 삭제 (필요하면 로그인으로 이동)
http.interceptors.response.use(
    (res) => res,
    (err) => {
        if (err.response?.status === 401) {
            removeToken()
            // window.location.href = '/login'
            // 라우터 세팅 후
            // router.push('/login')
        }
        return Promise.reject(err)
    }
)

export default http