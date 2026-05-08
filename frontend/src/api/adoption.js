import request from '../utils/request'

export function createAdoption(data) {
  return request({
    url: '/api/adoptions',
    method: 'post',
    data
  })
}

export function getMyAdoptions() {
  return request({
    url: '/api/adoptions/my',
    method: 'get'
  })
}

export function cancelAdoption(id) {
  return request({
    url: `/api/adoptions/${id}/cancel`,
    method: 'put'
  })
}

export function getAdminAdoptions() {
  return request({
    url: '/api/admin/adoptions',
    method: 'get'
  })
}

export function auditAdoption(id, data) {
  return request({
    url: `/api/admin/adoptions/${id}/audit`,
    method: 'put',
    data
  })
}
