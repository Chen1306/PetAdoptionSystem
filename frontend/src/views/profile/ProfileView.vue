<template>
  <div>
    <app-header />
    <main class="page-container profile-page">
      <div class="profile-grid">
        <div class="card-panel profile-card">
          <h2 class="section-title">个人信息</h2>
          <div class="profile-row"><span>用户名</span><strong>{{ profile.username }}</strong></div>
          <div class="profile-row"><span>手机号</span><strong>{{ profile.phone }}</strong></div>
          <div class="profile-row"><span>角色</span><strong>{{ profile.role }}</strong></div>
          <div class="profile-row"><span>状态</span><strong>{{ profile.status }}</strong></div>
        </div>
        <div class="card-panel profile-card">
          <h2 class="section-title">修改资料</h2>
          <el-form :model="form" label-position="top">
            <el-form-item label="用户名">
              <el-input v-model="form.username" />
            </el-form-item>
            <el-form-item label="手机号">
              <el-input v-model="form.phone" />
            </el-form-item>
            <el-form-item label="新密码">
              <el-input v-model="form.password" type="password" show-password />
            </el-form-item>
            <el-button type="primary" :loading="loading" @click="submit">保存修改</el-button>
          </el-form>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import { getProfile, updateProfile } from '../../api/user'
import AppHeader from '../../components/layout/AppHeader.vue'

export default {
  name: 'ProfileView',
  components: { AppHeader },
  data() {
    return {
      loading: false,
      profile: {},
      form: {
        username: '',
        phone: '',
        password: ''
      }
    }
  },
  created() {
    this.fetchProfile()
  },
  methods: {
    async fetchProfile() {
      const data = await getProfile()
      this.profile = data
      this.form.username = data.username || ''
      this.form.phone = data.phone || ''
    },
    async submit() {
      this.loading = true
      try {
        await updateProfile(this.form)
        this.$message.success('修改成功')
        this.fetchProfile()
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style scoped>
.profile-page {
  padding: 28px 0 40px;
}

.profile-grid {
  display: grid;
  grid-template-columns: 0.95fr 1.05fr;
  gap: 20px;
}

.profile-card {
  padding: 24px;
}

.profile-row {
  display: flex;
  justify-content: space-between;
  padding: 14px 0;
  border-bottom: 1px solid var(--border-color);
}

.profile-row span {
  color: var(--text-sub);
}
</style>
