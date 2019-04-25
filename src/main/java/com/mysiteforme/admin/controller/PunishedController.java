package com.mysiteforme.admin.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mysiteforme.admin.annotation.SysLog;
import com.mysiteforme.admin.base.BaseController;
import com.mysiteforme.admin.base.MySysUser;
import com.mysiteforme.admin.entity.Punished;
import com.mysiteforme.admin.util.LayerData;
import com.mysiteforme.admin.util.RestResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 受罚记录  前端控制器
 * </p>
 *
 * @author wangl
 * @since 2018-01-17
 */
@Controller
@RequestMapping("/admin/punished")
public class PunishedController extends BaseController {

    @GetMapping("list")
    @SysLog("跳转受罚记录列表")
    public String list() {
        return "/admin/punished/list";
    }

    @RequiresPermissions("blog:punished:list")
    @PostMapping("list")
    @ResponseBody
    public LayerData<Punished> list(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam
            (value = "limit", defaultValue = "10") Integer limit, ServletRequest request) {
        Long userId = MySysUser.id();
        EntityWrapper<Punished> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        LayerData<Punished> layerData = new LayerData<>();
        Page<Punished> pageData = punishedService.queryList(wrapper, new Page<>(page, limit));
        layerData.setData(pageData.getRecords());
        layerData.setCount(pageData.getTotal());
        return layerData;
    }

    /**
     * 添加受罚记录
     *
     * @return
     */
    @GetMapping("add")
    public String add() {
        return "/admin/punished/add";
    }

    /**
     * 保存新增受罚记录数据
     *
     * @param punished
     * @return
     */
    @RequiresPermissions("blog:punished:add")
    @PostMapping("add")
    @SysLog("保存新增受罚记录数据")
    @ResponseBody
    public RestResponse add(Punished punished) {
        punished.setCreateDate(new Date());
        punished.setUserId(MySysUser.id());
        punishedService.saveOrUpdatePunished(punished);
        return RestResponse.success();
    }

    /**
     * 跳转编辑受罚记录页面
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("edit")
    public String edit(Long id, Model model) {
        Punished punished = punishedService.selectById(id);
        model.addAttribute("punished", punished);
        return "/admin/punished/edit";
    }

    /**
     * 编辑受罚记录
     *
     * @param punished
     * @return
     */
    @RequiresPermissions("blog:punished:edit")
    @PostMapping("edit")
    @ResponseBody
    @SysLog("保存编辑受罚记录数据")
    public RestResponse edit(Punished punished) {
        punished.setUpdateDate(new Date());
        punishedService.saveOrUpdatePunished(punished);
        return RestResponse.success();
    }

    @RequiresPermissions("blog:punished:delete")
    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除受罚记录数据")
    public RestResponse delete(@RequestParam(value = "ids[]", required = false) List<Long> ids) {
        if (null == ids || 0 == ids.size()) {
            return RestResponse.failure("ID不能为空");
        }
        punishedService.deletePunished(ids);
        return RestResponse.success();
    }
}