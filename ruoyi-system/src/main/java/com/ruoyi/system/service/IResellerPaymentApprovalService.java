package com.ruoyi.system.service;

public interface IResellerPaymentApprovalService
{
    public int passPayment(Long paymentId, String remark);

    public int rejectPayment(Long paymentId, String remark);
}
