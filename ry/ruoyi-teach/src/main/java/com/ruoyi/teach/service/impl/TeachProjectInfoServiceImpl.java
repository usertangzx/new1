package com.ruoyi.teach.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.teach.domain.entity.TeachProjectInfo;
import com.ruoyi.teach.mapper.TeachProjectInfoMapper;
import com.ruoyi.teach.service.ITeachProjectInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeachProjectInfoServiceImpl implements ITeachProjectInfoService {

    private final TeachProjectInfoMapper projectInfoMapper;

    @Override
    public List<TeachProjectInfo> findByConditions(String prjName, String prjCode, String manager, String prjStatus) {
        return projectInfoMapper.findByConditions(prjName, prjCode, manager, prjStatus);
    }

    @Override
    public int insert(TeachProjectInfo projectInfo) {
        projectInfo.setCreateTime(DateUtils.getNowDate());
        return projectInfoMapper.insert(projectInfo);
    }


    @Override
    public int update(TeachProjectInfo projectInfo) {
        projectInfo.setUpdateTime(DateUtils.getNowDate());
        return projectInfoMapper.update(projectInfo);
    }

    @Override
    public int deleteByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }
        return projectInfoMapper.deleteByIds(ids);
    }

}
