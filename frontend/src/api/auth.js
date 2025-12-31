import http from '../api/http'

export const login = async (loginId, password) => {
    return http.post('/auth/login', { loginId, password })
}

export const signup = async (payload) => {
    // payload: { loginId, password, name, phone, email, birthDate, address }
    return http.post('/auth/signup', payload)
}
