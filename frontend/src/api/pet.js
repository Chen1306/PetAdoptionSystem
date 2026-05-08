import request from '../utils/request'

export function getPetList(params) {
  return request({
    url: '/api/pets',
    method: 'get',
    params
  })
}

export function getPetDetail(id) {
  return request({
    url: `/api/pets/${id}`,
    method: 'get'
  })
}

export function getAdminPetList() {
  return request({
    url: '/api/admin/pets',
    method: 'get'
  })
}

export function createPet(data) {
  return request({
    url: '/api/admin/pets',
    method: 'post',
    data
  })
}

export function updatePet(id, data) {
  return request({
    url: `/api/admin/pets/${id}`,
    method: 'put',
    data
  })
}

export function deletePet(id) {
  return request({
    url: `/api/admin/pets/${id}`,
    method: 'delete'
  })
}

export function reviewPet(id, data) {
  return request({
    url: `/api/admin/pets/${id}/review`,
    method: 'put',
    data
  })
}
