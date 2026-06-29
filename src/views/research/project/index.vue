<template>
  <div class="app-container">
    <el-card shadow="always">
      <el-form :model="queryParams" :inline="true" label-width="68px">
        <el-form-item label="项目名称">
          <el-input
            v-model="queryParams.prjName"
            placeholder="请输入项目名称"
            clearable
            size="small"
            style="width: 220px"
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="项目编号">
          <el-input
            v-model="queryParams.prjCode"
            placeholder="请输入项目编号"
            clearable
            size="small"
            style="width: 220px"
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="负责人">
          <el-input
            v-model="queryParams.manager"
            placeholder="请输入负责人"
            clearable
            size="small"
            style="width: 220px"
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="状态">
          <el-select
            v-model="queryParams.prjStatus"
            placeholder="状态"
            clearable
            size="small"
            style="width: 180px"
          >
            <el-option label="正常" value="正常" />
            <el-option label="停用" value="停用" />
            <el-option label="0" value="0" />
            <el-option label="1" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="onOpenAddModule">新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="onTableRowDel">
            删除
          </el-button>
        </el-col>
      </el-row>

      <el-table v-loading="loading" :data="tableData" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="项目名称" align="center" prop="prjName" />
        <el-table-column label="项目编号" align="center" prop="prjCode" />
        <el-table-column label="项目类型" align="center" prop="prjType" />
        <el-table-column label="负责人" align="center" prop="manager" />
        <el-table-column label="状态" align="center" prop="prjStatus">
          <template #default="scope">
            <el-tag :type="scope.row.prjStatus === '停用' || scope.row.prjStatus === '1' ? 'danger' : 'success'">
              {{ statusText(scope.row.prjStatus) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="金额" align="center" prop="money" />
        <el-table-column label="开始日期" align="center" prop="startDate">
          <template #default="scope">{{ formatDate(scope.row.startDate) }}</template>
        </el-table-column>
        <el-table-column label="结束日期" align="center" prop="endDate">
          <template #default="scope">{{ formatDate(scope.row.endDate) }}</template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-button size="mini" type="text" icon="el-icon-edit" @click="onOpenEditModule(scope.row)">修改</el-button>
            <el-button size="mini" type="text" icon="el-icon-delete" @click="onTableRowDel(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div v-show="total > 0">
        <el-divider></el-divider>
        <el-pagination
          background
          :total="total"
          :current-page="queryParams.pageNum"
          :page-size="queryParams.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <EditModule ref="editModuleRef" :title="title" />
  </div>
</template>

<script lang="ts">
import { ref, toRefs, reactive, onMounted, getCurrentInstance, onUnmounted } from 'vue';
import { ElMessageBox, ElMessage } from 'element-plus';
import { listProject, delProject } from '/@/api/research/project';
import EditModule from './component/editModule.vue';

export default {
  name: 'researchProject',
  components: { EditModule },
  setup() {
    const { proxy } = getCurrentInstance() as any;
    const editModuleRef = ref();
    const state = reactive({
      loading: true,
      ids: [] as number[],
      multiple: true,
      title: '',
      tableData: [] as any[],
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        prjName: undefined,
        prjCode: undefined,
        manager: undefined,
        prjStatus: undefined,
      } as any,
    });

    const handleQuery = () => {
      state.loading = true;
      listProject(state.queryParams).then((response) => {
        state.tableData = response.data.data;
        state.total = response.data.total;
        state.loading = false;
      }).catch(() => {
        state.loading = false;
      });
    };

    const resetQuery = () => {
      state.queryParams.prjName = undefined;
      state.queryParams.prjCode = undefined;
      state.queryParams.manager = undefined;
      state.queryParams.prjStatus = undefined;
      state.queryParams.pageNum = 1;
      handleQuery();
    };

    const handleCurrentChange = (val: number) => {
      state.queryParams.pageNum = val;
      handleQuery();
    };

    const handleSizeChange = (val: number) => {
      state.queryParams.pageSize = val;
      handleQuery();
    };

    const onOpenAddModule = () => {
      state.title = '添加科研项目';
      editModuleRef.value.openDialog({});
    };

    const onOpenEditModule = (row: any) => {
      state.title = '修改科研项目';
      editModuleRef.value.openDialog(row);
    };

    const onTableRowDel = (row?: any) => {
      const ids = row && row.id ? [row.id] : state.ids;
      const msg = row && row.id ? `是否确认删除项目"${row.prjName}"?` : `是否确认删除${ids.length}条数据?`;
      ElMessageBox({
        message: msg,
        title: '警告',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => delProject(JSON.stringify(ids))).then(() => {
        handleQuery();
        ElMessage.success('删除成功');
      });
    };

    const handleSelectionChange = (selection: any[]) => {
      state.ids = selection.map((item) => item.id);
      state.multiple = !selection.length;
    };

    const statusText = (status: string) => {
      if (status === '0') return '正常';
      if (status === '1') return '停用';
      return status || '';
    };

    const formatDate = (value: string) => {
      if (!value) return '';
      return proxy.parseTime(value, '{y}-{m}-{d}');
    };

    onMounted(() => {
      handleQuery();
      proxy.mittBus.on('onEditProjectModule', handleQuery);
    });

    onUnmounted(() => {
      proxy.mittBus.off('onEditProjectModule');
    });

    return {
      editModuleRef,
      handleSelectionChange,
      handleQuery,
      handleCurrentChange,
      handleSizeChange,
      resetQuery,
      onOpenAddModule,
      onOpenEditModule,
      onTableRowDel,
      statusText,
      formatDate,
      ...toRefs(state),
    };
  },
};
</script>
