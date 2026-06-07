import request from '@/utils/request'

export function listResellerLevel(query) {
  return request({
    url: '/system/resellerLevel/list',
    method: 'get',
    params: query
  })
}
