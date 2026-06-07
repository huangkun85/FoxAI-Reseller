package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.ResellerAccount;

public interface ResellerAccountMapper
{
    public ResellerAccount selectResellerAccountById(Long id);

    public ResellerAccount selectResellerAccountByUserId(Long userId);

    public List<ResellerAccount> selectResellerAccountList(ResellerAccount resellerAccount);

    public int insertResellerAccount(ResellerAccount resellerAccount);

    public int updateResellerAccount(ResellerAccount resellerAccount);
}
