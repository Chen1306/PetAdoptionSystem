<template>
  <div class="pet-card card-panel">
    <div class="pet-image">
      <img src="https://images.unsplash.com/photo-1517849845537-4d257902454a?auto=format&fit=crop&w=800&q=80" alt="pet">
    </div>
    <div class="pet-content">
      <div class="pet-head">
        <h3>{{ pet.petName }}</h3>
        <el-tag size="mini" type="success">{{ pet.type }}</el-tag>
      </div>
      <p class="meta">年龄：{{ pet.age }} 岁 ｜ 性别：{{ pet.gender || '未知' }}</p>
      <p class="desc">{{ pet.description || '暂无描述' }}</p>
      <el-button type="primary" plain @click="$router.push(`/pets/${pet.id}`)">查看详情</el-button>
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
  }
}
</script>

<style scoped>
.pet-card {
  overflow: hidden;
}

.pet-image img {
  width: 100%;
  height: 220px;
  object-fit: cover;
  display: block;
}

.pet-content {
  padding: 18px;
}

.pet-head {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
}

.pet-head h3 {
  margin: 0;
  font-size: 20px;
}

.meta {
  color: var(--text-sub);
  margin: 10px 0;
}

.desc {
  min-height: 44px;
  color: #374151;
  line-height: 1.6;
}
</style>
