package com.ruoyi.teach.service;

import com.ruoyi.teach.domain.entity.FalldownDevice;

import java.util.List;

public interface IFalldownDeviceService {

    List<FalldownDevice> findByConditions(
            String deviceCode,
            String model,
            String status,
            Integer pageNum,
            Integer pageSize
    );

    FalldownDevice selectById(Long id);

    int insert(FalldownDevice falldownDevice);

    int update(FalldownDevice falldownDevice);

    int deleteByIds(List<Long> ids);

}
