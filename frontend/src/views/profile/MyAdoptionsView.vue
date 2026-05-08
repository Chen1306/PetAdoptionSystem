<template>
  <div>
    <app-header />
    <main class="page-container profile-page">
      <div class="card-panel page-card">
        <h2 class="section-title">我的领养申请</h2>
        <el-table v-loading="loading" :data="list" border>
          <el-table-column prop="id" label="申请ID" width="90" />
          <el-table-column prop="petId" label="宠物ID" width="90" />
          <el-table-column prop="applyDescription" label="申请说明" min-width="260" />
          <el-table-column label="状态" width="120">
            <template slot-scope="{ row }">
              <status-tag :value="row.status" />
            </template>
          </el-table-column>
          <el-table-column prop="createdAt" label="创建时间" min-width="180" />
          <el-table-column label="操作" width="120">
            <template slot-scope="{ row }">
              <el-button
                v-if="row.status === 'WAITING'"
                type="text"
                @click="handleCancel(row.id)"
              >
                取消申请
              </el-button>
              <span v-else>-</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </main>
  </div>
</template>

<script>
import { cancelAdoption, getMyAdoptions } from '../../api/adoption'
import AppHeader from '../../components/layout/AppHeader.vue'
import StatusTag from '../../components/common/StatusTag.vue'

export default {
  name: 'MyAdoptionsView',
  components: { AppHeader, StatusTag },
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
        this.list = await getMyAdoptions()
      } finally {
        this.loading = false
      }
    },
    async handleCancel(id) {
      await this.$confirm('确认取消这条申请吗？', '提示', { type: 'warning' })
      await cancelAdoption(id)
      this.$message.success('取消成功')
      this.fetchData()
    }
  }
}
</script>

<style scoped>
.profile-page {
  padding: 28px 0 40px;
}

.page-card {
  padding: 24px;
}
</style>
