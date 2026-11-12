<template>
  <div>
    <app-header />
    <main class="page-container detail-page">
      <div class="breadcrumb">
        <router-link to="/pets">宠物列表</router-link>
        <span class="sep">></span>
        <span v-if="pet" class="current">{{ pet.petName }}</span>
      </div>
      <div v-loading="loading" class="detail-main" v-if="pet">
        <div class="detail-gallery">
          <div class="gallery-main">
            <img
              :src="pet.imageUrl || 'https://images.unsplash.com/photo-1548199973-03cce0bbc87b?auto=format&fit=crop&w=1200&q=80'"
              :alt="pet.petName"
            >
          </div>
        </div>
        <div class="detail-info">
          <div class="info-header">
            <span class="type-badge">{{ typeLabel }}</span>
            <span class="adopt-badge" :class="pet.adoptStatus">
              {{ pet.adoptStatus === 'AVAILABLE' ? '可领养' : '已领养' }}
            </span>
          </div>
          <h1 class="detail-title">{{ pet.petName }}</h1>
          <div class="detail-price-bar">
            <div class="price-area">
              <span class="price-label">领养编号：</span>
              <span class="price-value">{{ pet.id }}</span>
            </div>
          </div>
          <div class="detail-params">
            <div class="param-row">
              <span class="param-label">年龄</span>
              <span class="param-value">{{ pet.age }}岁</span>
            </div>
            <div class="param-row">
              <span class="param-label">性别</span>
              <span class="param-value">{{ pet.gender || '未知' }}</span>
            </div>
            <div class="param-row">
              <span class="param-label">类型</span>
              <span class="param-value">{{ typeLabel }}</span>
            </div>
            <div class="param-row">
              <span class="param-label">状态</span>
              <span class="param-value">{{ pet.adoptStatus === 'AVAILABLE' ? '可领养' : '已被领养' }}</span>
            </div>
          </div>
          <div class="detail-actions">
            <el-button
              class="apply-btn"
              :disabled="pet.adoptStatus !== 'AVAILABLE'"
              @click="handleApply"
            >
              {{ pet.adoptStatus === 'AVAILABLE' ? '立即申请领养' : '已被领养' }}
            </el-button>
            <el-button class="back-btn" @click="$router.push('/pets')">返回列表</el-button>
          </div>
        </div>
      </div>
      <div class="detail-section" v-if="pet">
        <h2 class="section-title">宠物描述</h2>
        <div class="desc-content">{{ pet.description || '暂无描述' }}</div>
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
  computed: {
    typeLabel() {
      const map = { cat: '猫', dog: '狗', other: '其他' }
      return map[this.pet?.type] || this.pet?.type || ''
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
  padding: 12px 0 40px;
}

.breadcrumb {
  font-size: 12px;
  color: var(--text-sub);
  margin-bottom: 12px;
}

.breadcrumb a {
  color: var(--text-sub);
}

.breadcrumb a:hover {
  color: var(--brand-primary);
}

.sep {
  margin: 0 6px;
  color: #ccc;
}

.current {
  color: var(--text-main);
}

.detail-main {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 30px;
  background: #fff;
  border-radius: 4px;
  padding: 24px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.04);
}

.detail-gallery {
  position: relative;
}

.gallery-main {
  width: 100%;
  padding-top: 100%;
  position: relative;
  background: #fafafa;
  overflow: hidden;
  border-radius: 4px;
}

.gallery-main img {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.detail-info {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.info-header {
  display: flex;
  gap: 8px;
  align-items: center;
}

.type-badge {
  display: inline-block;
  padding: 2px 10px;
  background: var(--brand-light);
  color: var(--brand-primary);
  font-size: 12px;
  border-radius: 2px;
  line-height: 20px;
}

.adopt-badge {
  font-size: 12px;
  padding: 2px 10px;
  border-radius: 2px;
  line-height: 20px;
}

.adopt-badge.AVAILABLE {
  background: #FFF1EB;
  color: #FF4400;
}

.adopt-badge.ADOPTED {
  background: #F5F5F5;
  color: #999;
}

.detail-title {
  margin: 0;
  font-size: 24px;
  font-weight: 700;
  color: var(--text-main);
  line-height: 1.3;
}

.detail-price-bar {
  background: #FFF9F5;
  padding: 12px 16px;
  border-radius: 4px;
}

.price-area {
  display: flex;
  align-items: center;
  gap: 4px;
}

.price-label {
  font-size: 13px;
  color: #666;
}

.price-value {
  font-size: 20px;
  font-weight: 700;
  color: var(--brand-primary);
}

.detail-params {
  border-top: 1px dashed #eee;
  padding-top: 12px;
}

.param-row {
  display: flex;
  padding: 8px 0;
  border-bottom: 1px solid #f5f5f5;
  font-size: 14px;
}

.param-label {
  width: 80px;
  color: var(--text-sub);
  flex-shrink: 0;
}

.param-value {
  color: var(--text-main);
}

.detail-actions {
  margin-top: auto;
  padding-top: 16px;
  display: flex;
  gap: 12px;
}

.apply-btn {
  flex: 1;
  height: 44px;
  font-size: 16px;
  font-weight: 700;
  border: none;
  background: var(--brand-gradient) !important;
  color: #fff !important;
  border-radius: 22px;
}

.apply-btn:hover {
  opacity: 0.9;
}

.back-btn {
  height: 44px;
  font-size: 14px;
  border-radius: 22px;
  padding: 0 28px;
}

.detail-section {
  margin-top: 20px;
  background: #fff;
  border-radius: 4px;
  padding: 24px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.04);
}

.desc-content {
  font-size: 14px;
  line-height: 1.8;
  color: #666;
}
</style>