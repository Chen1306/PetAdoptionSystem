<template>
  <div>
    <div class="stats-grid">
      <div class="stat-card card-panel">
        <span>用户总数</span>
        <strong>{{ overview.userCount || 0 }}</strong>
      </div>
      <div class="stat-card card-panel">
        <span>宠物总数</span>
        <strong>{{ overview.petCount || 0 }}</strong>
      </div>
      <div class="stat-card card-panel">
        <span>申请总数</span>
        <strong>{{ overview.adoptionCount || 0 }}</strong>
      </div>
      <div class="stat-card card-panel">
        <span>待审核申请</span>
        <strong>{{ overview.waitingCount || 0 }}</strong>
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
  padding: 28px;
}

.stat-card span {
  color: var(--text-sub);
}

.stat-card strong {
  display: block;
  margin-top: 12px;
  font-size: 40px;
}
</style>
