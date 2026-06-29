package com.ruoyi.teach.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.teach.domain.entity.FalldownDevice;
import com.ruoyi.teach.mapper.FalldownDeviceMapper;
import com.ruoyi.teach.service.IFalldownDeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FalldownDeviceServiceImpl implements IFalldownDeviceService {

    private final FalldownDeviceMapper falldownDeviceMapper;

    @Override
    public List<FalldownDevice> findByConditions(String deviceCode, String model, String status, Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageSize == null) {
            pageNum = 1;
            pageSize = 10;
        }
        return falldownDeviceMapper.findByConditions(deviceCode, model, status, pageNum, pageSize);
    }

    @Override
    public FalldownDevice selectById(Long id) {
        return falldownDeviceMapper.selectById(id);
    }

    @Override
    public int insert(FalldownDevice falldownDevice) {
        falldownDevice.setCreateTime(DateUtils.getNowDate());
        return falldownDeviceMapper.insert(falldownDevice);
    }

    @Override
    public int update(FalldownDevice falldownDevice) {
        falldownDevice.setUpdateTime(DateUtils.getNowDate());
        return falldownDeviceMapper.update(falldownDevice);
    }

    @Override
    public int deleteByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }
        return falldownDeviceMapper.deleteByIds(ids);
    }
}
