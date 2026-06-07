<template>
  <div class="app-container">
    <el-table v-loading="loading" :data="paymentList">
      <el-table-column label="订单号" align="center" prop="orderNo" width="200">
        <template #default="scope">
          <code>{{ scope.row.orderNo || '-' }}</code>
        </template>
      </el-table-column>
      <el-table-column label="金额" align="center" prop="amount" width="150">
        <template #default="scope">¥{{ formatMoney(scope.row.amount) }}</template>
      </el-table-column>
      <el-table-column label="联系人" align="center" prop="contactName" width="120" />
      <el-table-column label="类型" align="center" prop="resellerType" width="80">
        <template #default="scope">{{ scope.row.resellerType === '1' ? '企业' : '个人' }}</template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template #default="scope">
          <dict-tag :options="payment_status" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="170">
        <template #default="scope">{{ parseTime(scope.row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="120">
        <template #default="scope">
          <el-button v-if="scope.row.status === '0'" link type="primary" icon="Money" @click="showPay(scope.row)">
            支付
          </el-button>
          <el-tag v-else-if="scope.row.status === '1'" type="success" size="small">已付款</el-tag>
          <el-tag v-else type="danger" size="small">已驳回</el-tag>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog title="支付" v-model="payDialog" width="480px">
      <div v-if="payOrder" style="padding:8px 0">
        <el-descriptions :column="1" border>
          <el-descriptions-item label="订单号">{{ payOrder.orderNo }}</el-descriptions-item>
          <el-descriptions-item label="应付金额">
            <span style="color:#f56c6c;font-size:18px;font-weight:bold">¥{{ formatMoney(payOrder.amount) }}</span>
          </el-descriptions-item>
        </el-descriptions>

        <el-divider />

        <div class="bank-info">
          <p><strong>开户行：</strong>{{ bankInfo.bankName }}</p>
          <p><strong>账  号：</strong>{{ bankInfo.accountNumber }}</p>
          <p><strong>户  名：</strong>{{ bankInfo.accountName }}</p>
          <p style="color:#909399;font-size:12px;margin-top:12px">
            请按以上信息转账，到账后由管理员确认。后续将接入微信/支付宝等在线支付方式。
          </p>
        </div>
      </div>
      <template #footer>
        <el-button @click="payDialog = false">我知道了</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="ResellerPaymentHistory">
import { ref, reactive, onMounted } from 'vue'
import { myPaymentList } from '@/api/reseller/payment'
import { useDict } from '@/utils/dict'

const { proxy } = getCurrentInstance()
const { payment_status } = useDict('sys_payment_status')

const paymentList = ref([])
const loading = ref(true)
const total = ref(0)
const queryParams = reactive({ pageNum: 1, pageSize: 10 })

const payDialog = ref(false)
const payOrder = ref(null)
const bankInfo = ref({ bankName: '', accountNumber: '', accountName: '' })

function getList() {
  loading.value = true
  myPaymentList(queryParams).then(res => {
    paymentList.value = res.rows
    total.value = res.total
    loading.value = false
  })
}

function formatMoney(v) {
  return Number(v || 0).toLocaleString('zh-CN', { minimumFractionDigits: 2 })
}

function showPay(row) {
  payOrder.value = row
  import('@/api/system/config').then(m => {
    m.getConfigKey('sys.reseller.bank.account').then(res => {
      try {
        bankInfo.value = JSON.parse(res.msg || res.data?.configValue || '{}')
      } catch (e) { /* ignore */ }
    })
  })
  payDialog.value = true
}

onMounted(() => getList())
</script>

<style lang="scss" scoped>
.bank-info {
  background: #f5f7fa;
  border-radius: 8px;
  padding: 16px 20px;
  line-height: 1.8;
  p { margin: 0; }
}
html.dark .bank-info {
  background: var(--el-bg-color-page);
}
</style>
