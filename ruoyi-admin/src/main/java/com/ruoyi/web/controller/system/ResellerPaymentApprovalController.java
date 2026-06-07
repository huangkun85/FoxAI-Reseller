package com.ruoyi.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.service.IResellerPaymentApprovalService;

@RestController
@RequestMapping("/reseller/payment/approval")
public class ResellerPaymentApprovalController extends BaseController
{
    @Autowired
    private IResellerPaymentApprovalService resellerPaymentApprovalService;

    @PreAuthorize("@ss.hasPermi('reseller:payment:approve:pass')")
    @Log(title = "付款审核", businessType = BusinessType.UPDATE)
    @PostMapping("/pass/{paymentId}")
    public AjaxResult pass(@PathVariable Long paymentId, String remark)
    {
        return toAjax(resellerPaymentApprovalService.passPayment(paymentId, remark));
    }

    @PreAuthorize("@ss.hasPermi('reseller:payment:approve:reject')")
    @Log(title = "付款审核", businessType = BusinessType.UPDATE)
    @PostMapping("/reject/{paymentId}")
    public AjaxResult reject(@PathVariable Long paymentId, String remark)
    {
        return toAjax(resellerPaymentApprovalService.rejectPayment(paymentId, remark));
    }
}
