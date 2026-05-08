import { ROLE_KEY, TOKEN_KEY, USER_KEY } from './constants'

export function getToken() {
  return localStorage.getItem(TOKEN_KEY) || ''
}

export function setAuth(payload) {
  localStorage.setItem(TOKEN_KEY, payload.token || '')
  localStorage.setItem(USER_KEY, JSON.stringify({
    userId: payload.userId,
    username: payload.username,
    role: payload.role
  }))
  localStorage.setItem(ROLE_KEY, payload.role || '')
}

export function clearAuth() {
  localStorage.removeItem(TOKEN_KEY)
  localStorage.removeItem(USER_KEY)
  localStorage.removeItem(ROLE_KEY)
}

export function getUser() {
  const raw = localStorage.getItem(USER_KEY)
  if (!raw) return null
  try {
    return JSON.parse(raw)
  } catch (e) {
    return null
  }
}

export function getRole() {
  return localStorage.getItem(ROLE_KEY) || ''
}

export function isLoggedIn() {
  return !!getToken()
}
