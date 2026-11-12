<template>
  <div>
    <app-header />
    <main class="page-container main-area">
      <div class="filter-bar">
        <div class="filter-tabs">
          <span
            class="filter-tab"
            :class="{ active: !query.type }"
            @click="setType('')"
          >全部</span>
          <span
            class="filter-tab"
            :class="{ active: query.type === 'cat' }"
            @click="setType('cat')"
          >猫</span>
          <span
            class="filter-tab"
            :class="{ active: query.type === 'dog' }"
            @click="setType('dog')"
          >狗</span>
          <span
            class="filter-tab"
            :class="{ active: query.type === 'other' }"
            @click="setType('other')"
          >其他</span>
        </div>
        <div class="filter-info">
          <span class="result-count">共 {{ pets.length }} 只宠物等待领养</span>
          <el-button class="reset-btn" @click="reset">显示全部</el-button>
        </div>
      </div>
      <div v-loading="loading" class="pet-grid">
        <pet-card v-for="pet in pets" :key="pet.id" :pet="pet" />
      </div>
      <div v-if="!loading && !pets.length" class="empty-text">
        <div class="empty-icon">🐾</div>
        <p>暂时没有符合条件的宠物信息</p>
      </div>
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
    this.query.keyword = this.$route.query.keyword || ''
    this.query.type = this.$route.query.type || ''
    this.fetchPets()
  },
  beforeRouteUpdate(to, from, next) {
    this.query.keyword = to.query.keyword || ''
    this.query.type = to.query.type || ''
    this.fetchPets()
    next()
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
    setType(type) {
      if (this.query.type === type) return
      this.query.type = type
      this.fetchPets()
    },
    reset() {
      this.query.keyword = ''
      this.query.type = ''
      this.$router.replace({ query: {} })
      this.fetchPets()
    }
  }
}
</script>

<style scoped>
.main-area {
  padding: 16px 0 40px;
}

.filter-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #fff;
  border-radius: 4px;
  padding: 12px 20px;
  margin-bottom: 16px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.04);
  flex-wrap: wrap;
  gap: 10px;
}

.filter-tabs {
  display: flex;
  gap: 0;
}

.filter-tab {
  padding: 6px 16px;
  font-size: 13px;
  color: #666;
  cursor: pointer;
  border-radius: 2px;
  transition: all 0.2s;
}

.filter-tab:hover {
  color: var(--brand-primary);
  background: var(--brand-light);
}

.filter-tab.active {
  color: #fff;
  background: var(--brand-gradient);
  font-weight: 600;
}

.filter-info {
  display: flex;
  align-items: center;
  gap: 12px;
}

.result-count {
  font-size: 13px;
  color: var(--text-sub);
}

.reset-btn {
  font-size: 12px;
  padding: 5px 12px;
}

.pet-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(260px, 1fr));
  gap: 16px;
}

.empty-icon {
  font-size: 48px;
  margin-bottom: 10px;
}

.empty-text p {
  color: var(--text-sub);
  font-size: 14px;
}
</style>