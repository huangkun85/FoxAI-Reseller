package com.ruoyi.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.ResellerRegisterBody;
import com.ruoyi.system.service.IResellerRegisterService;

@RestController
@RequestMapping("/reseller")
public class ResellerRegisterController extends BaseController
{
    @Autowired
    private IResellerRegisterService resellerRegisterService;

    @PostMapping("/register")
    public AjaxResult register(@RequestBody ResellerRegisterBody user)
    {
        String msg = resellerRegisterService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
}
