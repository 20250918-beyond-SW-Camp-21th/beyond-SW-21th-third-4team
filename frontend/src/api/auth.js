import http from '../api/http'
import { removeToken } from "../utils/token.js";

export const login = async (loginId, password) => {
    return http.post('/auth/login', { loginId, password })
}

export const signup = async (payload) => {
    // payload: { loginId, password, name, phone, email, birthDate, address }
    return http.post('/auth/signup', payload)
}

export const logout = () => {
    removeToken()
    window.location.replace('/')
}
