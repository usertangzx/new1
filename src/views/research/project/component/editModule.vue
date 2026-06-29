<template>
  <div class="research-project-dialog">
    <el-dialog v-model="isShowDialog" width="760px">
      <template #title>
        <div style="font-size: large" v-drag="['.research-project-dialog .el-dialog', '.research-project-dialog .el-dialog__header']">
          {{ title }}
        </div>
      </template>
      <el-form :model="ruleForm" size="small" :rules="ruleRules" ref="ruleFormRef" label-width="90px">
        <el-row :gutter="24">
          <el-col :span="12" class="mb20">
            <el-form-item label="项目名称" prop="prjName">
              <el-input v-model="ruleForm.prjName" placeholder="请输入项目名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12" class="mb20">
            <el-form-item label="项目编号" prop="prjCode">
              <el-input v-model="ruleForm.prjCode" placeholder="请输入项目编号" />
            </el-form-item>
          </el-col>
          <el-col :span="12" class="mb20">
            <el-form-item label="项目类型" prop="prjType">
              <el-input-number v-model="ruleForm.prjType" :min="0" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12" class="mb20">
            <el-form-item label="项目状态1" prop="prjStatus">
              <el-select v-model="ruleForm.prjStatus" placeholder="请选择项目状态" style="width: 100%">
                <el-option label="正常" value="正常" />
                <el-option label="停用" value="停用" />
                <el-option label="0" value="0" />
                <el-option label="1" value="1" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12" class="mb20">
            <el-form-item label="负责人" prop="manager">
              <el-input v-model="ruleForm.manager" placeholder="请输入负责人" />
            </el-form-item>
          </el-col>
          <el-col :span="12" class="mb20">
            <el-form-item label="金额" prop="money">
              <el-input-number v-model="ruleForm.money" :min="0" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12" class="mb20">
            <el-form-item label="开始日期" prop="startDate">
              <el-date-picker v-model="ruleForm.startDate" type="date" value-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择开始日期" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12" class="mb20">
            <el-form-item label="结束日期" prop="endDate">
              <el-date-picker v-model="ruleForm.endDate" type="date" value-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择结束日期" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="24" class="mb20">
            <el-form-item label="项目描述" prop="prjDesc">
              <el-input v-model="ruleForm.prjDesc" type="textarea" placeholder="请输入项目描述" />
            </el-form-item>
          </el-col>
          <el-col :span="24" class="mb20">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="ruleForm.remark" type="textarea" placeholder="请输入备注" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="onCancel" size="small">取消</el-button>
          <el-button type="primary" @click="onSubmit" size="small">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { reactive, toRefs, ref, unref, getCurrentInstance } from 'vue';
import { addProject, updateProject } from '/@/api/research/project';
import { ElMessage } from 'element-plus';

export default {
  name: 'editProject',
  props: {
    title: {
      type: String,
      default: () => '',
    },
  },
  setup() {
    const { proxy } = getCurrentInstance() as any;
    const ruleFormRef = ref<HTMLElement | null>(null);
    const state = reactive({
      isShowDialog: false,
      ruleForm: {
        id: 0,
        prjName: '',
        prjCode: '',
        prjType: undefined as number | undefined,
        prjStatus: '正常',
        prjDesc: '',
        startDate: '',
        endDate: '',
        manager: '',
        money: undefined as number | undefined,
        remark: '',
      },
      ruleRules: {
        prjName: [{ required: true, message: '项目名称不能为空', trigger: 'blur' }],
        prjCode: [{ required: true, message: '项目编号不能为空', trigger: 'blur' }],
      },
    });

    const openDialog = (row: any) => {
      if (row && row.id) {
        state.ruleForm = {
          id: row.id,
          prjName: row.prjName || '',
          prjCode: row.prjCode || '',
          prjType: row.prjType,
          prjStatus: row.prjStatus || '正常',
          prjDesc: row.prjDesc || '',
          startDate: row.startDate || '',
          endDate: row.endDate || '',
          manager: row.manager || '',
          money: row.money,
          remark: row.remark || '',
        };
      } else {
        initForm();
      }
      state.isShowDialog = true;
    };

    const closeDialog = () => {
      proxy.mittBus.emit('onEditProjectModule');
      state.isShowDialog = false;
    };

    const onCancel = () => {
      closeDialog();
    };

    const onSubmit = () => {
      const formWrap = unref(ruleFormRef) as any;
      if (!formWrap) return;
      formWrap.validate((valid: boolean) => {
        if (!valid) return;
        const request = state.ruleForm.id ? updateProject : addProject;
        request(state.ruleForm).then(() => {
          ElMessage.success(state.ruleForm.id ? '修改成功' : '新增成功');
          closeDialog();
        });
      });
    };

    const initForm = () => {
      state.ruleForm = {
        id: 0,
        prjName: '',
        prjCode: '',
        prjType: undefined,
        prjStatus: '正常',
        prjDesc: '',
        startDate: '',
        endDate: '',
        manager: '',
        money: undefined,
        remark: '',
      };
    };

    return {
      ruleFormRef,
      openDialog,
      closeDialog,
      onCancel,
      initForm,
      onSubmit,
      ...toRefs(state),
    };
  },
};
</script>
