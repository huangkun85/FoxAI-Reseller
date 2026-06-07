import request from '@/utils/request'

export function getMyAccount() {
  return request({
    url: '/reseller/account/my-info',
    method: 'get'
  })
}

export function listAccount(query) {
  return request({
    url: '/reseller/account/list',
    method: 'get',
    params: query
  })
}

export function updateAccount(data) {
  return request({
    url: '/reseller/account',
    method: 'put',
    data: data
  })
}
