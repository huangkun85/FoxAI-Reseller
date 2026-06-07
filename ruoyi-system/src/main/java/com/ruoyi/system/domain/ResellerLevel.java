package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class ResellerLevel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "等级代码")
    private String levelCode;

    @Excel(name = "等级名称")
    private String levelName;

    @Excel(name = "套餐金额")
    private BigDecimal packageAmount;

    @Excel(name = "加盟费")
    private BigDecimal franchiseFee;

    @Excel(name = "Token额度")
    private BigDecimal tokenQuota;

    @Excel(name = "赠送额度")
    private BigDecimal bonusAmount;

    @Excel(name = "下级返佣比例(%)")
    private BigDecimal commissionRate;

    @Excel(name = "推荐奖励金额")
    private BigDecimal referralReward;

    @Excel(name = "显示顺序")
    private Integer sortOrder;

    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLevelCode() { return levelCode; }
    public void setLevelCode(String levelCode) { this.levelCode = levelCode; }

    public String getLevelName() { return levelName; }
    public void setLevelName(String levelName) { this.levelName = levelName; }

    public BigDecimal getPackageAmount() { return packageAmount; }
    public void setPackageAmount(BigDecimal packageAmount) { this.packageAmount = packageAmount; }

    public BigDecimal getFranchiseFee() { return franchiseFee; }
    public void setFranchiseFee(BigDecimal franchiseFee) { this.franchiseFee = franchiseFee; }

    public BigDecimal getTokenQuota() { return tokenQuota; }
    public void setTokenQuota(BigDecimal tokenQuota) { this.tokenQuota = tokenQuota; }

    public BigDecimal getBonusAmount() { return bonusAmount; }
    public void setBonusAmount(BigDecimal bonusAmount) { this.bonusAmount = bonusAmount; }

    public BigDecimal getCommissionRate() { return commissionRate; }
    public void setCommissionRate(BigDecimal commissionRate) { this.commissionRate = commissionRate; }

    public BigDecimal getReferralReward() { return referralReward; }
    public void setReferralReward(BigDecimal referralReward) { this.referralReward = referralReward; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("levelCode", getLevelCode())
                .append("levelName", getLevelName())
                .append("packageAmount", getPackageAmount())
                .append("franchiseFee", getFranchiseFee())
                .append("tokenQuota", getTokenQuota())
                .append("bonusAmount", getBonusAmount())
                .append("commissionRate", getCommissionRate())
                .append("referralReward", getReferralReward())
                .append("sortOrder", getSortOrder())
                .append("status", getStatus())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
