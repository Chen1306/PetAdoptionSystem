<template>
  <div>
    <app-header />
    <main class="page-container profile-page">
      <div class="profile-grid">
        <div class="card-panel profile-card">
          <div class="profile-card-header">
            <div class="avatar-ring">
              <span class="avatar-text">{{ profile.username ? profile.username.charAt(0).toUpperCase() : 'U' }}</span>
            </div>
            <h2 class="profile-name">{{ profile.username || '用户' }}</h2>
            <span class="profile-role-badge">{{ profile.role === 'admin' ? '管理员' : '普通用户' }}</span>
          </div>
          <div class="profile-stats">
            <div class="stat-item">
              <span class="stat-label">手机号</span>
              <span class="stat-value">{{ profile.phone || '未设置' }}</span>
            </div>
            <div class="stat-item">
              <span class="stat-label">角色</span>
              <span class="stat-value">{{ profile.role === 'admin' ? '管理员' : '普通用户' }}</span>
            </div>
            <div class="stat-item">
              <span class="stat-label">状态</span>
              <span class="stat-value" :class="profile.status">{{ profile.status === 'ENABLED' ? '正常' : '已禁用' }}</span>
            </div>
          </div>
        </div>
        <div class="card-panel profile-card">
          <h2 class="section-title" style="margin-bottom:24px">修改资料</h2>
          <el-form :model="form" label-position="top">
            <el-form-item label="用户名">
              <el-input v-model="form.username" placeholder="请输入用户名" />
            </el-form-item>
            <el-form-item label="手机号">
              <el-input v-model="form.phone" placeholder="请输入手机号" />
            </el-form-item>
            <el-form-item label="新密码（不修改请留空）">
              <el-input v-model="form.password" type="password" show-password placeholder="留空则不修改密码" />
            </el-form-item>
            <el-button class="save-btn" :loading="loading" @click="submit">保存修改</el-button>
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
  padding: 24px 0 40px;
}

.profile-grid {
  display: grid;
  grid-template-columns: 1fr 1.1fr;
  gap: 20px;
}

.profile-card {
  padding: 32px 24px;
}

.profile-card-header {
  text-align: center;
  padding-bottom: 24px;
  border-bottom: 1px solid #f0f0f0;
}

.avatar-ring {
  width: 72px;
  height: 72px;
  border-radius: 50%;
  background: var(--brand-gradient);
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 12px;
}

.avatar-text {
  font-size: 28px;
  font-weight: 700;
  color: #fff;
}

.profile-name {
  margin: 0 0 6px;
  font-size: 20px;
  font-weight: 700;
}

.profile-role-badge {
  display: inline-block;
  padding: 2px 14px;
  background: var(--brand-light);
  color: var(--brand-primary);
  font-size: 12px;
  border-radius: 12px;
}

.profile-stats {
  padding-top: 20px;
}

.stat-item {
  display: flex;
  justify-content: space-between;
  padding: 12px 0;
  border-bottom: 1px solid #f5f5f5;
  font-size: 14px;
}

.stat-item:last-child {
  border-bottom: none;
}

.stat-label {
  color: var(--text-sub);
}

.stat-value.ENABLED {
  color: #00A000;
  font-weight: 600;
}

.stat-value.DISABLED {
  color: var(--danger);
  font-weight: 600;
}

.save-btn {
  width: 100%;
  height: 40px;
  font-size: 15px;
  font-weight: 600;
  border: none;
  background: var(--brand-gradient) !important;
  color: #fff !important;
  border-radius: 4px;
  margin-top: 8px;
}

.save-btn:hover {
  opacity: 0.9;
}
</style>