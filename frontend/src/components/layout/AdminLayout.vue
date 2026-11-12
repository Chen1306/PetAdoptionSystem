<template>
  <div class="admin-layout">
    <aside class="sidebar">
      <div class="sidebar-header">
        <div class="sidebar-logo">宠</div>
        <div class="sidebar-title">管理后台</div>
      </div>
      <el-menu :default-active="$route.path" router>
        <el-menu-item index="/admin">
          <i class="el-icon-data-analysis"></i>
          <span slot="title">控制台</span>
        </el-menu-item>
        <el-menu-item index="/admin/users">
          <i class="el-icon-user"></i>
          <span slot="title">用户管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/pets">
          <i class="el-icon-s-goods"></i>
          <span slot="title">宠物管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/adoptions">
          <i class="el-icon-document"></i>
          <span slot="title">领养审核</span>
        </el-menu-item>
      </el-menu>
    </aside>
    <main class="main-area">
      <div class="admin-topbar">
        <div class="topbar-left">
          <router-link to="/pets" class="topbar-link">
            <i class="el-icon-s-home"></i> 前台首页
          </router-link>
        </div>
        <div class="topbar-right">
          <el-button class="logout-btn" @click="logout">退出登录</el-button>
        </div>
      </div>
      <div class="admin-content">
        <router-view />
      </div>
    </main>
  </div>
</template>

<script>
import { clearAuth } from '../../utils/auth'

export default {
  name: 'AdminLayout',
  methods: {
    logout() {
      clearAuth()
      this.$message.success('已退出登录')
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>
.admin-layout {
  display: flex;
  min-height: 100vh;
  background: #f0f2f5;
}

.sidebar {
  width: 220px;
  background: #1a1a2e;
  color: #fff;
  display: flex;
  flex-direction: column;
}

.sidebar-header {
  padding: 24px 20px 20px;
  display: flex;
  align-items: center;
  gap: 10px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.08);
}

.sidebar-logo {
  width: 36px;
  height: 36px;
  border-radius: 8px;
  background: var(--brand-gradient);
  color: #fff;
  font-size: 18px;
  font-weight: 800;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.sidebar-title {
  font-size: 18px;
  font-weight: 700;
}

.sidebar ::v-deep .el-menu {
  border-right: none;
  background: transparent;
  padding-top: 8px;
}

.sidebar ::v-deep .el-menu-item {
  color: rgba(255, 255, 255, 0.7);
  height: 46px;
  line-height: 46px;
  margin: 2px 8px;
  border-radius: 8px;
  font-size: 14px;
}

.sidebar ::v-deep .el-menu-item i {
  color: rgba(255, 255, 255, 0.6);
  margin-right: 8px;
}

.sidebar ::v-deep .el-menu-item:hover,
.sidebar ::v-deep .el-menu-item:focus {
  background: rgba(255, 255, 255, 0.08);
  color: #fff;
}

.sidebar ::v-deep .el-menu-item.is-active {
  color: #fff;
  background: var(--brand-gradient);
}

.sidebar ::v-deep .el-menu-item.is-active i {
  color: #fff;
}

.main-area {
  flex: 1;
  padding: 0;
  display: flex;
  flex-direction: column;
}

.admin-topbar {
  background: #fff;
  padding: 0 24px;
  height: 56px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.04);
}

.topbar-left {
  display: flex;
  align-items: center;
  gap: 16px;
}

.topbar-title {
  font-size: 17px;
  font-weight: 700;
}

.topbar-link {
  font-size: 13px;
  color: var(--text-sub);
}
.topbar-link:hover {
  color: var(--brand-primary);
}

.topbar-right {
  display: flex;
  align-items: center;
  gap: 12px;
}

.logout-btn {
  font-size: 12px;
  padding: 6px 14px;
  border-color: #ddd;
}

.admin-content {
  padding: 20px 24px;
  flex: 1;
}
</style>