package com.ruoyi.system.domain;

import java.math.BigDecimal;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class ResellerPayment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    private String orderNo;

    private Long userId;

    private Long levelId;

    @Excel(name = "分销商类型", readConverterExp = "0=个人,1=企业")
    private String resellerType;

    @Excel(name = "证件号")
    private String idNumber;

    @Excel(name = "联系人姓名")
    private String contactName;

    @Excel(name = "联系人电话")
    private String contactPhone;

    @Excel(name = "联系人邮箱")
    private String contactEmail;

    @Excel(name = "付款金额")
    private BigDecimal amount;

    @Excel(name = "状态", readConverterExp = "0=待付款,1=已付款,2=已驳回")
    private String status;

    @Excel(name = "审核备注")
    private String remark;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getLevelId() { return levelId; }
    public void setLevelId(Long levelId) { this.levelId = levelId; }

    public String getResellerType() { return resellerType; }
    public void setResellerType(String resellerType) { this.resellerType = resellerType; }

    public String getIdNumber() { return idNumber; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }

    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
