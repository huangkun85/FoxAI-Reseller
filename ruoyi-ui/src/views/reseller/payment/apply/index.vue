<template>
  <div class="app-container payment-apply">
    <div class="level-cards">
      <div
        v-for="item in levelList"
        :key="item.id"
        class="level-card"
        :class="{ selected: selectedLevel && selectedLevel.id === item.id }"
        @click="selectLevel(item)"
      >
        <div class="level-card-icon">
          <svg-icon :icon-class="getLevelIcon(item.levelCode)" style="width:40px;height:40px" />
        </div>
        <div class="level-card-name">{{ item.levelName }}</div>
        <div class="level-card-price">¥{{ formatNumber(item.packageAmount) }}</div>
      </div>
    </div>

    <div v-if="selectedLevel" class="payment-detail">
      <el-descriptions title="费用明细" :column="2" border>
        <el-descriptions-item label="套餐金额">
          <span style="color:#f56c6c;font-weight:bold;font-size:16px">¥{{ formatNumber(selectedLevel.packageAmount) }}</span>
        </el-descriptions-item>
        <el-descriptions-item label="加盟费">¥{{ formatNumber(selectedLevel.franchiseFee) }}</el-descriptions-item>
        <el-descriptions-item label="Token额度">¥{{ formatNumber(selectedLevel.tokenQuota) }}</el-descriptions-item>
        <el-descriptions-item label="赠送额度">¥{{ formatNumber(selectedLevel.bonusAmount) }}</el-descriptions-item>
        <el-descriptions-item label="下级返佣">{{ selectedLevel.commissionRate }}%</el-descriptions-item>
        <el-descriptions-item label="推荐奖励">¥{{ formatNumber(selectedLevel.referralReward) }}</el-descriptions-item>
        <el-descriptions-item label="应付金额" :span="2">
          <span style="color:#f56c6c;font-size:20px;font-weight:bold">
            ¥{{ formatNumber(packageAmount) }}
          </span>
          <span style="color:#909399;font-size:12px;margin-left:8px">（仅套餐金额）</span>
        </el-descriptions-item>
      </el-descriptions>

      <el-divider content-position="left">联系人信息</el-divider>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="联系人姓名" prop="contactName">
          <el-input v-model="form.contactName" placeholder="请输入联系人姓名" maxlength="50" />
        </el-form-item>
        <el-form-item label="联系人电话" prop="contactPhone">
          <el-input v-model="form.contactPhone" placeholder="请输入联系电话" maxlength="20" />
        </el-form-item>
        <el-form-item label="联系人邮箱" prop="contactEmail">
          <el-input v-model="form.contactEmail" placeholder="请输入联系邮箱" maxlength="100" />
        </el-form-item>
        <el-form-item label="分销商类型" prop="resellerType">
          <el-radio-group v-model="form.resellerType">
            <el-radio value="0">个人</el-radio>
            <el-radio value="1">企业</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="证件号码">
          <el-input v-model="form.idNumber" placeholder="身份证号或营业执照号（选填）" maxlength="50" />
        </el-form-item>
      </el-form>

      <div style="margin-top:24px;text-align:center">
        <el-button type="primary" size="large" :loading="submitting" @click="createOrder" style="width:300px">
          创建订单
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup name="ResellerPaymentApply">
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { listResellerLevel } from '@/api/reseller/level'
import { applyPayment } from '@/api/reseller/payment'

const levelList = ref([])
const selectedLevel = ref(null)
const submitting = ref(false)

const form = reactive({
  contactName: '',
  contactPhone: '',
  contactEmail: '',
  resellerType: '0',
  idNumber: ''
})

const rules = {
  contactName: [{ required: true, message: '联系人姓名不能为空', trigger: 'blur' }],
  contactPhone: [{ required: true, message: '联系人电话不能为空', trigger: 'blur' }],
  contactEmail: [
    { required: true, message: '联系人邮箱不能为空', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
  ]
}

const packageAmount = computed(() => {
  if (!selectedLevel.value) return 0
  return Number(selectedLevel.value.packageAmount || 0)
})

function formatNumber(val) {
  if (val === null || val === undefined) return '0.00'
  return Number(val).toLocaleString('zh-CN', { minimumFractionDigits: 2, maximumFractionDigits: 2 })
}

function getLevelIcon(code) {
  const map = { bronze: 'skill', silver: 'star', gold: 'star', diamond: 'peoples' }
  return map[code] || 'skill'
}

function selectLevel(item) {
  selectedLevel.value = item
}

function createOrder() {
  if (!selectedLevel.value) {
    ElMessage.warning('请先选择套餐等级')
    return
  }
  submitting.value = true
  applyPayment({
    levelId: selectedLevel.value.id,
    resellerType: form.resellerType,
    idNumber: form.idNumber,
    contactName: form.contactName,
    contactPhone: form.contactPhone,
    contactEmail: form.contactEmail,
    amount: packageAmount.value
  }).then(res => {
    const orderNo = res.data?.orderNo || ''
    ElMessageBox.alert(
      `<div style="text-align:center">
        <p style="font-size:16px;color:#67c23a;margin-bottom:12px">订单创建成功！</p>
        <p style="font-size:14px;color:#303133">订单号：<strong>${orderNo}</strong></p>
        <p style="font-size:14px;color:#303133">金额：<strong style="color:#f56c6c">¥${formatNumber(packageAmount.value)}</strong></p>
        <p style="font-size:12px;color:#909399;margin-top:8px">请前往「支付查询」完成支付</p>
      </div>`,
      '创建成功',
      { dangerouslyUseHTMLString: true, type: 'success', confirmButtonText: '知道了' }
    )
    selectedLevel.value = null
  }).catch(() => {}).finally(() => {
    submitting.value = false
  })
}

onMounted(() => {
  listResellerLevel().then(res => {
    levelList.value = res.rows || res.data || []
  })
})
</script>

<style lang="scss" scoped>
.payment-apply {
  max-width: 960px;
  margin: 0 auto;
}

.level-cards {
  display: flex;
  gap: 16px;
  margin-bottom: 24px;
  flex-wrap: wrap;
  justify-content: center;
}

.level-card {
  flex: 1;
  min-width: 180px;
  max-width: 220px;
  border: 2px solid #e4e7ed;
  border-radius: 12px;
  padding: 24px 16px;
  text-align: center;
  cursor: pointer;
  transition: all 0.25s;

  &:hover {
    border-color: #6366f1;
    box-shadow: 0 4px 16px rgba(99,102,241,0.15);
  }
  &.selected {
    border-color: #6366f1;
    background: linear-gradient(135deg, rgba(99,102,241,0.05), rgba(139,92,246,0.08));
  }

  .level-card-icon { margin-bottom: 12px; }
  .level-card-name { font-size: 18px; font-weight: 600; color: #303133; margin-bottom: 8px; }
  .level-card-price { font-size: 22px; font-weight: 700; color: #f56c6c; }
}

.payment-detail { margin-top: 16px; }
</style>
