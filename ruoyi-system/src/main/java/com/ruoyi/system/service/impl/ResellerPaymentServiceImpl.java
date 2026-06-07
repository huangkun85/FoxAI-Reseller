package com.ruoyi.system.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.ResellerPayment;
import com.ruoyi.system.mapper.ResellerPaymentMapper;
import com.ruoyi.system.service.IResellerPaymentService;

@Service
public class ResellerPaymentServiceImpl implements IResellerPaymentService
{
    @Autowired
    private ResellerPaymentMapper resellerPaymentMapper;

    @Override
    public ResellerPayment selectResellerPaymentById(Long id)
    {
        return resellerPaymentMapper.selectResellerPaymentById(id);
    }

    @Override
    public List<ResellerPayment> selectResellerPaymentList(ResellerPayment resellerPayment)
    {
        return resellerPaymentMapper.selectResellerPaymentList(resellerPayment);
    }

    @Override
    public List<ResellerPayment> selectPaymentsByUserId(Long userId)
    {
        return resellerPaymentMapper.selectPaymentsByUserId(userId);
    }

    @Override
    public int insertResellerPayment(ResellerPayment resellerPayment)
    {
        int rows = resellerPaymentMapper.insertResellerPayment(resellerPayment);
        if (rows > 0)
        {
            String orderNo = "ORD" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                    + String.format("%04d", resellerPayment.getId());
            resellerPaymentMapper.updateOrderNo(resellerPayment.getId(), orderNo);
            resellerPayment.setOrderNo(orderNo);
        }
        return rows;
    }

    @Override
    public int updateResellerPayment(ResellerPayment resellerPayment)
    {
        return resellerPaymentMapper.updateResellerPayment(resellerPayment);
    }
}
