package com.ruoyi.web.controller.system;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.service.IResellerApprovalService;

@RestController
@RequestMapping("/reseller/approval")
public class ResellerApprovalController extends BaseController
{
    @Autowired
    private IResellerApprovalService resellerApprovalService;

    @PreAuthorize("@ss.hasPermi('reseller:approval:list')")
    @GetMapping("/pending-list")
    public TableDataInfo pendingList(SysUser user)
    {
        startPage();
        user.setUserType("01");
        user.setStatus("1");
        List<SysUser> list = resellerApprovalService.selectPendingList(user);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('reseller:approval:approve')")
    @Log(title = "分销商审核", businessType = BusinessType.UPDATE)
    @PostMapping("/approve/{userId}")
    public AjaxResult approve(@PathVariable Long userId)
    {
        return toAjax(resellerApprovalService.approveUser(userId));
    }

    @PreAuthorize("@ss.hasPermi('reseller:approval:reject')")
    @Log(title = "分销商审核", businessType = BusinessType.UPDATE)
    @PostMapping("/reject/{userId}")
    public AjaxResult reject(@PathVariable Long userId)
    {
        return toAjax(resellerApprovalService.rejectUser(userId));
    }
}
