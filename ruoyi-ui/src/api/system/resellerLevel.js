import request from '@/utils/request'

export function listResellerLevel(query) {
  return request({
    url: '/system/resellerLevel/list',
    method: 'get',
    params: query
  })
}

export function getResellerLevel(id) {
  return request({
    url: '/system/resellerLevel/' + id,
    method: 'get'
  })
}

export function addResellerLevel(data) {
  return request({
    url: '/system/resellerLevel',
    method: 'post',
    data: data
  })
}

export function updateResellerLevel(data) {
  return request({
    url: '/system/resellerLevel',
    method: 'put',
    data: data
  })
}

export function delResellerLevel(ids) {
  return request({
    url: '/system/resellerLevel/' + ids,
    method: 'delete'
  })
}
