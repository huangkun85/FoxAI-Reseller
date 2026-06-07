import request from '@/utils/request'

export function pendingList(query) {
  return request({
    url: '/reseller/approval/pending-list',
    method: 'get',
    params: query
  })
}

export function approveUser(userId) {
  return request({
    url: '/reseller/approval/approve/' + userId,
    method: 'post'
  })
}

export function rejectUser(userId) {
  return request({
    url: '/reseller/approval/reject/' + userId,
    method: 'post'
  })
}
