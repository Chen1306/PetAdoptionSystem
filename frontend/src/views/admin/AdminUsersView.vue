<template>
  <div class="card-panel admin-card">
    <h2 class="section-title">用户管理</h2>
    <el-table v-loading="loading" :data="list" style="width:100%" border>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="phone" label="手机号" />
      <el-table-column label="角色" width="120">
        <template slot-scope="{ row }">
          <el-tag :type="row.role === 'admin' ? 'danger' : 'primary'" size="mini" effect="dark">
            {{ row.role === 'admin' ? '管理员' : '用户' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="120">
        <template slot-scope="{ row }">
          <status-tag :value="row.status" />
        </template>
      </el-table-column>
      <el-table-column label="操作" width="160" align="center">
        <template slot-scope="{ row }">
          <el-button
            type="text"
            class="action-link"
            :class="row.status === 'ENABLED' ? 'danger' : 'success'"
            @click="toggleStatus(row)"
          >
            {{ row.status === 'ENABLED' ? '禁用' : '启用' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { getUsers, updateUserStatus } from '../../api/admin'
import StatusTag from '../../components/common/StatusTag.vue'

export default {
  name: 'AdminUsersView',
  components: { StatusTag },
  data() {
    return {
      loading: false,
      list: []
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    async fetchData() {
      this.loading = true
      try {
        this.list = await getUsers()
      } finally {
        this.loading = false
      }
    },
    async toggleStatus(row) {
      const nextStatus = row.status === 'ENABLED' ? 'DISABLED' : 'ENABLED'
      const actionText = nextStatus === 'ENABLED' ? '启用' : '禁用'
      await this.$confirm(`确认${actionText}用户「${row.username}」吗？`, '提示', { type: 'warning' })
      await updateUserStatus(row.id, { status: nextStatus })
      this.$message.success('状态更新成功')
      this.fetchData()
    }
  }
}
</script>

<style scoped>
.admin-card {
  padding: 24px;
}

.action-link {
  font-size: 13px;
  padding: 3px 6px;
}
.action-link.danger {
  color: var(--danger) !important;
}
.action-link.success {
  color: #00A000 !important;
}
</style>