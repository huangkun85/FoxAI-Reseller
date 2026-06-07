<template>
  <div class="app-container">
    <div v-loading="loading" style="min-height:300px">

      <!-- 未购买 -->
      <div v-if="!loading && !isPaid" class="dashboard-empty">
        <div class="empty-icon">
          <svg-icon icon-class="shopping" style="width:64px;height:64px;color:#c0c4cc" />
        </div>
        <h3>暂无购买套餐</h3>
        <p class="empty-desc">您还没有购买任何套餐，请前往套餐购买页面选择适合您的方案。</p>
        <el-button type="primary" size="large" @click="goBuy">前往购买</el-button>
      </div>

      <!-- 已购买 -->
      <div v-if="!loading && isPaid && account" class="dashboard">
        <div class="dashboard-header">
          <h2>{{ account.levelName || '分销商' }} Dashboard</h2>
          <el-tag type="success" effect="dark">已付费</el-tag>
        </div>

        <el-row :gutter="20" class="info-cards">
          <el-col :span="8">
            <div class="info-card">
              <div class="info-label">当前套餐</div>
              <div class="info-value">{{ account.levelName || '-' }}</div>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="info-card">
              <div class="info-label">Token总额度</div>
              <div class="info-value primary">¥{{ formatMoney(account.tokenQuota) }}</div>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="info-card">
              <div class="info-label">已用额度</div>
              <div class="info-value warning">¥{{ formatMoney(account.tokenUsed) }}</div>
            </div>
          </el-col>
        </el-row>

        <el-row :gutter="20" class="info-cards">
          <el-col :span="8">
            <div class="info-card">
              <div class="info-label">剩余额度</div>
              <div class="info-value success">¥{{ formatMoney(remaining) }}</div>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="info-card">
              <div class="info-label">赠送额度</div>
              <div class="info-value">¥{{ formatMoney(account.bonusAmount) }}</div>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="info-card">
              <div class="info-label">状态</div>
              <div class="info-value"><el-tag>{{ account.status === '0' ? '正常' : '停用' }}</el-tag></div>
            </div>
          </el-col>
        </el-row>

        <el-divider />

        <el-descriptions title="API 凭证" :column="1" border>
          <el-descriptions-item label="API Key">
            <code>{{ account.apiKey || '未设置' }}</code>
          </el-descriptions-item>
          <el-descriptions-item label="API Secret">
            <code>{{ account.apiSecret || '未设置' }}</code>
          </el-descriptions-item>
          <el-descriptions-item label="API地址">
            <code>{{ account.endpointUrl || '未设置' }}</code>
          </el-descriptions-item>
        </el-descriptions>
      </div>
    </div>
  </div>
</template>

<script setup name="ResellerDashboard">
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getMyAccount } from '@/api/reseller/dashboard'
import useUserStore from '@/store/modules/user'

const router = useRouter()
const userStore = useUserStore()

const loading = ref(true)
const account = ref(null)

const isPaid = computed(() => userStore.userType === '02')

const remaining = computed(() => {
  if (!account.value) return 0
  return Number(account.value.tokenQuota || 0) - Number(account.value.tokenUsed || 0)
})

function formatMoney(v) {
  return Number(v || 0).toLocaleString('zh-CN', { minimumFractionDigits: 2 })
}

function goBuy() {
  router.push('/reseller/payment/apply')
}

onMounted(() => {
  if (!isPaid.value) {
    loading.value = false
    return
  }
  getMyAccount().then(res => {
    const data = res.data || res
    if (data) {
      import('@/api/reseller/level').then(m => m.listResellerLevel()).then(levelRes => {
        const levels = levelRes.rows || levelRes.data || []
        const level = levels.find(l => l.id === data.levelId)
        data.levelName = level ? level.levelName : ''
        account.value = data
        loading.value = false
      })
    } else {
      loading.value = false
    }
  }).catch(() => { loading.value = false })
})
</script>

<style lang="scss" scoped>
.dashboard {
  max-width: 960px;
  margin: 0 auto;
}

.dashboard-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 20px;
  h2 { margin: 0; font-size: 22px; }
}

.info-cards {
  margin-bottom: 16px;
}

.info-card {
  background: #f5f7fa;
  border-radius: 10px;
  padding: 20px;
  text-align: center;
  border: 1px solid #ebeef5;
  .info-label {
    font-size: 14px;
    color: #909399;
    margin-bottom: 8px;
  }
  .info-value {
    font-size: 24px;
    font-weight: 700;
    color: #303133;
    &.primary { color: #6366f1; }
    &.warning { color: #e6a23c; }
    &.success { color: #67c23a; }
  }
}

.dashboard-empty {
  text-align: center;
  padding: 80px 20px;
  .empty-icon { margin-bottom: 20px; }
  h3 { font-size: 20px; color: #303133; margin-bottom: 12px; }
  .empty-desc { color: #909399; font-size: 14px; margin-bottom: 24px; }
}

html.dark .info-card {
  background: var(--el-bg-color-page);
  border-color: var(--el-border-color-light);
}
</style>
