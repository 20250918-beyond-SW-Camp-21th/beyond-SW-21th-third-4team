import http from '../api/http'

export const login = async (loginId, password) => {
    return http.post('/auth/login', { loginId, password })
}
