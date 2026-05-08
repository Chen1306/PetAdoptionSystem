<template>
  <header class="app-header">
    <div class="page-container header-inner">
      <router-link class="brand" to="/pets">流浪动物领养平台</router-link>
      <nav class="nav-links">
        <router-link to="/pets">宠物列表</router-link>
        <router-link v-if="isUser" to="/my-adoptions">我的申请</router-link>
        <router-link v-if="isUser" to="/profile">个人中心</router-link>
      </nav>
      <div class="actions">
        <span v-if="user" class="welcome">你好，{{ user.username }}</span>
        <el-button v-if="!token" type="primary" size="mini" @click="$router.push('/login')">登录</el-button>
        <el-button v-else size="mini" @click="handleLogout">退出</el-button>
      </div>
    </div>
  </header>
</template>

<script>
import { clearAuth, getRole, getToken, getUser } from '../../utils/auth'

export default {
  name: 'AppHeader',
  data() {
    return {
      token: getToken(),
      user: getUser(),
      role: getRole()
    }
  },
  computed: {
    isUser() {
      return this.role === 'user'
    }
  },
  methods: {
    handleLogout() {
      clearAuth()
      this.$message.success('已退出登录')
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>
.app-header {
  position: sticky;
  top: 0;
  z-index: 20;
  background: rgba(255, 255, 255, 0.92);
  backdrop-filter: blur(10px);
  border-bottom: 1px solid rgba(229, 231, 235, 0.8);
}

.header-inner {
  display: flex;
  align-items: center;
  justify-content: space-between;
  min-height: 72px;
}

.brand {
  color: var(--brand-color);
  font-size: 22px;
  font-weight: 800;
}

.nav-links {
  display: flex;
  gap: 20px;
}

.nav-links a {
  color: var(--text-main);
  font-weight: 600;
}

.nav-links .router-link-exact-active {
  color: var(--brand-color);
}

.actions {
  display: flex;
  align-items: center;
  gap: 12px;
}

.welcome {
  color: var(--text-sub);
}
</style>
