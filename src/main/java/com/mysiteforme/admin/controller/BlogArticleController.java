package com.mysiteforme.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.mysiteforme.admin.annotation.SysLog;
import com.mysiteforme.admin.base.BaseController;
import com.mysiteforme.admin.entity.BlogArticle;
import com.mysiteforme.admin.entity.BlogChannel;
import com.mysiteforme.admin.entity.BlogTags;
import com.mysiteforme.admin.entity.VO.ZtreeVO;
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
 * 成绩管理  前端控制器
 * </p>
 *
 * @author wangl
 * @since 2018-01-19
 */
@Controller
@RequestMapping("/admin/blogArticle")
public class BlogArticleController extends BaseController {

    @GetMapping("list")
    @SysLog("跳转成绩管理列表")
    public String list() {
        return "/admin/blogArticle/list";
    }

    @RequiresPermissions("blog:article:list")
    @PostMapping("list")
    @ResponseBody
    public LayerData<BlogArticle> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                       ServletRequest request) {
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, "s_");
        LayerData<BlogArticle> layerData = new LayerData<>();
        EntityWrapper<BlogArticle> wrapper = new EntityWrapper<>();
        wrapper.eq("del_flag", false);
        String channelId = null;
        if (!map.isEmpty()) {
            String title = (String) map.get("title");
            if (StringUtils.isBlank(title)) {
                map.remove("title");
            }
            String category = (String) map.get("category");
            if (StringUtils.isBlank(category)) {
                map.remove("category");
            }
            String beginPublistTime = (String) map.get("beginPublistTime");
            String endPublistTime = (String) map.get("endPublistTime");
            if (StringUtils.isNotBlank(beginPublistTime)) {
                Date begin = DateUtil.parse(beginPublistTime);
                map.put("publist_time", begin);
            } else {
                map.remove("beginPublistTime");
            }
            if (StringUtils.isNotBlank(endPublistTime)) {
                Date end = DateUtil.parse(endPublistTime);
                map.put("publist_time", end);
            } else {
                map.remove("endPublistTime");
            }
            String content = (String) map.get("content");
            if (StringUtils.isBlank(content)) {
                map.remove("content");
            }
            channelId = (String) map.get("channelId");
            // 1. 判断是否是父级菜单
            if (StringUtils.isBlank(channelId)) {
                map.remove("channelId");
            }
        }
        String parentId = blogChannelService.selectPatentTreeId(channelId);
        if (parentId == null) {
            map.put("channel_patent_id", channelId);
            map.remove("channelId");
        } else {
            map.remove("channel_patent_id");
        }
        Page<BlogArticle> pageData;
        pageData = blogArticleService.selectDetailArticle(map, new Page<>(page, limit));
        layerData.setData(pageData.getRecords());
        layerData.setCount(pageData.getTotal());
        return layerData;
    }

    @GetMapping("add")
    public String add(@RequestParam(value = "channelId", required = false) Long channelId, Model model) {
        BlogChannel blogChannel = blogChannelService.selectById(channelId);
        if (blogChannel != null) {
            model.addAttribute("channel", blogChannel);
        }
        List<ZtreeVO> list = blogChannelService.selectZtreeData();
        model.addAttribute("ztreeData", JSONObject.toJSONString(list));
        List<BlogTags> blogTags = blogTagsService.listAll();
        model.addAttribute("taglist", blogTags);
        return "/admin/blogArticle/add";
    }

    @RequiresPermissions("blog:article:add")
    @PostMapping("add")
    @SysLog("保存新增成绩管理数据")
    @ResponseBody
    public RestResponse add(@RequestBody BlogArticle blogArticle) {
        if (StringUtils.isBlank(blogArticle.getTitle())) {
            return RestResponse.failure("学生姓名不能为空");
        }
        if (StringUtils.isBlank(blogArticle.getContent())) {
            return RestResponse.failure("内容不能为空");
        }
        if (blogArticle.getChannelId() == null) {
            return RestResponse.failure("年级ID不能为空");
        }
        String channelId = String.valueOf(blogArticle.getChannelId());
        String parentId = blogChannelService.selectPatentTreeId(channelId);
        blogArticle.setChannelPatentId(parentId);
        if (blogArticle.getChannelPatentId() == null) {
            return RestResponse.failure("年级不能添加");
        }
        Object o = blogArticleService.selectObj(Condition.create()
                .setSqlSelect("max(sort)")
                .eq("channel_id", blogArticle.getChannelId())
                .eq("del_flag", false));
        int sort = 0;
        if (o != null) {
            sort = (Integer) o + 1;
        }
        blogArticle.setSort(sort);
        blogArticleService.saveOrUpdateArticle(blogArticle);
        if (blogArticle.getBlogTags() != null && blogArticle.getBlogTags().size() > 0) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("articleId", blogArticle.getId());
            map.put("tags", blogArticle.getBlogTags());
            blogArticleService.saveArticleTags(map);
        }
        return RestResponse.success();
    }

    @GetMapping("edit")
    public String edit(Long id, Model model) {
        BlogArticle blogArticle = blogArticleService.selectOneDetailById(id);
        model.addAttribute("blogArticle", blogArticle);
        List<ZtreeVO> list = blogChannelService.selectZtreeData();
        model.addAttribute("ztreeData", JSONObject.toJSONString(list));
        List<BlogTags> blogTags = blogTagsService.listAll();
        model.addAttribute("taglist", blogTags);
        return "/admin/blogArticle/edit";
    }

    @RequiresPermissions("blog:article:edit")
    @PostMapping("edit")
    @ResponseBody
    @SysLog("保存编辑成绩管理数据")
    public RestResponse edit(@RequestBody BlogArticle blogArticle) {
        if (null == blogArticle.getId() || 0 == blogArticle.getId()) {
            return RestResponse.failure("ID不能为空");
        }
        if (StringUtils.isBlank(blogArticle.getTitle())) {
            return RestResponse.failure("姓名不能为空");
        }
        if (StringUtils.isBlank(blogArticle.getContent())) {
            return RestResponse.failure("内容不能为空");
        }
        blogArticleService.saveOrUpdateArticle(blogArticle);
        blogArticleService.removeArticleTags(blogArticle.getId());
        if (blogArticle.getBlogTags() != null && blogArticle.getBlogTags().size() > 0) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("articleId", blogArticle.getId());
            map.put("tags", blogArticle.getBlogTags());
            blogArticleService.saveArticleTags(map);
        }
        return RestResponse.success();
    }

    @RequiresPermissions("blog:article:delete")
    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除成绩管理数据")
    public RestResponse delete(@RequestParam(value = "id", required = false) Long id) {
        if (null == id || 0 == id) {
            return RestResponse.failure("ID不能为空");
        }
        BlogArticle blogArticle = blogArticleService.selectById(id);
        blogArticle.setDelFlag(true);
        blogArticleService.saveOrUpdateArticle(blogArticle);
        return RestResponse.success();
    }

    @GetMapping("createIndex")
    @ResponseBody
    public RestResponse createIndex() {
        blogArticleService.createArticlIndex();
        return RestResponse.success();
    }

}