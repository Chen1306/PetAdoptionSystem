<template>
  <div class="card-panel admin-card">
    <h2 class="section-title">用户管理</h2>
    <el-table v-loading="loading" :data="list" border>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="phone" label="手机号" />
      <el-table-column prop="role" label="角色" width="120" />
      <el-table-column label="状态" width="120">
        <template slot-scope="{ row }">
          <status-tag :value="row.status" />
        </template>
      </el-table-column>
      <el-table-column label="操作" width="160">
        <template slot-scope="{ row }">
          <el-button type="text" @click="toggleStatus(row)">
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
</style>
