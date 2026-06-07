package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysUser;

public interface IResellerApprovalService
{
    public List<SysUser> selectPendingList(SysUser user);

    public int approveUser(Long userId);

    public int rejectUser(Long userId);
}
