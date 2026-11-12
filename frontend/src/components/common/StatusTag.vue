<template>
  <span class="status-tag" :class="tagClass">{{ text }}</span>
</template>

<script>
export default {
  name: 'StatusTag',
  props: {
    value: {
      type: String,
      default: ''
    }
  },
  computed: {
    tagClass() {
      const map = {
        WAITING: 'tag-waiting',
        PASSED: 'tag-passed',
        REJECTED: 'tag-rejected',
        CANCELED: 'tag-canceled',
        APPROVED: 'tag-passed',
        PENDING: 'tag-waiting',
        AVAILABLE: 'tag-available',
        ADOPTED: 'tag-adopted',
        ENABLED: 'tag-passed',
        DISABLED: 'tag-rejected'
      }
      return map[this.value] || ''
    },
    text() {
      const map = {
        WAITING: '待审核',
        PASSED: '已通过',
        REJECTED: '已拒绝',
        CANCELED: '已取消',
        APPROVED: '已审核',
        PENDING: '待审核',
        AVAILABLE: '可领养',
        ADOPTED: '已领养',
        ENABLED: '启用',
        DISABLED: '禁用'
      }
      return map[this.value] || this.value
    }
  }
}
</script>

<style scoped>
.status-tag {
  display: inline-block;
  padding: 2px 12px;
  font-size: 12px;
  border-radius: 2px;
  line-height: 22px;
  font-weight: 500;
}

.tag-waiting {
  background: #FFF7E6;
  color: #FF6A00;
  border: 1px solid #FFD9B3;
}

.tag-passed {
  background: #F0FFF0;
  color: #00A000;
  border: 1px solid #B3FFB3;
}

.tag-rejected {
  background: #FFF0F0;
  color: #FF0036;
  border: 1px solid #FFB3B3;
}

.tag-canceled {
  background: #F5F5F5;
  color: #999;
  border: 1px solid #E0E0E0;
}

.tag-available {
  background: var(--brand-light);
  color: var(--brand-primary);
  border: 1px solid #FFCCB3;
}

.tag-adopted {
  background: #F5F5F5;
  color: #999;
  border: 1px solid #E0E0E0;
}
</style>