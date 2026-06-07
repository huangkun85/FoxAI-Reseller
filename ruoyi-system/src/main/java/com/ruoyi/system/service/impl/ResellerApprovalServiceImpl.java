package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.IResellerApprovalService;

@Service
public class ResellerApprovalServiceImpl implements IResellerApprovalService
{
    @Autowired
    private SysUserMapper userMapper;

    @Override
    public List<SysUser> selectPendingList(SysUser user)
    {
        return userMapper.selectUserList(user);
    }

    @Override
    public int approveUser(Long userId)
    {
        return userMapper.updateUserStatus(userId, "0");
    }

    @Override
    public int rejectUser(Long userId)
    {
        return userMapper.updateUserStatus(userId, "1");
    }
}
