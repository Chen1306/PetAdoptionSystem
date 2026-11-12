<template>
  <div class="auth-page">
    <div class="auth-card">
      <div class="auth-side">
        <div class="side-content">
          <div class="side-logo">宠</div>
          <h1>宠物领养平台</h1>
          <p>为每一只等待家的动物，找到认真生活的人</p>
        </div>
      </div>
      <div class="auth-form-wrap">
        <div class="auth-form">
          <h2>账号登录</h2>
          <p class="form-subtitle">欢迎回来，请登录你的账号</p>
          <el-form ref="formRef" :model="form" :rules="rules" label-position="top">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="form.username" placeholder="请输入用户名" prefix-icon="el-icon-user" />
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="form.password" type="password" placeholder="请输入密码" show-password prefix-icon="el-icon-lock" />
            </el-form-item>
            <el-button class="submit-btn" :loading="loading" @click="submit">登录</el-button>
            <div class="auth-footer">
              没有账号？
              <router-link to="/register">立即注册</router-link>
            </div>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { login } from '../../api/auth'
import { setAuth } from '../../utils/auth'

export default {
  name: 'LoginView',
  data() {
    return {
      loading: false,
      form: {
        username: '',
        password: ''
      },
      rules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      }
    }
  },
  methods: {
    submit() {
      this.$refs.formRef.validate(async valid => {
        if (!valid) return
        this.loading = true
        try {
          const data = await login(this.form)
          setAuth(data)
          this.$message.success('登录成功')
          this.$router.push(data.role === 'admin' ? '/admin' : '/pets')
        } catch {
          // error message already shown by interceptor
        } finally {
          this.loading = false
        }
      })
    }
  }
}
</script>

<style scoped>
.auth-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 24px;
  background: var(--page-bg);
}

.auth-card {
  width: min(880px, 100%);
  display: grid;
  grid-template-columns: 1.1fr 0.9fr;
  overflow: hidden;
  border-radius: 8px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  background: #fff;
}

.auth-side {
  background: var(--brand-gradient);
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 48px;
}

.side-content {
  text-align: center;
}

.side-logo {
  width: 72px;
  height: 72px;
  background: #fff;
  color: var(--brand-primary);
  border-radius: 50%;
  font-size: 36px;
  font-weight: 800;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 24px;
}

.side-content h1 {
  font-size: 32px;
  margin: 0 0 16px;
  font-weight: 700;
}

.side-content p {
  font-size: 15px;
  line-height: 1.8;
  opacity: 0.85;
  margin: 0;
}

.auth-form-wrap {
  padding: 48px;
  display: flex;
  align-items: center;
}

.auth-form {
  width: 100%;
}

.auth-form h2 {
  margin: 0 0 4px;
  font-size: 24px;
  font-weight: 700;
}

.form-subtitle {
  margin: 0 0 28px;
  color: var(--text-sub);
  font-size: 14px;
}

.submit-btn {
  width: 100%;
  height: 44px;
  font-size: 16px;
  font-weight: 700;
  border: none;
  background: var(--brand-gradient) !important;
  color: #fff !important;
  border-radius: 22px;
  margin-top: 6px;
}

.submit-btn:hover {
  opacity: 0.9;
}

.auth-footer {
  margin-top: 20px;
  text-align: center;
  color: var(--text-sub);
  font-size: 13px;
}

.auth-footer a {
  color: var(--brand-primary);
  font-weight: 600;
}
</style>