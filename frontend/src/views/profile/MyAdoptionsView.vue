<template>
  <div>
    <app-header />
    <main class="page-container profile-page">
      <div class="card-panel page-card">
        <h2 class="section-title">我的领养申请</h2>
        <el-table v-loading="loading" :data="list" style="width:100%">
          <el-table-column prop="id" label="申请ID" width="90" />
          <el-table-column prop="petId" label="宠物ID" width="90" />
          <el-table-column prop="applyDescription" label="申请说明" min-width="260">
            <template slot-scope="{ row }">
              <div class="desc-cell">{{ row.applyDescription }}</div>
            </template>
          </el-table-column>
          <el-table-column label="状态" width="120">
            <template slot-scope="{ row }">
              <status-tag :value="row.status" />
            </template>
          </el-table-column>
          <el-table-column prop="createdAt" label="创建时间" min-width="170" />
          <el-table-column label="操作" width="110" align="center">
            <template slot-scope="{ row }">
              <el-button
                v-if="row.status === 'WAITING'"
                type="text"
                class="cancel-btn"
                @click="handleCancel(row.id)"
              >
                取消申请
              </el-button>
              <span v-else class="no-action">-</span>
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
  padding: 24px 0 40px;
}

.page-card {
  padding: 24px;
}

.section-title {
  padding-bottom: 16px;
  border-bottom: 1px solid #f0f0f0;
  margin-bottom: 20px;
}

.desc-cell {
  max-width: 300px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.cancel-btn {
  color: var(--danger) !important;
  font-size: 13px;
}
.cancel-btn:hover {
  opacity: 0.8;
}

.no-action {
  color: #ccc;
}
</style>