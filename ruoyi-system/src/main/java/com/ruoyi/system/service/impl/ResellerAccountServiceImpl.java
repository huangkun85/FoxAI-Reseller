package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.ResellerAccount;
import com.ruoyi.system.mapper.ResellerAccountMapper;
import com.ruoyi.system.service.IResellerAccountService;

@Service
public class ResellerAccountServiceImpl implements IResellerAccountService
{
    @Autowired
    private ResellerAccountMapper resellerAccountMapper;

    @Override
    public ResellerAccount selectResellerAccountByUserId(Long userId)
    {
        return resellerAccountMapper.selectResellerAccountByUserId(userId);
    }

    @Override
    public List<ResellerAccount> selectResellerAccountList(ResellerAccount resellerAccount)
    {
        return resellerAccountMapper.selectResellerAccountList(resellerAccount);
    }

    @Override
    public int insertResellerAccount(ResellerAccount resellerAccount)
    {
        return resellerAccountMapper.insertResellerAccount(resellerAccount);
    }

    @Override
    public int updateResellerAccount(ResellerAccount resellerAccount)
    {
        return resellerAccountMapper.updateResellerAccount(resellerAccount);
    }
}
