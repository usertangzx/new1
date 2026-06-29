package com.ruoyi.teach.controller;

import com.ruoyi.common.core.controller.TeachBaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.teach.domain.entity.FalldownDevice;
import com.ruoyi.teach.service.IFalldownDeviceService;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/device/falldowndevice")
public class FalldownDeviceController extends TeachBaseController {

    private final IFalldownDeviceService falldownDeviceService;

    @GetMapping("/list")
    public AjaxResult list(
            @RequestParam(value = "DeviceCode", required = false) String deviceCode,
            @RequestParam(value = "Model", required = false) String model,
            @RequestParam(value = "Status", required = false) String status,
            @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        startPage(pageNum, pageSize);
        return getPagedData(
                falldownDeviceService.findByConditions(deviceCode, model, status, pageNum, pageSize),
                pageNum,
                pageSize
        );
    }

    @GetMapping("/detail")
    public AjaxResult detail(@RequestParam("Id") Long id) {
        return success(falldownDeviceService.selectById(id));
    }

    @PostMapping("/add")
    public AjaxResult add(@RequestBody FalldownDevice falldownDevice) {
        return toAjax(falldownDeviceService.insert(falldownDevice));
    }

    @PostMapping("/update")
    public AjaxResult update(@RequestBody FalldownDevice falldownDevice) {
        return toAjax(falldownDeviceService.update(falldownDevice));
    }

    @PostMapping("/delete")
    public AjaxResult delete(@RequestBody String ids) {
        return toAjax(falldownDeviceService.deleteByIds(parseIds(ids)));
    }

    @PostMapping("/export")
    public void export(
            HttpServletResponse response,
            @RequestParam(value = "DeviceCode", required = false) String deviceCode,
            @RequestParam(value = "Model", required = false) String model,
            @RequestParam(value = "Status", required = false) String status
    ) {
        List<FalldownDevice> list = falldownDeviceService.findByConditions(deviceCode, model, status, 1, Integer.MAX_VALUE);
        ExcelUtil<FalldownDevice> util = new ExcelUtil<>(FalldownDevice.class);
        util.exportExcel(response, list, "跌倒报警器数据");
    }

    private List<Long> parseIds(String ids) {
        String normalized = ids == null ? "" : ids.trim();
        normalized = normalized.replace("[", "").replace("]", "").replace("\"", "");
        String[] parts = normalized.split(",");
        List<Long> result = new ArrayList<>();
        for (String part : parts) {
            String value = part.trim();
            if (!value.isEmpty()) {
                result.add(Long.valueOf(value));
            }
        }
        return result;
    }
}
