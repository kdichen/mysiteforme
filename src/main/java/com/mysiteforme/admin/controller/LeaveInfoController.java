package com.mysiteforme.admin.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mysiteforme.admin.annotation.SysLog;
import com.mysiteforme.admin.base.MySysUser;
import com.mysiteforme.admin.entity.LeaveInfo;
import com.mysiteforme.admin.service.LeaveInfoService;
import com.mysiteforme.admin.util.LayerData;
import com.mysiteforme.admin.util.RestResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 请假记录
 * </p>
 *
 * @author wangl
 * @since 2018-01-24
 */
@Controller
@RequestMapping("/admin/leaveInfo")
public class LeaveInfoController {

    @Autowired
    private LeaveInfoService leaveInfoService;

    @GetMapping("list")
    @SysLog("跳转请假信息列表")
    public String list() {
        return "/admin/leaveInfo/list";
    }

    @RequiresPermissions("blog:leaveInfo:list")
    @PostMapping("list")
    @ResponseBody
    public LayerData<LeaveInfo> list(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "limit", defaultValue = "10") Integer limit, ServletRequest request) {
        Long userId = MySysUser.id();
        Map map = WebUtils.getParametersStartingWith(request, "s_");
        LayerData<LeaveInfo> layerData = new LayerData<>();
        EntityWrapper<LeaveInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("del_flag", false);
        wrapper.eq("user_id", userId);
        if (!map.isEmpty()) {
            String name = (String) map.get("studentName");
            if (StringUtils.isNotBlank(name)) {
                wrapper.like("student_name", name);
            } else {
                map.remove("student_name");
            }

            String status = (String) map.get("status");
            if (StringUtils.isNotBlank(status)) {
                wrapper.eq("status", status);
            } else {
                map.remove("status");
            }

        }
        Page<LeaveInfo> pageData = leaveInfoService.queryList(wrapper, new Page<>(page, limit));
        layerData.setData(pageData.getRecords());
        layerData.setCount(pageData.getTotal());
        return layerData;
    }

    /**
     * 添加请假信息
     *
     * @return
     */
    @GetMapping("add")
    public String add() {
        return "/admin/leaveInfo/add";
    }

    /**
     * 添加请假信息
     *
     * @param leaveInfo
     * @return
     */
    @RequiresPermissions("blog:leaveInfo:add")
    @PostMapping("add")
    @SysLog("保存新增请假信息数据")
    @ResponseBody
    public RestResponse add(LeaveInfo leaveInfo) {
        Long userId = MySysUser.id();
        leaveInfo.setUserId(userId);
        leaveInfoService.saveLeaveInfor(leaveInfo);
        return RestResponse.success();
    }

    /**
     * 编辑请假信息
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("edit")
    public String edit(Long id, Model model) {
        LeaveInfo leaveInfo = leaveInfoService.selectById(id);
        model.addAttribute("leaveInfo", leaveInfo);
        return "/admin/leaveInfo/edit";
    }

    @RequiresPermissions("blog:leaveInfo:edit")
    @PostMapping("edit")
    @ResponseBody
    @SysLog("保存编辑请假信息数据")
    public RestResponse edit(LeaveInfo leaveInfo) {
        if (null == leaveInfo.getId() || 0 == leaveInfo.getId()) {
            return RestResponse.failure("ID不能为空");
        }
        leaveInfoService.updateLeaveInfor(leaveInfo);
        return RestResponse.success();
    }

    @RequiresPermissions("blog:leaveInfo:delete")
    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除请假信息数据")
    public RestResponse delete(@RequestParam(value = "ids[]", required = false) List<Long> ids) {
        if (null == ids || 0 == ids.size()) {
            return RestResponse.failure("ID不能为空");
        }
        leaveInfoService.deleteLeaveInfo(ids);
        return RestResponse.success();
    }

    /**
     * 暂停选中的请假信息
     *
     * @param ids
     * @return
     */
    @PostMapping("paushStatusById")
    @ResponseBody
    public RestResponse paushStatusById(@RequestParam(value = "ids[]", required = false) List<Long> ids) {
        if (null == ids || 0 == ids.size()) {
            return RestResponse.failure("ID不能为空");
        }
        leaveInfoService.paushStatusById(ids);
        return RestResponse.success();
    }

    /**
     * 恢复选中的请假信息运行
     *
     * @param ids 任务ID List
     * @return
     */
    @PostMapping("resumeStatusById")
    @ResponseBody
    public RestResponse resumeStatusById(@RequestParam(value = "ids[]", required = false) List<Long> ids) {
        if (null == ids || 0 == ids.size()) {
            return RestResponse.failure("ID不能为空");
        }
        leaveInfoService.resumeStatusById(ids);
        return RestResponse.success();
    }


}