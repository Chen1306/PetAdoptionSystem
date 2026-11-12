<template>
  <div>
    <div class="stats-grid">
      <div class="stat-card" style="background: linear-gradient(135deg, #FF4400, #FF6A00);">
        <div class="stat-icon"><i class="el-icon-user"></i></div>
        <div class="stat-body">
          <span class="stat-label">用户总数</span>
          <strong class="stat-number">{{ overview.userCount || 0 }}</strong>
        </div>
      </div>
      <div class="stat-card" style="background: linear-gradient(135deg, #FF6A00, #FF944D);">
        <div class="stat-icon"><i class="el-icon-s-goods"></i></div>
        <div class="stat-body">
          <span class="stat-label">宠物总数</span>
          <strong class="stat-number">{{ overview.petCount || 0 }}</strong>
        </div>
      </div>
      <div class="stat-card" style="background: linear-gradient(135deg, #FF944D, #FFB380);">
        <div class="stat-icon"><i class="el-icon-document"></i></div>
        <div class="stat-body">
          <span class="stat-label">申请总数</span>
          <strong class="stat-number">{{ overview.adoptionCount || 0 }}</strong>
        </div>
      </div>
      <div class="stat-card" style="background: linear-gradient(135deg, #FF6A00, #FF4400);">
        <div class="stat-icon"><i class="el-icon-warning"></i></div>
        <div class="stat-body">
          <span class="stat-label">待审核申请</span>
          <strong class="stat-number">{{ overview.waitingCount || 0 }}</strong>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getOverview } from '../../api/admin'

export default {
  name: 'AdminDashboardView',
  data() {
    return {
      overview: {}
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    async fetchData() {
      this.overview = await getOverview()
    }
  }
}
</script>

<style scoped>
.stats-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 18px;
}

.stat-card {
  padding: 24px;
  border-radius: 8px;
  color: #fff;
  display: flex;
  align-items: center;
  gap: 16px;
  box-shadow: 0 4px 14px rgba(255, 68, 0, 0.2);
}

.stat-icon {
  width: 52px;
  height: 52px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.stat-icon i {
  font-size: 24px;
  color: #fff;
}

.stat-body {
  flex: 1;
}

.stat-label {
  display: block;
  font-size: 13px;
  opacity: 0.85;
  margin-bottom: 4px;
}

.stat-number {
  display: block;
  font-size: 32px;
  font-weight: 700;
  line-height: 1;
}
</style>