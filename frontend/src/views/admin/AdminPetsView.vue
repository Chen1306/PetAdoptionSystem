<template>
  <div class="card-panel admin-card">
    <div class="toolbar">
      <h2 class="section-title">宠物管理</h2>
      <el-button type="primary" @click="openCreate">新增宠物</el-button>
    </div>
    <el-table v-loading="loading" :data="list" border>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="petName" label="宠物名称" />
      <el-table-column prop="type" label="类型" width="100" />
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
          <el-button type="text" @click="openEdit(row)">编辑</el-button>
          <el-button type="text" @click="openReview(row)">审核</el-button>
          <el-button type="text" style="color:#e05353" @click="remove(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="formVisible" :title="editingId ? '编辑宠物' : '新增宠物'" width="560px">
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
        <el-form-item label="性别"><el-input v-model="form.gender" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="form.description" type="textarea" :rows="4" /></el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="formVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">保存</el-button>
      </span>
    </el-dialog>

    <el-dialog :visible.sync="reviewVisible" title="审核宠物" width="480px">
      <el-form :model="reviewForm" label-width="90px">
        <el-form-item label="审核结果">
          <el-select v-model="reviewForm.reviewStatus" style="width:100%">
            <el-option label="审核通过" value="APPROVED" />
            <el-option label="驳回" value="REJECTED" />
          </el-select>
        </el-form-item>
        <el-form-item label="审核意见">
          <el-input v-model="reviewForm.reviewRemark" type="textarea" :rows="4" />
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
      }
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
      this.form = { ...row }
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
        reviewStatus: row.reviewStatus || 'APPROVED',
        reviewRemark: row.reviewRemark || ''
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
}
</style>
