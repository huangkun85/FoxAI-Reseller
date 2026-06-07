import request from '@/utils/request'

export function getMyAccount() {
  return request({
    url: '/reseller/account/my-info',
    method: 'get'
  })
}
