package com.ruoyi.web.controller.system;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.ResellerAccount;
import com.ruoyi.system.service.IResellerAccountService;

@RestController
@RequestMapping("/reseller/account")
public class ResellerAccountController extends BaseController
{
    @Autowired
    private IResellerAccountService resellerAccountService;

    @PreAuthorize("@ss.hasPermi('reseller:account:list')")
    @GetMapping("/list")
    public TableDataInfo list(ResellerAccount account)
    {
        startPage();
        List<ResellerAccount> list = resellerAccountService.selectResellerAccountList(account);
        return getDataTable(list);
    }

    @GetMapping("/my-info")
    public AjaxResult myInfo()
    {
        return success(resellerAccountService.selectResellerAccountByUserId(getUserId()));
    }

    @PreAuthorize("@ss.hasPermi('reseller:account:edit')")
    @Log(title = "账户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ResellerAccount account)
    {
        return toAjax(resellerAccountService.updateResellerAccount(account));
    }
}
