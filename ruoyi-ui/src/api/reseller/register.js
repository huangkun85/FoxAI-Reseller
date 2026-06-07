import request from '@/utils/request'

export function resellerRegister(data) {
  return request({
    url: '/reseller/register',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}
