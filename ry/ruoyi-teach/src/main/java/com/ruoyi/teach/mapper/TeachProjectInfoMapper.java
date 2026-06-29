package com.ruoyi.teach.mapper;

import com.ruoyi.teach.domain.entity.TeachProjectInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TeachProjectInfoMapper {

    List<TeachProjectInfo> findByConditions(
            @Param("prjName") String prjName,
            @Param("prjCode") String prjCode,
            @Param("manager") String manager,
            @Param("prjStatus") String prjStatus
    );

    int insert(TeachProjectInfo projectInfo);

    int update(TeachProjectInfo projectInfo);

    int deleteByIds(@Param("ids") List<Integer> ids);

}
