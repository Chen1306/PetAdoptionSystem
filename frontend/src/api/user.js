import request from '../utils/request'

export function getProfile() {
  return request({
    url: '/api/users/profile',
    method: 'get'
  })
}

export function updateProfile(data) {
  return request({
    url: '/api/users/profile',
    method: 'put',
    data
  })
}
