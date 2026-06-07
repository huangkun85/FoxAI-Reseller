<template>
  <div class="app-container">
    <el-table v-loading="loading" :data="accountList" @row-click="openEdit">
      <el-table-column label="ID" align="center" prop="id" width="60" />
      <el-table-column label="用户ID" align="center" prop="userId" width="80" />
      <el-table-column label="Token总额度" align="center" prop="tokenQuota" width="120">
        <template #default="scope">¥{{ formatMoney(scope.row.tokenQuota) }}</template>
      </el-table-column>
      <el-table-column label="已用" align="center" prop="tokenUsed" width="120">
        <template #default="scope">¥{{ formatMoney(scope.row.tokenUsed) }}</template>
      </el-table-column>
      <el-table-column label="赠送额度" align="center" prop="bonusAmount" width="120">
        <template #default="scope">¥{{ formatMoney(scope.row.bonusAmount) }}</template>
      </el-table-column>
      <el-table-column label="API Key" align="center" prop="apiKey" min-width="180">
        <template #default="scope"><code>{{ scope.row.apiKey || '-' }}</code></template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" width="80">
        <template #default="scope">
          <dict-tag :options="sys_normal_disable" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="120">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click.stop="openEdit(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog title="编辑账户" v-model="dialogVisible" width="600px">
      <el-form ref="formRef" :model="form" label-width="120px">
        <el-form-item label="Token总额度">
          <el-input-number v-model="form.tokenQuota" :min="0" :precision="2" style="width:100%" />
        </el-form-item>
        <el-form-item label="赠送额度">
          <el-input-number v-model="form.bonusAmount" :min="0" :precision="2" style="width:100%" />
        </el-form-item>
        <el-form-item label="API Key">
          <el-input v-model="form.apiKey" placeholder="输入 API Key" />
        </el-form-item>
        <el-form-item label="API Secret">
          <el-input v-model="form.apiSecret" placeholder="输入 API Secret" />
        </el-form-item>
        <el-form-item label="API地址">
          <el-input v-model="form.endpointUrl" placeholder="https://api.example.com" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio value="0">正常</el-radio>
            <el-radio value="1">停用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveEdit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="ResellerAccount">
import { ref, reactive } from 'vue'
import { listAccount, updateAccount } from '@/api/reseller/account'
import { useDict } from '@/utils/dict'

const { proxy } = getCurrentInstance()
const { sys_normal_disable } = useDict('sys_normal_disable')

const accountList = ref([])
const loading = ref(true)
const total = ref(0)
const dialogVisible = ref(false)
const queryParams = reactive({ pageNum: 1, pageSize: 10 })
const form = reactive({})

function getList() {
  loading.value = true
  listAccount(queryParams).then(res => {
    accountList.value = res.rows
    total.value = res.total
    loading.value = false
  })
}

function formatMoney(v) {
  return Number(v || 0).toLocaleString('zh-CN', { minimumFractionDigits: 2 })
}

function openEdit(row) {
  Object.assign(form, {
    id: row.id, userId: row.userId, tokenQuota: row.tokenQuota || 0,
    bonusAmount: row.bonusAmount || 0, apiKey: row.apiKey || '',
    apiSecret: row.apiSecret || '', endpointUrl: row.endpointUrl || '',
    status: row.status || '0'
  })
  dialogVisible.value = true
}

function saveEdit() {
  updateAccount(form).then(() => {
    proxy.$modal.msgSuccess('保存成功')
    dialogVisible.value = false
    getList()
  })
}

onMounted(() => getList())
</script>
