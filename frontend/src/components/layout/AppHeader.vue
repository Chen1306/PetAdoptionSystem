<template>
  <header class="app-header">
    <div class="header-top">
      <div class="page-container top-inner">
        <router-link class="logo" to="/pets">
          <span class="logo-icon">宠</span>
          <span class="logo-text">宠物领养</span>
        </router-link>
        <div class="search-area">
          <div class="search-box">
            <el-input
              v-model="keyword"
              placeholder="搜索宠物名称或描述"
              class="search-input"
              @keyup.enter.native="doSearch"
            />
            <el-button class="search-btn" @click="doSearch">搜索</el-button>
          </div>
          <div class="hot-tags">
            <span class="hot-label">热门：</span>
            <span class="hot-tag" @click="searchTag('cat')">猫</span>
            <span class="hot-tag" @click="searchTag('dog')">狗</span>
            <span class="hot-tag" @click="searchTag('other')">其他</span>
          </div>
        </div>
        <div class="header-actions">
          <template v-if="token">
            <span class="user-greeting">你好，{{ user?.username || '用户' }}</span>
            <el-button v-if="isAdmin" class="action-btn" @click="$router.push('/admin')">后台管理</el-button>
            <el-button v-if="isUser" class="action-btn" @click="$router.push('/my-adoptions')">我的申请</el-button>
            <el-button class="action-btn" @click="$router.push('/profile')">个人中心</el-button>
            <el-button class="action-btn logout-btn" @click="handleLogout">退出</el-button>
          </template>
          <template v-else>
            <el-button class="action-btn" @click="$router.push('/login')">登录</el-button>
            <el-button class="action-btn register-btn" @click="$router.push('/register')">注册</el-button>
          </template>
        </div>
      </div>
    </div>
    <div class="header-nav">
      <div class="page-container nav-inner">
        <router-link class="nav-item" to="/pets">首页</router-link>
        <router-link class="nav-item" to="/pets?type=cat">猫咪</router-link>
        <router-link class="nav-item" to="/pets?type=dog">狗狗</router-link>
        <router-link class="nav-item" to="/pets?type=other">其他宠物</router-link>
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
      role: getRole(),
      keyword: ''
    }
  },
  computed: {
    isUser() {
      return this.role === 'user'
    },
    isAdmin() {
      return this.role === 'admin'
    }
  },
  methods: {
    doSearch() {
      if (this.keyword.trim()) {
        this.$router.push({ path: '/pets', query: { keyword: this.keyword.trim() } })
      } else {
        this.$router.push('/pets')
      }
    },
    searchTag(type) {
      this.$router.push({ path: '/pets', query: { type } })
    },
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
  z-index: 100;
}

.header-top {
  background: var(--brand-gradient);
  padding: 10px 0 8px;
}

.top-inner {
  display: flex;
  align-items: center;
  gap: 30px;
}

.logo {
  display: flex;
  align-items: center;
  gap: 8px;
  flex-shrink: 0;
  color: #fff !important;
}

.logo-icon {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 36px;
  height: 36px;
  background: #fff;
  color: var(--brand-primary);
  border-radius: 50%;
  font-size: 18px;
  font-weight: 800;
}

.logo-text {
  font-size: 22px;
  font-weight: 700;
  letter-spacing: 1px;
}

.search-area {
  flex: 1;
  max-width: 540px;
}

.search-box {
  display: flex;
  height: 40px;
}

.search-input >>> .el-input__inner {
  height: 40px;
  border-radius: 20px 0 0 20px;
  border: 2px solid #fff;
  border-right: none;
  background: #fff;
  font-size: 14px;
  padding: 0 16px;
}

.search-input >>> .el-input__inner:focus {
  border-color: #fff !important;
}

.search-btn {
  height: 40px;
  border-radius: 0 20px 20px 0;
  background: #fff;
  color: var(--brand-primary);
  border: 2px solid #fff;
  font-size: 15px;
  font-weight: 700;
  padding: 0 22px;
  cursor: pointer;
  transition: all 0.2s;
}
.search-btn:hover {
  background: #FFF5F0;
  color: var(--brand-primary-hover);
}

.hot-tags {
  margin-top: 6px;
  font-size: 12px;
  color: rgba(255, 255, 255, 0.85);
  display: flex;
  align-items: center;
  gap: 4px;
}

.hot-label {
  flex-shrink: 0;
}

.hot-tag {
  cursor: pointer;
  padding: 1px 8px;
  border-radius: 10px;
  transition: background 0.2s;
}

.hot-tag:hover {
  background: rgba(255, 255, 255, 0.2);
  color: #fff;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 8px;
  flex-shrink: 0;
}

.user-greeting {
  color: rgba(255, 255, 255, 0.9);
  font-size: 13px;
  white-space: nowrap;
}

.action-btn {
  height: 30px;
  padding: 0 14px;
  font-size: 12px;
  border-radius: 15px;
  background: rgba(255, 255, 255, 0.2);
  color: #fff !important;
  border: 1px solid rgba(255, 255, 255, 0.3);
  white-space: nowrap;
}
.action-btn:hover {
  background: rgba(255, 255, 255, 0.3);
  border-color: rgba(255, 255, 255, 0.5);
}

.logout-btn {
  background: rgba(255, 255, 255, 0.1);
}

.register-btn {
  background: #fff;
  color: var(--brand-primary) !important;
  border-color: #fff;
  font-weight: 600;
}
.register-btn:hover {
  background: #FFF5F0;
}

/* 导航栏 */
.header-nav {
  background: #fff;
  border-bottom: 1px solid #eee;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.04);
}

.nav-inner {
  display: flex;
  align-items: center;
  height: 40px;
  gap: 0;
}

.nav-item {
  padding: 0 18px;
  height: 40px;
  line-height: 40px;
  font-size: 14px;
  color: #666;
  transition: color 0.2s;
  position: relative;
}

.nav-item:hover,
.nav-item.router-link-exact-active {
  color: var(--brand-primary);
}

.nav-item.router-link-exact-active::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 18px;
  right: 18px;
  height: 2px;
  background: var(--brand-primary);
}
</style>