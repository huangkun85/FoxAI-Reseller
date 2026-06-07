package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.ResellerPayment;

public interface IResellerPaymentService
{
    public ResellerPayment selectResellerPaymentById(Long id);

    public List<ResellerPayment> selectResellerPaymentList(ResellerPayment resellerPayment);

    public List<ResellerPayment> selectPaymentsByUserId(Long userId);

    public int insertResellerPayment(ResellerPayment resellerPayment);

    public int updateResellerPayment(ResellerPayment resellerPayment);
}
