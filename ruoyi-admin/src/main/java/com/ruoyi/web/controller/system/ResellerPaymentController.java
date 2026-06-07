package com.ruoyi.web.controller.system;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.ResellerPayment;
import com.ruoyi.system.service.IResellerPaymentService;

@RestController
@RequestMapping("/reseller/payment")
public class ResellerPaymentController extends BaseController
{
    @Autowired
    private IResellerPaymentService resellerPaymentService;

    @PreAuthorize("@ss.hasPermi('reseller:payment:apply')")
    @PostMapping("/apply")
    public AjaxResult apply(@RequestBody ResellerPayment payment)
    {
        payment.setUserId(getUserId());
        return toAjax(resellerPaymentService.insertResellerPayment(payment));
    }

    @PreAuthorize("@ss.hasPermi('reseller:payment:history')")
    @GetMapping("/my-list")
    public TableDataInfo myList()
    {
        startPage();
        List<ResellerPayment> list = resellerPaymentService.selectPaymentsByUserId(getUserId());
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('reseller:payment:approve:list')")
    @GetMapping("/pending-list")
    public TableDataInfo pendingList(ResellerPayment payment)
    {
        startPage();
        payment.setStatus("0");
        List<ResellerPayment> list = resellerPaymentService.selectResellerPaymentList(payment);
        return getDataTable(list);
    }
}
