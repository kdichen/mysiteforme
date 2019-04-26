package com.mysiteforme.admin.controller;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mysiteforme.admin.annotation.SysLog;
import com.mysiteforme.admin.base.BaseController;
import com.mysiteforme.admin.entity.BlogChannel;
import com.mysiteforme.admin.entity.Site;
import com.mysiteforme.admin.entity.VO.ZtreeVO;
import com.mysiteforme.admin.util.RestResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 班级管理  前端控制器
 * </p>
 *
 * @author wangl
 * @since 2018-01-17
 */
@Controller
@RequestMapping("/admin/blogChannel")
public class BlogChannelController extends BaseController {

    @GetMapping("list")
    @SysLog("跳转班级管理列表")
    public String list() {
        return "/admin/blogChannel/list";
    }

    @RequiresPermissions("blog:channel:list")
    @PostMapping("list")
    @ResponseBody
    public RestResponse list(HttpServletRequest request) {
        List<BlogChannel> blogChannels = blogChannelService.selectChannelList();
        return RestResponse.success().setData(blogChannels);
    }

    @GetMapping("add")
    public String add(@RequestParam(value = "parentId", required = false) Long parentId, Model model) {
        if (parentId != null && parentId != 0) {
            BlogChannel blogChannel = blogChannelService.selectById(parentId);
            model.addAttribute("parentChannel", blogChannel);
        }
        EntityWrapper<Site> wrapper = new EntityWrapper<>();
        wrapper.eq("del_flag", false);
        return "/admin/blogChannel/add";
    }

    @RequiresPermissions("blog:channel:add")
    @PostMapping("add")
    @SysLog("保存新增班级管理数据")
    @ResponseBody
    public RestResponse add(BlogChannel blogChannel) {
        if (StringUtils.isBlank(blogChannel.getName())) {
            return RestResponse.failure("班级不能为空");
        }
        if (blogChannelService.getCountByName(blogChannel.getName()) > 0) {
            return RestResponse.failure("班级已经存在");
        }
        if (blogChannel.getParentId() == null) {
            blogChannel.setLevel(1);
            Object o = blogChannelService.selectObj(Condition.create()
                    .setSqlSelect("max(sort)").isNull("parent_id")
                    .eq("del_flag", false));
            int sort = 0;
            if (o != null) {
                sort = (Integer) o + 10;
            }
            blogChannel.setSort(sort);
        } else {
            BlogChannel parentMenu = blogChannelService.selectById(blogChannel.getParentId());
            if (parentMenu == null) {
                return RestResponse.failure("年级不存在");
            }
            blogChannel.setParentIds(parentMenu.getParentIds());
            blogChannel.setLevel(parentMenu.getLevel() + 1);
            Object o = blogChannelService.selectObj(Condition.create()
                    .setSqlSelect("max(sort)")
                    .eq("parent_id", blogChannel.getParentId())
                    .eq("del_flag", false));
            int sort = 0;
            if (o != null) {
                sort = (Integer) o + 10;
            }
            blogChannel.setSort(sort);
        }
        blogChannelService.saveOrUpdateChannel(blogChannel);
        blogChannel.setParentIds(StringUtils.isBlank(blogChannel.getParentIds()) ? blogChannel.getId() + "," :
                blogChannel.getParentIds() + blogChannel.getId() + ",");
        blogChannelService.saveOrUpdateChannel(blogChannel);
        return RestResponse.success();
    }

    @GetMapping("edit")
    public String edit(Long id, Model model) {
        BlogChannel blogChannel = blogChannelService.selectById(id);
        model.addAttribute("blogChannel", blogChannel);

        EntityWrapper<Site> wrapper = new EntityWrapper<>();
        wrapper.eq("del_flag", false);
        return "/admin/blogChannel/edit";
    }

    @RequiresPermissions("blog:channel:edit")
    @PostMapping("edit")
    @ResponseBody
    @SysLog("保存编辑班级管理数据")
    public RestResponse edit(BlogChannel blogChannel) {
        if (null == blogChannel.getId() || 0 == blogChannel.getId()) {
            return RestResponse.failure("ID不能为空");
        }
        if (StringUtils.isBlank(blogChannel.getName())) {
            return RestResponse.failure("班级不能为空");
        }
        BlogChannel oldChannel = blogChannelService.selectById(blogChannel.getId());
        if (!oldChannel.getName().equals(oldChannel.getName())) {
            if (blogChannelService.getCountByName(oldChannel.getName()) > 0) {
                return RestResponse.failure("班级已存在");
            }
        }
        blogChannelService.saveOrUpdateChannel(blogChannel);
        return RestResponse.success();
    }

    @RequiresPermissions("blog:channel:delete")
    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除班级管理数据")
    public RestResponse delete(@RequestParam(value = "id", required = false) Long id) {
        if (null == id || 0 == id) {
            return RestResponse.failure("ID不能为空");
        }
        BlogChannel blogChannel = blogChannelService.selectById(id);
        blogChannel.setDelFlag(true);
        blogChannelService.saveOrUpdateChannel(blogChannel);
        //清除此栏目所对应的文章的关系
        blogArticleService.removeArticleChannel(id);
        return RestResponse.success();
    }

    /**
     * 栏目ztree树
     *
     * @return
     */
    @PostMapping("ztreeData")
    @ResponseBody
    public RestResponse getZtreeChannelData() {
        List<ZtreeVO> list = blogChannelService.selectZtreeData();
        return RestResponse.success().setData(list);
    }
}