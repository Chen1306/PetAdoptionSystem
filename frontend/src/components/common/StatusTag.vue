<template>
  <el-tag :type="tagType" effect="light">{{ text }}</el-tag>
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
    tagType() {
      const map = {
        WAITING: 'warning',
        PASSED: 'success',
        REJECTED: 'danger',
        CANCELED: 'info',
        APPROVED: 'success',
        PENDING: 'warning',
        AVAILABLE: 'success',
        ADOPTED: 'info',
        ENABLED: 'success',
        DISABLED: 'danger'
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
