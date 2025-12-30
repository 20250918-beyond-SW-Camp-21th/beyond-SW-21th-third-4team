const KEY = 'accessToken'

export const getToken = () => localStorage.getItem(KEY)

export const setToken = (token) => {
    if(!token) return
    localStorage.setItem(KEY, token)
}

export const removeToken = () => localStorage.removeItem(KEY)