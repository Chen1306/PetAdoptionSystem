import axios from 'axios'
import { Message } from 'element-ui'
import { BASE_URL } from './constants'
import { clearAuth, getToken } from './auth'

const service = axios.create({
  baseURL: BASE_URL,
  timeout: 10000
})

service.interceptors.request.use(config => {
  const token = getToken()
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

service.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code === 200) {
      return res.data
    }
    if (res.code === 401) {
      clearAuth()
      Message.error(res.msg || '请先登录')
      window.location.href = '/login'
      return Promise.reject(new Error(res.msg || 'Unauthorized'))
    }
    Message.error(res.msg || '请求失败')
    return Promise.reject(new Error(res.msg || 'Error'))
  },
  error => {
    Message.error(error.message || '网络异常')
    return Promise.reject(error)
  }
)

export default service
