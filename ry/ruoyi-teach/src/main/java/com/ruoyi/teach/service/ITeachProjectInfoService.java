package com.ruoyi.teach.service;

import com.ruoyi.teach.domain.entity.TeachProjectInfo;

import java.util.List;

public interface ITeachProjectInfoService {

    List<TeachProjectInfo> findByConditions(String prjName, String prjCode, String manager, String prjStatus);

    int insert(TeachProjectInfo projectInfo);

    int update(TeachProjectInfo projectInfo);

    int deleteByIds(List<Integer> ids);
}
