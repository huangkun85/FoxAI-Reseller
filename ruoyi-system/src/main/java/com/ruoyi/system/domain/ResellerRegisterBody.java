package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.model.LoginBody;

public class ResellerRegisterBody extends LoginBody
{
    private String email;

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
