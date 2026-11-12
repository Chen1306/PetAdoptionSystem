<template>
  <div class="card-panel admin-card">
    <div class="toolbar">
      <h2 class="section-title" style="margin:0">宠物管理</h2>
      <el-button type="primary" @click="openCreate">+ 新增宠物</el-button>
    </div>
    <el-table v-loading="loading" :data="list" style="width:100%" border>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="petName" label="宠物名称" />
      <el-table-column prop="type" label="类型" width="100">
        <template slot-scope="{ row }">{{ { cat: '猫', dog: '狗', other: '其他' }[row.type] || row.type }}</template>
      </el-table-column>
      <el-table-column prop="age" label="年龄" width="80" />
      <el-table-column label="审核状态" width="120">
        <template slot-scope="{ row }">
          <status-tag :value="row.reviewStatus" />
        </template>
      </el-table-column>
      <el-table-column label="领养状态" width="120">
        <template slot-scope="{ row }">
          <status-tag :value="row.adoptStatus" />
        </template>
      </el-table-column>
      <el-table-column label="操作" min-width="220">
        <template slot-scope="{ row }">
          <el-button type="text" class="action-link" @click="openEdit(row)">编辑</el-button>
          <el-button type="text" class="action-link" @click="openReview(row)">审核</el-button>
          <el-button type="text" class="action-link danger" @click="remove(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="formVisible" :title="editingId ? '编辑宠物' : '新增宠物'" width="560px" :close-on-click-modal="false">
      <el-form :model="form" label-width="90px">
        <el-form-item label="名称"><el-input v-model="form.petName" /></el-form-item>
        <el-form-item label="类型">
          <el-select v-model="form.type" style="width:100%">
            <el-option label="猫" value="cat" />
            <el-option label="狗" value="dog" />
            <el-option label="其他" value="other" />
          </el-select>
        </el-form-item>
        <el-form-item label="年龄"><el-input-number v-model="form.age" :min="0" /></el-form-item>
        <el-form-item label="性别">
          <el-select v-model="form.gender" style="width:100%">
            <el-option label="公" value="公" />
            <el-option label="母" value="母" />
            <el-option label="未知" value="未知" />
          </el-select>
        </el-form-item>
        <el-form-item label="图片">
          <el-upload
            class="pet-image-upload"
            :action="uploadAction"
            :headers="uploadHeaders"
            :show-file-list="false"
            :on-success="onUploadSuccess"
            :on-error="onUploadError"
            :before-upload="beforeUpload"
            accept="image/*"
          >
            <img v-if="form.imageUrl" :src="form.imageUrl" class="upload-preview" />
            <i v-else class="el-icon-plus upload-trigger" />
          </el-upload>
        </el-form-item>
        <el-form-item label="描述"><el-input v-model="form.description" type="textarea" :rows="4" /></el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="formVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">保存</el-button>
      </span>
    </el-dialog>

    <el-dialog :visible.sync="reviewVisible" title="审核宠物" width="480px" :close-on-click-modal="false">
      <el-form :model="reviewForm" label-width="90px">
        <el-form-item label="审核结果">
          <el-select v-model="reviewForm.reviewStatus" style="width:100%">
            <el-option label="审核通过" value="APPROVED" />
            <el-option label="驳回" value="REJECTED" />
          </el-select>
        </el-form-item>
        <el-form-item label="审核意见">
          <el-input v-model="reviewForm.reviewRemark" type="textarea" :rows="4" placeholder="请输入审核意见（可选）" />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="reviewVisible = false">取消</el-button>
        <el-button type="primary" @click="submitReview">提交审核</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { createPet, deletePet, getAdminPetList, reviewPet, updatePet } from '../../api/pet'
import StatusTag from '../../components/common/StatusTag.vue'
import { BASE_URL } from '../../utils/constants'
import { getToken } from '../../utils/auth'

const defaultForm = () => ({
  petName: '',
  type: 'cat',
  age: 0,
  gender: '',
  description: '',
  imageUrl: ''
})

export default {
  name: 'AdminPetsView',
  components: { StatusTag },
  data() {
    return {
      loading: false,
      list: [],
      formVisible: false,
      reviewVisible: false,
      editingId: null,
      reviewId: null,
      form: defaultForm(),
      reviewForm: {
        reviewStatus: 'APPROVED',
        reviewRemark: ''
      },
      uploadAction: BASE_URL + '/api/admin/pets/upload',
      uploadHeaders: { Authorization: 'Bearer ' + getToken() }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    async fetchData() {
      this.loading = true
      try {
        this.list = await getAdminPetList()
      } finally {
        this.loading = false
      }
    },
    openCreate() {
      this.editingId = null
      this.form = defaultForm()
      this.formVisible = true
    },
    openEdit(row) {
      this.editingId = row.id
      this.form = {
        petName: row.petName || '',
        type: row.type || 'cat',
        age: row.age ?? 0,
        gender: row.gender || '',
        description: row.description || '',
        imageUrl: row.imageUrl || ''
      }
      this.formVisible = true
    },
    async submitForm() {
      if (this.editingId) {
        await updatePet(this.editingId, this.form)
      } else {
        await createPet(this.form)
      }
      this.$message.success('保存成功')
      this.formVisible = false
      this.fetchData()
    },
    openReview(row) {
      this.reviewId = row.id
      this.reviewForm = {
        reviewStatus: 'APPROVED',
        reviewRemark: ''
      }
      this.reviewVisible = true
    },
    async submitReview() {
      await reviewPet(this.reviewId, this.reviewForm)
      this.$message.success('审核成功')
      this.reviewVisible = false
      this.fetchData()
    },
    async remove(id) {
      await this.$confirm('确认删除这条宠物信息吗？', '提示', { type: 'warning' })
      await deletePet(id)
      this.$message.success('删除成功')
      this.fetchData()
    },
    onUploadSuccess(res) {
      if (res.code === 200) {
        this.form.imageUrl = BASE_URL + res.data
        this.$message.success('上传成功')
      } else {
        this.$message.error(res.msg || '上传失败')
      }
    },
    onUploadError() {
      this.$message.error('上传失败，请重试')
    },
    beforeUpload(file) {
      const isImage = file.type.startsWith('image/')
      if (!isImage) {
        this.$message.error('只能上传图片文件')
        return false
      }
      const isLt5M = file.size / 1024 / 1024 < 5
      if (!isLt5M) {
        this.$message.error('图片大小不能超过 5MB')
        return false
      }
      return true
    }
  }
}
</script>

<style scoped>
.admin-card {
  padding: 24px;
}

.toolbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.action-link {
  font-size: 13px;
  padding: 3px 6px;
  color: var(--brand-primary) !important;
}
.action-link:hover {
  opacity: 0.8;
}
.action-link.danger {
  color: var(--danger) !important;
}

.pet-image-upload {
  display: inline-block;
}
.pet-image-upload >>> .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  width: 150px;
  height: 150px;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}
.pet-image-upload >>> .el-upload:hover {
  border-color: var(--brand-primary);
}
.upload-trigger {
  font-size: 28px;
  color: #8c939d;
}
.upload-preview {
  width: 150px;
  height: 150px;
  object-fit: cover;
  display: block;
}
</style>