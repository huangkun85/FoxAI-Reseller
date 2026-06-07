<template>
  <div class="app-container">
    <el-table v-loading="loading" :data="paymentList">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" width="60" />
      <el-table-column label="用户ID" align="center" prop="userId" width="80" />
      <el-table-column label="金额" align="center" prop="amount" width="150">
        <template #default="scope">¥{{ formatMoney(scope.row.amount) }}</template>
      </el-table-column>
      <el-table-column label="类型" align="center" prop="resellerType" width="80">
        <template #default="scope">{{ scope.row.resellerType === '1' ? '企业' : '个人' }}</template>
      </el-table-column>
      <el-table-column label="证件号" align="center" prop="idNumber" width="150" />
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template #default="scope"><el-tag type="warning">待审核</el-tag></template>
      </el-table-column>
      <el-table-column label="申请时间" align="center" prop="createTime" width="170">
        <template #default="scope">{{ parseTime(scope.row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200">
        <template #default="scope">
          <el-button link type="success" icon="CircleCheck" @click="handlePass(scope.row)">通过</el-button>
          <el-button link type="danger" icon="CircleClose" @click="handleReject(scope.row)">驳回</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize" @pagination="getList" />
  </div>
</template>

<script setup name="ResellerPaymentApproval">
import { ref, reactive } from 'vue'
import { pendingPaymentList, passPayment, rejectPayment } from '@/api/reseller/payment'

const { proxy } = getCurrentInstance()
const paymentList = ref([])
const loading = ref(true)
const total = ref(0)
const queryParams = reactive({ pageNum: 1, pageSize: 10 })

function getList() {
  loading.value = true
  pendingPaymentList(queryParams).then(res => {
    paymentList.value = res.rows
    total.value = res.total
    loading.value = false
  })
}

function formatMoney(v) {
  return Number(v || 0).toLocaleString('zh-CN', { minimumFractionDigits: 2 })
}

function handlePass(row) {
  proxy.$modal.prompt('审核备注', '确认通过', { inputType: 'textarea' }).then(({ value }) => {
    passPayment(row.id, value || '').then(() => {
      proxy.$modal.msgSuccess('已通过')
      getList()
    })
  }).catch(() => {})
}

function handleReject(row) {
  proxy.$modal.prompt('驳回原因', '确认驳回', { inputType: 'textarea' }).then(({ value }) => {
    rejectPayment(row.id, value || '').then(() => {
      proxy.$modal.msgSuccess('已驳回')
      getList()
    })
  }).catch(() => {})
}

onMounted(() => getList())
</script>
