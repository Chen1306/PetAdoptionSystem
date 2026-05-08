<template>
  <el-dialog :visible.sync="visible" title="提交领养申请" width="520px" @close="reset">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
      <el-form-item label="宠物名称">
        <span>{{ petName }}</span>
      </el-form-item>
      <el-form-item label="申请说明" prop="applyDescription">
        <el-input
          v-model="form.applyDescription"
          type="textarea"
          :rows="5"
          maxlength="500"
          show-word-limit
          placeholder="请填写你的领养理由、居住环境和照顾计划"
        />
      </el-form-item>
    </el-form>
    <span slot="footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" :loading="loading" @click="submit">提交</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { createAdoption } from '../../api/adoption'

export default {
  name: 'AdoptionDialog',
  props: {
    petId: {
      type: Number,
      default: null
    },
    petName: {
      type: String,
      default: ''
    },
    value: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      visible: this.value,
      loading: false,
      form: {
        applyDescription: ''
      },
      rules: {
        applyDescription: [{ required: true, message: '请输入申请说明', trigger: 'blur' }]
      }
    }
  },
  watch: {
    value(val) {
      this.visible = val
    },
    visible(val) {
      this.$emit('input', val)
    }
  },
  methods: {
    submit() {
      this.$refs.formRef.validate(async valid => {
        if (!valid) return
        this.loading = true
        try {
          await createAdoption({
            petId: this.petId,
            applyDescription: this.form.applyDescription
          })
          this.$message.success('申请已提交')
          this.visible = false
          this.$emit('success')
        } finally {
          this.loading = false
        }
      })
    },
    reset() {
      this.form.applyDescription = ''
    }
  }
}
</script>
