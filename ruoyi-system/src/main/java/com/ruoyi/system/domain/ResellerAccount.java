package com.ruoyi.system.domain;

import java.math.BigDecimal;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class ResellerAccount extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private Long levelId;

    @Excel(name = "Token总额度")
    private BigDecimal tokenQuota;

    @Excel(name = "已用额度")
    private BigDecimal tokenUsed;

    @Excel(name = "赠送额度")
    private BigDecimal bonusAmount;

    @Excel(name = "API Key")
    private String apiKey;

    @Excel(name = "API Secret")
    private String apiSecret;

    @Excel(name = "API地址")
    private String endpointUrl;

    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    private String remark;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getLevelId() { return levelId; }
    public void setLevelId(Long levelId) { this.levelId = levelId; }

    public BigDecimal getTokenQuota() { return tokenQuota; }
    public void setTokenQuota(BigDecimal tokenQuota) { this.tokenQuota = tokenQuota; }

    public BigDecimal getTokenUsed() { return tokenUsed; }
    public void setTokenUsed(BigDecimal tokenUsed) { this.tokenUsed = tokenUsed; }

    public BigDecimal getBonusAmount() { return bonusAmount; }
    public void setBonusAmount(BigDecimal bonusAmount) { this.bonusAmount = bonusAmount; }

    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }

    public String getApiSecret() { return apiSecret; }
    public void setApiSecret(String apiSecret) { this.apiSecret = apiSecret; }

    public String getEndpointUrl() { return endpointUrl; }
    public void setEndpointUrl(String endpointUrl) { this.endpointUrl = endpointUrl; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
