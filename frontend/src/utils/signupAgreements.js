const KEY = 'signupAgreements'

export const setSignupAgreements = (value) => {
    sessionStorage.setItem(KEY, JSON.stringify(value))
}

export const getSignupAgreements = () => {
    const raw = sessionStorage.getItem(KEY)
    if (!raw) return null
    try {
        return JSON.parse(raw)
    } catch {
        return null
    }
}

export const clearSignupAgreements = () => {
    sessionStorage.removeItem(KEY)
}
