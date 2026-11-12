<template>
  <div class="auth-page">
    <div class="auth-card">
      <div class="auth-side">
        <div class="side-content">
          <div class="side-logo">宠</div>
          <h1>加入我们</h1>
          <p>注册账号，开启你的领养之旅</p>
        </div>
      </div>
      <div class="auth-form-wrap">
        <div class="auth-form">
          <h2>注册新用户</h2>
          <p class="form-subtitle">填写信息创建账号</p>
          <el-form ref="formRef" :model="form" :rules="rules" label-position="top">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="form.username" placeholder="请输入用户名" prefix-icon="el-icon-user" />
            </el-form-item>
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入手机号" prefix-icon="el-icon-phone" />
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="form.password" type="password" placeholder="请输入密码" show-password prefix-icon="el-icon-lock" />
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword">
              <el-input v-model="form.confirmPassword" type="password" placeholder="请确认密码" show-password prefix-icon="el-icon-lock" />
            </el-form-item>
            <el-button class="submit-btn" :loading="loading" @click="submit">注册</el-button>
            <div class="auth-footer">
              已有账号？
              <router-link to="/login">返回登录</router-link>
            </div>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { register } from '../../api/auth'

export default {
  name: 'RegisterView',
  data() {
    const validateConfirm = (rule, value, callback) => {
      if (value !== this.form.password) {
        callback(new Error('两次输入的密码不一致'))
        return
      }
      callback()
    }
    return {
      loading: false,
      form: {
        username: '',
        phone: '',
        password: '',
        confirmPassword: ''
      },
      rules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        confirmPassword: [{ validator: validateConfirm, trigger: 'blur' }]
      }
    }
  },
  methods: {
    submit() {
      this.$refs.formRef.validate(async valid => {
        if (!valid) return
        this.loading = true
        try {
          await register({
            username: this.form.username,
            phone: this.form.phone,
            password: this.form.password
          })
          this.$message.success('注册成功，请登录')
          this.$router.push('/login')
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
  padding: 36px 48px;
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
  margin: 0 0 24px;
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