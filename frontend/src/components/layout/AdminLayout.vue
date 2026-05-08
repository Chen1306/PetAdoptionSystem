<template>
  <div class="admin-layout">
    <aside class="sidebar">
      <div class="sidebar-title">后台管理</div>
      <el-menu :default-active="$route.path" router>
        <el-menu-item index="/admin">控制台</el-menu-item>
        <el-menu-item index="/admin/users">用户管理</el-menu-item>
        <el-menu-item index="/admin/pets">宠物管理</el-menu-item>
        <el-menu-item index="/admin/adoptions">领养审核</el-menu-item>
      </el-menu>
    </aside>
    <main class="main-area">
      <div class="admin-topbar">
        <div>
          <div class="admin-title">流浪动物管理及领养系统</div>
          <div class="admin-subtitle">管理员工作台</div>
        </div>
        <div class="topbar-actions">
          <el-button size="mini" @click="$router.push('/pets')">前台首页</el-button>
          <el-button type="danger" size="mini" plain @click="logout">退出</el-button>
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
  background: #f3f6f8;
}

.sidebar {
  width: 240px;
  background: linear-gradient(180deg, #21543b 0%, #173f2c 100%);
  color: #fff;
  padding: 24px 0;
}

.sidebar-title {
  padding: 0 24px 18px;
  font-size: 22px;
  font-weight: 800;
}

.sidebar ::v-deep .el-menu {
  border-right: none;
  background: transparent;
}

.sidebar ::v-deep .el-menu-item {
  color: rgba(255, 255, 255, 0.84);
}

.sidebar ::v-deep .el-menu-item.is-active {
  color: #fff;
  background: rgba(255, 255, 255, 0.12);
}

.main-area {
  flex: 1;
  padding: 24px;
}

.admin-topbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.admin-title {
  font-size: 28px;
  font-weight: 800;
}

.admin-subtitle {
  margin-top: 6px;
  color: var(--text-sub);
}

.topbar-actions {
  display: flex;
  gap: 12px;
}

.admin-content {
  min-height: calc(100vh - 120px);
}
</style>
