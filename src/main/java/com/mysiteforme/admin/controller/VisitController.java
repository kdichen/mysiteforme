package com.mysiteforme.admin.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mysiteforme.admin.annotation.SysLog;
import com.mysiteforme.admin.base.BaseController;
import com.mysiteforme.admin.base.MySysUser;
import com.mysiteforme.admin.entity.Evaluate;
import com.mysiteforme.admin.entity.Visit;
import com.mysiteforme.admin.util.LayerData;
import com.mysiteforme.admin.util.RestResponse;
import com.xiaoleilu.hutool.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 家访内容
 * </p>
 *
 * @author 王云
 */
@Controller
@RequestMapping("/admin/visit")
public class VisitController extends BaseController {

    @GetMapping("list")
    @SysLog("跳转家访内容列表")
    public String list() {
        return "/admin/visit/list";
    }


    @RequiresPermissions("blog:visit:list")
    @PostMapping("list")
    @ResponseBody
    public LayerData<Visit> list(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value
            = "limit", defaultValue = "10") Integer limit, ServletRequest request) {
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, "s_");
        LayerData<Visit> layerData = new LayerData<>();
        EntityWrapper<Visit> wrapper = new EntityWrapper<>();
        //        wrapper.eq("del_flag", false);
        wrapper.eq("user_id", MySysUser.id());
        if (!map.isEmpty()) {
            String name = (String) map.get("studentName");
            if (StringUtils.isNotBlank(name)) {
                wrapper.like("student_name", name);
            } else {
                map.remove("student_name");
            }
            String beginPublistTime = (String) map.get("beginPublistTime");
            String endPublistTime = (String) map.get("endPublistTime");
            if (StringUtils.isNotBlank(beginPublistTime)) {
                Date begin = DateUtil.parse(beginPublistTime);
                wrapper.ge("publist_time", begin);

            } else {
                map.remove("publist_time");
            }
            if (StringUtils.isNotBlank(endPublistTime)) {
                Date end = DateUtil.parse(endPublistTime);
                wrapper.lt("publist_time", end);
            } else {
                map.remove("publist_time");
            }
        }
        Page<Visit> pageData = visitService.selectDetailVisit(wrapper, new Page<>(page, limit));
        layerData.setData(pageData.getRecords());
        layerData.setCount(pageData.getTotal());
        return layerData;
    }

    @GetMapping("add")
    public String add(@RequestParam(value = "channelId", required = false) Long channelId, Model model) {
        return "/admin/visit/add";
    }

    @RequiresPermissions("blog:visit:add")
    @PostMapping("add")
    @SysLog("保存新增家访内容数据")
    @ResponseBody
    public RestResponse add(@RequestBody Visit visit) {
        if (StringUtils.isBlank(visit.getStudentName())) {
            return RestResponse.failure("姓名不能为空");
        }
        if (StringUtils.isBlank(visit.getContent())) {
            return RestResponse.failure("内容不能为空");
        }
        visit.setUserId(MySysUser.id());
        visitService.saveOrUpdateVisit(visit);
        return RestResponse.success();
    }


    @GetMapping("edit")
    public String edit(Long id, Model model) {
        Visit visit = visitService.selectOneDetailById(id);
        model.addAttribute("visit", visit);
        List<Evaluate> visits = evaluateService.listAll();
        model.addAttribute("visits", visits);
        return "/admin/visit/edit";
    }

    @RequiresPermissions("blog:visit:edit")
    @PostMapping("edit")
    @ResponseBody
    @SysLog("保存编辑家访内容数据")
    public RestResponse edit(@RequestBody Visit visit) {
        if (null == visit.getId() || 0 == visit.getId()) {
            return RestResponse.failure("ID不能为空");
        }
        if (StringUtils.isBlank(visit.getStudentName())) {
            return RestResponse.failure("学生姓名不能为空");
        }
        if (StringUtils.isBlank(visit.getContent())) {
            return RestResponse.failure("内容不能为空");
        }
        visitService.saveOrUpdateVisit(visit);
        return RestResponse.success();
    }

    @RequiresPermissions("blog:visit:delete")
    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除家访内容数据")
    public RestResponse delete(@RequestParam(value = "id", required = false) Long id) {
        if (null == id || 0 == id) {
            return RestResponse.failure("ID不能为空");
        }
        Visit visit = visitService.selectById(id);
        visit.setDelFlag(true);
        visitService.deleteById(visit);
        return RestResponse.success();
    }


}