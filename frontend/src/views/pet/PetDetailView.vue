<template>
  <div>
    <app-header />
    <main class="page-container detail-page">
      <div v-loading="loading" class="detail-card card-panel">
        <div class="detail-image">
          <img src="https://images.unsplash.com/photo-1548199973-03cce0bbc87b?auto=format&fit=crop&w=1200&q=80" alt="pet">
        </div>
        <div class="detail-info" v-if="pet">
          <el-tag type="success">{{ pet.type }}</el-tag>
          <h1>{{ pet.petName }}</h1>
          <p class="detail-meta">年龄：{{ pet.age }} 岁 ｜ 性别：{{ pet.gender || '未知' }}</p>
          <p class="detail-description">{{ pet.description || '暂无描述' }}</p>
          <div class="detail-actions">
            <el-button type="primary" @click="handleApply">申请领养</el-button>
            <el-button @click="$router.push('/pets')">返回列表</el-button>
          </div>
        </div>
      </div>
    </main>
    <adoption-dialog
      v-model="dialogVisible"
      :pet-id="Number($route.params.id)"
      :pet-name="pet ? pet.petName : ''"
      @success="$router.push('/my-adoptions')"
    />
  </div>
</template>

<script>
import { getPetDetail } from '../../api/pet'
import { getRole, isLoggedIn } from '../../utils/auth'
import AppHeader from '../../components/layout/AppHeader.vue'
import AdoptionDialog from '../../components/adoption/AdoptionDialog.vue'

export default {
  name: 'PetDetailView',
  components: { AppHeader, AdoptionDialog },
  data() {
    return {
      loading: false,
      pet: null,
      dialogVisible: false
    }
  },
  created() {
    this.fetchDetail()
  },
  methods: {
    async fetchDetail() {
      this.loading = true
      try {
        this.pet = await getPetDetail(this.$route.params.id)
      } finally {
        this.loading = false
      }
    },
    handleApply() {
      if (!isLoggedIn()) {
        this.$message.warning('请先登录')
        this.$router.push('/login')
        return
      }
      if (getRole() !== 'user') {
        this.$message.warning('管理员账号不能提交领养申请')
        return
      }
      this.dialogVisible = true
    }
  }
}
</script>

<style scoped>
.detail-page {
  padding: 28px 0 40px;
}

.detail-card {
  display: grid;
  grid-template-columns: 1fr 1fr;
  overflow: hidden;
}

.detail-image img {
  width: 100%;
  height: 100%;
  min-height: 460px;
  object-fit: cover;
}

.detail-info {
  padding: 40px;
}

.detail-info h1 {
  margin: 16px 0 16px;
  font-size: 40px;
}

.detail-meta {
  color: var(--text-sub);
}

.detail-description {
  margin: 24px 0 30px;
  line-height: 1.8;
  font-size: 16px;
}

.detail-actions {
  display: flex;
  gap: 14px;
}
</style>
