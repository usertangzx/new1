package com.ruoyi.teach.mapper;


import com.ruoyi.teach.domain.entity.FalldownDevice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FalldownDeviceMapper {

    List<FalldownDevice> findByConditions(
            @Param("deviceCode") String deviceCode,
            @Param("model") String model,
            @Param("status") String status,
            @Param("pageNum") Integer pageNum,
            @Param("pageSize") Integer pageSize
    );

    FalldownDevice selectById(@Param("id") Long id);

    int insert(FalldownDevice falldownDevice);

    int update(FalldownDevice falldownDevice);

    int deleteByIds(@Param("ids") List<Long> ids);

}




