<template>
  <div>
    <app-header />
    <section class="hero">
      <div class="page-container hero-inner">
        <div>
          <div class="hero-kicker">救助 · 领养 · 陪伴</div>
          <h1>为每一只等待家的动物，找到认真生活的人</h1>
          <p>浏览已通过审核的宠物信息，提交领养申请，跟进审核进度。</p>
        </div>
      </div>
    </section>
    <main class="page-container main-area">
      <div class="filter-bar card-panel">
        <el-input v-model="query.keyword" placeholder="搜索宠物名称或描述" clearable />
        <el-select v-model="query.type" placeholder="宠物类型" clearable>
          <el-option label="猫" value="cat" />
          <el-option label="狗" value="dog" />
          <el-option label="其他" value="other" />
        </el-select>
        <el-button type="primary" @click="fetchPets">查询</el-button>
        <el-button @click="reset">重置</el-button>
      </div>
      <div v-loading="loading" class="pet-grid">
        <pet-card v-for="pet in pets" :key="pet.id" :pet="pet" />
      </div>
      <div v-if="!loading && !pets.length" class="empty-text">暂时没有符合条件的宠物信息</div>
    </main>
  </div>
</template>

<script>
import { getPetList } from '../../api/pet'
import AppHeader from '../../components/layout/AppHeader.vue'
import PetCard from '../../components/pet/PetCard.vue'

export default {
  name: 'PetListView',
  components: { AppHeader, PetCard },
  data() {
    return {
      loading: false,
      pets: [],
      query: {
        keyword: '',
        type: ''
      }
    }
  },
  created() {
    this.fetchPets()
  },
  methods: {
    async fetchPets() {
      this.loading = true
      try {
        this.pets = await getPetList(this.query)
      } finally {
        this.loading = false
      }
    },
    reset() {
      this.query.keyword = ''
      this.query.type = ''
      this.fetchPets()
    }
  }
}
</script>

<style scoped>
.hero {
  padding: 52px 0 24px;
}

.hero-inner {
  background: linear-gradient(135deg, rgba(47, 133, 90, 0.94), rgba(116, 171, 115, 0.92));
  color: #fff;
  border-radius: 24px;
  padding: 48px;
}

.hero-kicker {
  font-size: 14px;
  letter-spacing: 2px;
  text-transform: uppercase;
  opacity: 0.85;
}

.hero h1 {
  margin: 12px 0 18px;
  font-size: clamp(34px, 5vw, 54px);
  line-height: 1.15;
}

.hero p {
  max-width: 640px;
  font-size: 17px;
  line-height: 1.8;
}

.main-area {
  padding: 20px 0 40px;
}

.filter-bar {
  display: grid;
  grid-template-columns: 1.7fr 1fr auto auto;
  gap: 14px;
  padding: 20px;
  margin-bottom: 24px;
}

.pet-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 20px;
}
</style>
