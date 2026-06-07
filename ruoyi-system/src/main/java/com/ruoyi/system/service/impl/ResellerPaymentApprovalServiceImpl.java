package com.ruoyi.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.ResellerAccount;
import com.ruoyi.system.domain.ResellerPayment;
import com.ruoyi.system.mapper.ResellerAccountMapper;
import com.ruoyi.system.mapper.ResellerPaymentMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.IResellerPaymentApprovalService;

@Service
public class ResellerPaymentApprovalServiceImpl implements IResellerPaymentApprovalService
{
    @Autowired
    private ResellerPaymentMapper paymentMapper;

    @Autowired
    private ResellerAccountMapper accountMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Override
    @Transactional
    public int passPayment(Long paymentId, String remark)
    {
        ResellerPayment payment = paymentMapper.selectResellerPaymentById(paymentId);
        if (payment == null) return 0;

        payment.setStatus("1");
        payment.setRemark(remark);
        paymentMapper.updateResellerPayment(payment);

        userMapper.updateUserType(payment.getUserId(), "02");

        ResellerAccount account = accountMapper.selectResellerAccountByUserId(payment.getUserId());
        if (account == null)
        {
            account = new ResellerAccount();
            account.setUserId(payment.getUserId());
            account.setLevelId(payment.getLevelId());
            account.setTokenQuota(java.math.BigDecimal.ZERO);
            account.setTokenUsed(java.math.BigDecimal.ZERO);
            account.setBonusAmount(java.math.BigDecimal.ZERO);
            account.setStatus("0");
            account.setCreateBy(String.valueOf(payment.getUserId()));
            accountMapper.insertResellerAccount(account);
        }
        return 1;
    }

    @Override
    @Transactional
    public int rejectPayment(Long paymentId, String remark)
    {
        ResellerPayment payment = paymentMapper.selectResellerPaymentById(paymentId);
        if (payment == null) return 0;

        payment.setStatus("2");
        payment.setRemark(remark);
        return paymentMapper.updateResellerPayment(payment);
    }
}
