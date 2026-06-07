package com.ruoyi.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.system.domain.ResellerPayment;

public interface ResellerPaymentMapper
{
    public ResellerPayment selectResellerPaymentById(Long id);

    public List<ResellerPayment> selectResellerPaymentList(ResellerPayment resellerPayment);

    public List<ResellerPayment> selectPaymentsByUserId(Long userId);

    public int insertResellerPayment(ResellerPayment resellerPayment);

    public int updateResellerPayment(ResellerPayment resellerPayment);

    public int updateOrderNo(@Param("id") Long id, @Param("orderNo") String orderNo);
}
