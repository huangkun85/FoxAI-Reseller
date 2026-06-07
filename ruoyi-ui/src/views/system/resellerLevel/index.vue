<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="等级代码" prop="levelCode">
        <el-select v-model="queryParams.levelCode" placeholder="请选择等级代码" clearable style="width: 240px">
          <el-option
            v-for="dict in reseller_level"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 240px">
          <el-option
            v-for="dict in sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['resellerLevel:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['resellerLevel:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['resellerLevel:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['resellerLevel:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="resellerLevelList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" width="60" />
      <el-table-column label="等级代码" align="center" prop="levelCode" width="100">
        <template #default="scope">
          <dict-tag :options="reseller_level" :value="scope.row.levelCode" />
        </template>
      </el-table-column>
      <el-table-column label="等级名称" align="center" prop="levelName" width="120" />
      <el-table-column label="套餐金额" align="center" prop="packageAmount" width="120">
        <template #default="scope">
          {{ parseMoney(scope.row.packageAmount) }}
        </template>
      </el-table-column>
      <el-table-column label="加盟费" align="center" prop="franchiseFee" width="120">
        <template #default="scope">
          {{ parseMoney(scope.row.franchiseFee) }}
        </template>
      </el-table-column>
      <el-table-column label="Token额度" align="center" prop="tokenQuota" width="120">
        <template #default="scope">
          {{ parseMoney(scope.row.tokenQuota) }}
        </template>
      </el-table-column>
      <el-table-column label="赠送额度" align="center" prop="bonusAmount" width="120">
        <template #default="scope">
          {{ parseMoney(scope.row.bonusAmount) }}
        </template>
      </el-table-column>
      <el-table-column label="下级返佣" align="center" prop="commissionRate" width="100">
        <template #default="scope">
          {{ scope.row.commissionRate }}%
        </template>
      </el-table-column>
      <el-table-column label="推荐奖励" align="center" prop="referralReward" width="120">
        <template #default="scope">
          {{ parseMoney(scope.row.referralReward) }}
        </template>
      </el-table-column>
      <el-table-column label="显示顺序" align="center" prop="sortOrder" width="80" />
      <el-table-column label="状态" align="center" prop="status" width="80">
        <template #default="scope">
          <dict-tag :options="sys_normal_disable" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true" min-width="150" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="180" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['resellerLevel:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['resellerLevel:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <el-dialog :title="title" v-model="open" width="700px" append-to-body>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="等级代码" prop="levelCode">
              <el-select v-model="form.levelCode" placeholder="请选择" style="width: 100%">
                <el-option
                  v-for="dict in reseller_level"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="等级名称" prop="levelName">
              <el-input v-model="form.levelName" placeholder="请输入等级名称" maxlength="50" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="套餐金额" prop="packageAmount">
              <el-input-number v-model="form.packageAmount" :min="0" :precision="2" controls-position="right" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="加盟费" prop="franchiseFee">
              <el-input-number v-model="form.franchiseFee" :min="0" :precision="2" controls-position="right" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Token额度" prop="tokenQuota">
              <el-input-number v-model="form.tokenQuota" :min="0" :precision="2" controls-position="right" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="赠送额度" prop="bonusAmount">
              <el-input-number v-model="form.bonusAmount" :min="0" :precision="2" controls-position="right" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="下级返佣(%)" prop="commissionRate">
              <el-input-number v-model="form.commissionRate" :min="0" :max="100" :precision="2" controls-position="right" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="推荐奖励" prop="referralReward">
              <el-input-number v-model="form.referralReward" :min="0" :precision="2" controls-position="right" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示顺序" prop="sortOrder">
              <el-input-number v-model="form.sortOrder" :min="0" controls-position="right" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in sys_normal_disable"
                  :key="dict.value"
                  :value="dict.value"
                >{{ dict.label }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" maxlength="500" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="ResellerLevel">
import { listResellerLevel, getResellerLevel, addResellerLevel, updateResellerLevel, delResellerLevel } from "@/api/system/resellerLevel"
import { useDict } from '@/utils/dict'

const { proxy } = getCurrentInstance()
const { sys_normal_disable, reseller_level } = useDict('sys_normal_disable', 'reseller_level')

const resellerLevelList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref('')

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    levelCode: null,
    status: null
  }
})

const { queryParams, form } = toRefs(data)

const rules = {
  levelCode: [{ required: true, message: "等级代码不能为空", trigger: "change" }],
  levelName: [{ required: true, message: "等级名称不能为空", trigger: "blur" }],
  packageAmount: [{ required: true, message: "套餐金额不能为空", trigger: "blur" }],
  franchiseFee: [{ required: true, message: "加盟费不能为空", trigger: "blur" }],
  tokenQuota: [{ required: true, message: "Token额度不能为空", trigger: "blur" }],
  commissionRate: [{ required: true, message: "下级返佣比例不能为空", trigger: "blur" }]
}

function getList() {
  loading.value = true
  listResellerLevel(queryParams.value).then(res => {
    resellerLevelList.value = res.rows
    total.value = res.total
    loading.value = false
  })
}

function cancel() {
  open.value = false
  reset()
}

function reset() {
  proxy.$refs.formRef?.resetFields()
}

function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

function resetQuery() {
  proxy.$refs.queryRef?.resetFields()
  handleQuery()
}

function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

function handleAdd() {
  reset()
  open.value = true
  title.value = "添加分销商等级"
  form.value = {
    id: null,
    levelCode: null,
    levelName: null,
    packageAmount: 0,
    franchiseFee: 0,
    tokenQuota: 0,
    bonusAmount: 0,
    commissionRate: 0,
    referralReward: 0,
    sortOrder: 0,
    status: "0",
    remark: null
  }
}

function handleUpdate(row) {
  reset()
  const id = row.id || ids.value[0]
  getResellerLevel(id).then(res => {
    form.value = res.data
    open.value = true
    title.value = "修改分销商等级"
  })
}

function submitForm() {
  proxy.$refs.formRef?.validate(valid => {
    if (valid) {
      if (form.value.id) {
        updateResellerLevel(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addResellerLevel(form.value).then(() => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

function handleDelete(row) {
  const delIds = row.id ? [row.id] : ids.value
  proxy.$modal.confirm('是否确认删除等级编号为"' + delIds + '"的数据项？').then(() => {
    return delResellerLevel(delIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

function handleExport() {
  proxy.download('/system/resellerLevel/export', {
    ...queryParams.value
  }, `resellerLevel_${new Date().getTime()}.xlsx`)
}

function parseMoney(value) {
  if (value === null || value === undefined) return '¥0.00'
  return '¥' + Number(value).toLocaleString('zh-CN', { minimumFractionDigits: 2, maximumFractionDigits: 2 })
}

onMounted(() => {
  getList()
})
</script>
