import request from '../utils/request'

export function getUsers() {
  return request({
    url: '/api/admin/users',
    method: 'get'
  })
}

export function updateUserStatus(id, data) {
  return request({
    url: `/api/admin/users/${id}/status`,
    method: 'put',
    data
  })
}

export function getOverview() {
  return request({
    url: '/api/admin/statistics/overview',
    method: 'get'
  })
}
