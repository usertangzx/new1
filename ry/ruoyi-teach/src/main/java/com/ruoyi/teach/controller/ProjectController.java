package com.ruoyi.teach.controller;
//接受前端请求
import com.ruoyi.common.core.controller.TeachBaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.teach.domain.entity.TeachProjectInfo;
import com.ruoyi.teach.service.ITeachProjectInfoService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ProjectInfo")
public class ProjectController extends TeachBaseController {

    private final ITeachProjectInfoService projectInfoService;

    @GetMapping("/list")
    public AjaxResult list(
            @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "prjName", required = false) String prjName,
            @RequestParam(value = "prjCode", required = false) String prjCode,
            @RequestParam(value = "manager", required = false) String manager,
            @RequestParam(value = "prjStatus", required = false) String prjStatus
    ) {
        startPage(pageNum, pageSize);
        return getPagedData(
                projectInfoService.findByConditions(prjName, prjCode, manager, prjStatus),
                pageNum == null ? 1 : pageNum,
                pageSize == null ? 10 : pageSize
        );
    }

    @PostMapping("/add")
    public AjaxResult add(@RequestBody TeachProjectInfo projectInfo) {
        int rows = projectInfoService.insert(projectInfo);
        return AjaxResult.success(String.valueOf(rows));
    }

    @PostMapping("/update")
    public AjaxResult update(@RequestBody TeachProjectInfo projectInfo) {
        int rows = projectInfoService.update(projectInfo);
        return AjaxResult.success(String.valueOf(rows));
    }

    @PostMapping("/delete")
    public AjaxResult delete(@RequestBody String ids) {
        int rows = projectInfoService.deleteByIds(parseIds(ids));
        return AjaxResult.success(String.valueOf(rows), rows);
    }

    private List<Integer> parseIds(String ids) {
        String normalized = ids == null ? "" : ids.trim();
        normalized = normalized.replace("[", "").replace("]", "").replace("\"", "");
        String[] parts = normalized.split(",");
        List<Integer> result = new ArrayList<>();
        for (String part : parts) {
            String value = part.trim();
            if (!value.isEmpty()) {
                result.add(Integer.valueOf(value));
            }
        }
        return result;
    }

}
