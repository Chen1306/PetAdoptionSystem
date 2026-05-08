<template>
  <div class="auth-page">
    <div class="auth-card card-panel">
      <div class="auth-side">
        <h1>让领养更透明</h1>
        <p>连接救助与陪伴，让每一只流浪动物被认真看见。</p>
      </div>
      <div class="auth-form">
        <h2>账号登录</h2>
        <el-form ref="formRef" :model="form" :rules="rules" label-position="top">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="form.username" placeholder="请输入用户名" />
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="form.password" type="password" placeholder="请输入密码" show-password />
          </el-form-item>
          <el-button type="primary" class="full-btn" :loading="loading" @click="submit">登录</el-button>
          <div class="auth-footer">
            没有账号？
            <router-link to="/register">去注册</router-link>
          </div>
        </el-form>
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
}

.auth-card {
  width: min(980px, 100%);
  display: grid;
  grid-template-columns: 1.1fr 0.9fr;
  overflow: hidden;
}

.auth-side {
  padding: 56px;
  background: linear-gradient(135deg, #2f855a 0%, #6ca86d 100%);
  color: #fff;
}

.auth-side h1 {
  font-size: 42px;
  margin: 0 0 20px;
}

.auth-side p {
  font-size: 18px;
  line-height: 1.8;
}

.auth-form {
  padding: 48px;
}

.auth-form h2 {
  margin: 0 0 24px;
}

.full-btn {
  width: 100%;
  margin-top: 10px;
}

.auth-footer {
  margin-top: 18px;
  color: var(--text-sub);
}
</style>
