<template>
  <div class="pet-card" @click="$router.push(`/pets/${pet.id}`)">
    <div class="pet-image">
      <img
        :src="pet.imageUrl || 'https://images.unsplash.com/photo-1517849845537-4d257902454a?auto=format&fit=crop&w=800&q=80'"
        :alt="pet.petName"
      >
      <span class="pet-type-tag">{{ typeLabel }}</span>
    </div>
    <div class="pet-content">
      <h3 class="pet-name">{{ pet.petName }}</h3>
      <p class="pet-desc">{{ pet.description || '暂无描述' }}</p>
      <div class="pet-meta">
        <span>{{ pet.age }}岁</span>
        <span class="divider">|</span>
        <span>{{ pet.gender || '未知' }}</span>
        <span class="divider">|</span>
        <span class="adopt-status" :class="pet.adoptStatus">
          {{ pet.adoptStatus === 'AVAILABLE' ? '可领养' : '已领养' }}
        </span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PetCard',
  props: {
    pet: {
      type: Object,
      required: true
    }
  },
  computed: {
    typeLabel() {
      const map = { cat: '猫', dog: '狗', other: '其他' }
      return map[this.pet.type] || this.pet.type
    }
  }
}
</script>

<style scoped>
.pet-card {
  background: #fff;
  border-radius: 4px;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.25s;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.06);
}

.pet-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
}

.pet-image {
  position: relative;
  width: 100%;
  padding-top: 100%;
  overflow: hidden;
  background: #fafafa;
}

.pet-image img {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.4s;
}

.pet-card:hover .pet-image img {
  transform: scale(1.05);
}

.pet-type-tag {
  position: absolute;
  top: 8px;
  left: 8px;
  padding: 2px 10px;
  background: var(--brand-gradient);
  color: #fff;
  font-size: 12px;
  border-radius: 2px;
  line-height: 20px;
}

.pet-content {
  padding: 12px 14px 14px;
}

.pet-name {
  margin: 0 0 6px;
  font-size: 14px;
  font-weight: 600;
  color: var(--text-main);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.pet-desc {
  margin: 0 0 8px;
  font-size: 12px;
  color: var(--text-sub);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.pet-meta {
  font-size: 12px;
  color: var(--text-sub);
  display: flex;
  align-items: center;
  gap: 4px;
}

.divider {
  color: #ddd;
}

.adopt-status {
  margin-left: auto;
  font-weight: 600;
}

.adopt-status.AVAILABLE {
  color: var(--brand-primary);
}

.adopt-status.ADOPTED {
  color: var(--text-sub);
}
</style>