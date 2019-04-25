package com.mysiteforme.admin.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mysiteforme.admin.annotation.SysLog;
import com.mysiteforme.admin.base.BaseController;
import com.mysiteforme.admin.base.MySysUser;
import com.mysiteforme.admin.entity.Evaluate;
import com.mysiteforme.admin.util.LayerData;
import com.mysiteforme.admin.util.RestResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * @author : ChenQian
 * @date : 2019/4/25 14:36
 */
@Controller
@RequestMapping("/admin/evaluate")
public class EvaluateController extends BaseController {


    @GetMapping("list")
    @SysLog("跳转评价记录列表")
    public String list() {
        return "/admin/evaluate/list";
    }

    @RequiresPermissions("blog:evaluate:list")
    @PostMapping("list")
    @ResponseBody
    public LayerData<Evaluate> list(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam
            (value = "limit", defaultValue = "10") Integer limit, ServletRequest request) {

        Map map = WebUtils.getParametersStartingWith(request, "s_");
        LayerData<Evaluate> layerData = new LayerData<>();
        EntityWrapper<Evaluate> wrapper = new EntityWrapper<>();
        // 拼接参数
        wrapper.eq("user_id", MySysUser.id());
        if (!map.isEmpty()) {
            String name = (String) map.get("name");
            if (StringUtils.isNotBlank(name)) {
                wrapper.like("name", name);
            } else {
                map.remove("name");
            }
        }
        // 调用service方法，查询数据库
        Page<Evaluate> pageData = evaluateService.queryList(wrapper, new Page<>(page, limit));
        // 把查询结果设置进layerData
        layerData.setData(pageData.getRecords());
        layerData.setCount(pageData.getTotal());
        // 返回封装好的查询数据给前端
        return layerData;
    }

    /**
     * 打开添加页面
     *
     * @return
     */
    @GetMapping("add")
    public String add() {
        return "/admin/evaluate/add";
    }

    /**
     * @param blogTags
     * @return
     */
    @RequiresPermissions("blog:evaluate:add")
    @PostMapping("add")
    @SysLog("保存新增评价记录数据")
    @ResponseBody
    public RestResponse add(Evaluate blogTags) {
        // 设置创建时间
        blogTags.setCreateDate(new Date());
        // 设置用户ID
        blogTags.setUserId(MySysUser.id());
        // 新增的sql
        evaluateService.insert(blogTags);

        return RestResponse.success();
    }

    @GetMapping("edit")
    public String edit(Long id, Model model) {
        Evaluate evaluate = evaluateService.selectById(id);
        model.addAttribute("evaluate", evaluate);
        return "/admin/evaluate/edit";
    }

    @RequiresPermissions("blog:evaluate:edit")
    @PostMapping("edit")
    @ResponseBody
    @SysLog("保存编辑评价记录数据")
    public RestResponse edit(Evaluate blogTags) {
        if (null == blogTags.getId() || 0 == blogTags.getId()) {
            return RestResponse.failure("ID不能为空");
        }
        blogTags.setUpdateDate(new Date());
        evaluateService.updateById(blogTags);
        return RestResponse.success();
    }

    @RequiresPermissions("blog:evaluate:delete")
    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除评价记录数据")
    public RestResponse delete(@RequestParam(value = "id", required = false) Long id) {
        if (null == id || 0 == id) {
            return RestResponse.failure("ID不能为空");
        }
        evaluateService.deleteThisEvaluate(id);
        return RestResponse.success();
    }
}
