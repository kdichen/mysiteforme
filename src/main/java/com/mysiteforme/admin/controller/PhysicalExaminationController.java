package com.mysiteforme.admin.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mysiteforme.admin.annotation.SysLog;
import com.mysiteforme.admin.base.BaseController;
import com.mysiteforme.admin.base.MySysUser;
import com.mysiteforme.admin.entity.PhysicalExamination;
import com.mysiteforme.admin.util.LayerData;
import com.mysiteforme.admin.util.RestResponse;
import com.xiaoleilu.hutool.log.Log;
import com.xiaoleilu.hutool.log.LogFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.util.Map;

/**
 * 体检记录
 *
 * @author 王云
 */
@Controller
@RequestMapping("/admin/physicalExamination")
public class PhysicalExaminationController extends BaseController {
    private static final Log log = LogFactory.get();

    @PostMapping("deleteById")
    @ResponseBody
    public RestResponse deleteById(@RequestParam(value = "id", required = false) Long id) {
        if (id == null || id == 0) {
            return RestResponse.failure("ID错误");
        }
        physicalExaminationService.deletePhysicalExamination(id);
        return RestResponse.success();
    }

    @GetMapping("list")
    @SysLog("跳转体检记录页面")
    public String list() {
        return "admin/physicalExamination/list";
    }

    @RequiresPermissions("blog:physicalExamination:list")
    @PostMapping("list")
    @ResponseBody
    public LayerData<PhysicalExamination> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                               @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                               ServletRequest request) {
        Map map = WebUtils.getParametersStartingWith(request, "s_");
        LayerData<PhysicalExamination> layerData = new LayerData<>();
        EntityWrapper<PhysicalExamination> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", MySysUser.id());
        if (!map.isEmpty()) {
            String type = (String) map.get("studentName");
            if (StringUtils.isNotBlank(type)) {
                wrapper.eq("student_name", type);
            }
        }
        Page<PhysicalExamination> dataPage = physicalExaminationService.queryList(new Page<>(page, limit), wrapper);
        layerData.setCount(dataPage.getTotal());
        layerData.setData(dataPage.getRecords());
        return layerData;
    }

    @GetMapping("add")
    public String add() {
        return "admin/physicalExamination/add";
    }

    @RequiresPermissions("blog:physicalExamination:add")
    @PostMapping("add")
    @SysLog("新增体检记录")
    @ResponseBody
    public RestResponse add(PhysicalExamination physicalExamination) {
        if (StringUtils.isBlank(physicalExamination.getStudentName())) {
            return RestResponse.failure("学生姓名不能为空");
        }
        if (StringUtils.isBlank(physicalExamination.getReview())) {
            return RestResponse.failure("体检结果不能为空");
        }
        physicalExamination.setUserId(MySysUser.id());
        physicalExaminationService.saveOrUpdateDict(physicalExamination);
        return RestResponse.success();
    }

    @GetMapping("edit")
    public String edit(Long id, Model model) {
        PhysicalExamination physicalExamination = physicalExaminationService.selectById(id);
        model.addAttribute("physicalExamination", physicalExamination);
        return "admin/physicalExamination/edit";
    }

    @RequiresPermissions("blog:physicalExamination:edit")
    @PostMapping("edit")
    @SysLog("编辑系统字典")
    @ResponseBody
    public RestResponse edit(PhysicalExamination physicalExamination) {
        if (physicalExamination.getId() == null) {
            return RestResponse.failure("ID不能为空");
        }
        physicalExaminationService.saveOrUpdateDict(physicalExamination);
        return RestResponse.success();
    }


}
