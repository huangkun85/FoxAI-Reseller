package com.ruoyi.web.controller.system;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.ResellerLevel;
import com.ruoyi.system.service.IResellerLevelService;

@RestController
@RequestMapping("/system/resellerLevel")
public class ResellerLevelController extends BaseController
{
    @Autowired
    private IResellerLevelService resellerLevelService;

    @PreAuthorize("@ss.hasPermi('resellerLevel:list')")
    @GetMapping("/list")
    public TableDataInfo list(ResellerLevel resellerLevel)
    {
        startPage();
        List<ResellerLevel> list = resellerLevelService.selectResellerLevelList(resellerLevel);
        return getDataTable(list);
    }

    @Log(title = "等级管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('resellerLevel:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, ResellerLevel resellerLevel)
    {
        List<ResellerLevel> list = resellerLevelService.selectResellerLevelList(resellerLevel);
        ExcelUtil<ResellerLevel> util = new ExcelUtil<ResellerLevel>(ResellerLevel.class);
        util.exportExcel(response, list, "等级数据");
    }

    @PreAuthorize("@ss.hasPermi('resellerLevel:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id)
    {
        return success(resellerLevelService.selectResellerLevelById(id));
    }

    @PreAuthorize("@ss.hasPermi('resellerLevel:add')")
    @Log(title = "等级管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ResellerLevel resellerLevel)
    {
        if (!resellerLevelService.checkLevelCodeUnique(resellerLevel))
        {
            return error("新增等级'" + resellerLevel.getLevelName() + "'失败，等级代码已存在");
        }
        return toAjax(resellerLevelService.insertResellerLevel(resellerLevel));
    }

    @PreAuthorize("@ss.hasPermi('resellerLevel:edit')")
    @Log(title = "等级管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ResellerLevel resellerLevel)
    {
        if (!resellerLevelService.checkLevelCodeUnique(resellerLevel))
        {
            return error("修改等级'" + resellerLevel.getLevelName() + "'失败，等级代码已存在");
        }
        return toAjax(resellerLevelService.updateResellerLevel(resellerLevel));
    }

    @PreAuthorize("@ss.hasPermi('resellerLevel:remove')")
    @Log(title = "等级管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(resellerLevelService.deleteResellerLevelByIds(ids));
    }
}
