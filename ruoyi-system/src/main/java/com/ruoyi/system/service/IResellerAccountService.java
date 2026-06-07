package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.ResellerAccount;

public interface IResellerAccountService
{
    public ResellerAccount selectResellerAccountByUserId(Long userId);

    public List<ResellerAccount> selectResellerAccountList(ResellerAccount resellerAccount);

    public int insertResellerAccount(ResellerAccount resellerAccount);

    public int updateResellerAccount(ResellerAccount resellerAccount);
}
