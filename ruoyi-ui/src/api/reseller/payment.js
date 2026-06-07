import request from '@/utils/request'

export function applyPayment(data) {
  return request({
    url: '/reseller/payment/apply',
    method: 'post',
    data: data
  })
}

export function myPaymentList(query) {
  return request({
    url: '/reseller/payment/my-list',
    method: 'get',
    params: query
  })
}

export function pendingPaymentList(query) {
  return request({
    url: '/reseller/payment/pending-list',
    method: 'get',
    params: query
  })
}

export function passPayment(paymentId, remark) {
  return request({
    url: '/reseller/payment/approval/pass/' + paymentId,
    method: 'post',
    params: { remark }
  })
}

export function rejectPayment(paymentId, remark) {
  return request({
    url: '/reseller/payment/approval/reject/' + paymentId,
    method: 'post',
    params: { remark }
  })
}
