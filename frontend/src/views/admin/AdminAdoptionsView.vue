<template>
  <div class="card-panel admin-card">
    <h2 class="section-title">领养审核</h2>
    <el-table v-loading="loading" :data="list" style="width:100%" border>
      <el-table-column prop="id" label="申请ID" width="90" />
      <el-table-column prop="userId" label="用户ID" width="90" />
      <el-table-column prop="petId" label="宠物ID" width="90" />
      <el-table-column prop="applyDescription" label="申请说明" min-width="300">
        <template slot-scope="{ row }">
          <div class="desc-cell">{{ row.applyDescription }}</div>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="120">
        <template slot-scope="{ row }">
          <status-tag :value="row.status" />
        </template>
      </el-table-column>
      <el-table-column label="操作" width="140" align="center">
        <template slot-scope="{ row }">
          <el-button
            type="text"
            class="action-link"
            :class="row.status !== 'WAITING' ? 'disabled' : ''"
            :disabled="row.status !== 'WAITING'"
            @click="openAudit(row)"
          >
            {{ row.status === 'WAITING' ? '审核' : '已处理' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="auditVisible" title="审核申请" width="480px" :close-on-click-modal="false">
      <el-form :model="auditForm" label-width="90px">
        <el-form-item label="审核结果">
          <el-select v-model="auditForm.status" style="width:100%">
            <el-option label="通过" value="PASSED" />
            <el-option label="拒绝" value="REJECTED" />
          </el-select>
        </el-form-item>
        <el-form-item label="审核意见">
          <el-input v-model="auditForm.auditRemark" type="textarea" :rows="4" placeholder="请输入审核意见（可选）" />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="auditVisible = false">取消</el-button>
        <el-button type="primary" @click="submitAudit">提交</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { auditAdoption, getAdminAdoptions } from '../../api/adoption'
import StatusTag from '../../components/common/StatusTag.vue'

export default {
  name: 'AdminAdoptionsView',
  components: { StatusTag },
  data() {
    return {
      loading: false,
      list: [],
      auditVisible: false,
      currentId: null,
      auditForm: {
        status: 'PASSED',
        auditRemark: ''
      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    async fetchData() {
      this.loading = true
      try {
        this.list = await getAdminAdoptions()
      } finally {
        this.loading = false
      }
    },
    openAudit(row) {
      this.currentId = row.id
      this.auditForm = {
        status: 'PASSED',
        auditRemark: ''
      }
      this.auditVisible = true
    },
    async submitAudit() {
      await auditAdoption(this.currentId, this.auditForm)
      this.$message.success('审核成功')
      this.auditVisible = false
      this.fetchData()
    }
  }
}
</script>

<style scoped>
.admin-card {
  padding: 24px;
}

.desc-cell {
  max-width: 300px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.action-link {
  font-size: 13px;
  padding: 3px 6px;
  color: var(--brand-primary) !important;
}
.action-link:hover {
  opacity: 0.8;
}
.action-link.disabled {
  color: #bbb !important;
}
</style>