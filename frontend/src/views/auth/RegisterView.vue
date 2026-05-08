<template>
  <div class="auth-page">
    <div class="register-card card-panel">
      <h2>注册新用户</h2>
      <el-form ref="formRef" :model="form" :rules="rules" label-position="top">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" show-password />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="form.confirmPassword" type="password" show-password />
        </el-form-item>
        <el-button type="primary" class="full-btn" :loading="loading" @click="submit">注册</el-button>
        <div class="auth-footer">
          已有账号？
          <router-link to="/login">返回登录</router-link>
        </div>
      </el-form>
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

.register-card {
  width: min(460px, 100%);
  padding: 36px;
}

.register-card h2 {
  margin: 0 0 24px;
}

.full-btn {
  width: 100%;
}

.auth-footer {
  margin-top: 18px;
  color: var(--text-sub);
}
</style>
