package com.mysiteforme.admin.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mysiteforme.admin.annotation.SysLog;
import com.mysiteforme.admin.base.MySysUser;
import com.mysiteforme.admin.entity.Reward;
import com.mysiteforme.admin.service.RewardService;
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
import java.util.Map;

/**
 * <p>
 * 获奖内容
 * </p>
 *
 * @author 王云
 */
@Controller
@RequestMapping("/admin/reward")
public class RewardController {

    @Autowired
    private RewardService rewardService;

    @GetMapping("list")
    @SysLog("跳转获奖记录列表")
    public String list() {
        return "/admin/reward/list";
    }

    @RequiresPermissions("blog:reward:list")
    @PostMapping("list")
    @ResponseBody
    public LayerData<Reward> list(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "limit", defaultValue = "10") Integer limit, ServletRequest request) {
        Map map = WebUtils.getParametersStartingWith(request, "s_");
        LayerData<Reward> layerData = new LayerData<>();
        EntityWrapper<Reward> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", MySysUser.id());
        if (!map.isEmpty()) {
            String content = (String) map.get("awardContent");
            if (StringUtils.isNotBlank(content)) {
                wrapper.like("award_content", content);
            } else {
                map.remove("award_content");
            }
            String studentName = (String) map.get("studentName");
            if (StringUtils.isNotBlank(studentName)) {
                wrapper.like("student_name", studentName);
            } else {
                map.remove("student_name");
            }
            String isAdminReply = (String) map.get("rewardLevel");
            if (StringUtils.isNotBlank(isAdminReply)) {
                if (isAdminReply.equalsIgnoreCase("1")) {
                    wrapper.eq("reward_level", 1);
                } else if (isAdminReply.equalsIgnoreCase("2")) {
                    wrapper.eq("reward_level", 2);
                } else {
                    map.remove("reward_level");
                }
            } else {
                map.remove("reward_level");
            }

        }
        Page<Reward> pageData = rewardService.selectPage(new Page<>(page, limit), wrapper);
        layerData.setData(pageData.getRecords());
        layerData.setCount(pageData.getTotal());
        return layerData;
    }

    @GetMapping("add")
    public String add() {
        return "/admin/reward/add";
    }

    @RequiresPermissions("blog:reward:add")
    @PostMapping("add")
    @ResponseBody
    public RestResponse add(Reward reward) {
        if (StringUtils.isBlank(reward.getTeacherContent())) {
            return RestResponse.failure("评论内容不能为空");
        }
        reward.setUserId(MySysUser.id());
        rewardService.insert(reward);
        return RestResponse.success();
    }

    @GetMapping("edit")
    public String edit(Long id, Model model) {
        Reward reward = rewardService.selectById(id);
        model.addAttribute("reward", reward);
        return "/admin/reward/edit";
    }

    @RequiresPermissions("blog:reward:edit")
    @PostMapping("edit")
    @ResponseBody
    public RestResponse edit(Reward reward) {
        if (null == reward.getId() || 0 == reward.getId()) {
            return RestResponse.failure("ID不能为空");
        }
        if (StringUtils.isBlank(reward.getTeacherContent())) {
            return RestResponse.failure("评论内容不能为空");
        }
        rewardService.updateById(reward);
        return RestResponse.success();
    }

    @RequiresPermissions("blog:reward:delete")
    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除获奖数据")
    public RestResponse delete(@RequestParam(value = "id", required = false) Long id) {
        if (null == id || 0 == id) {
            return RestResponse.failure("ID不能为空");
        }
        Reward reward = rewardService.selectById(id);
        reward.setDelFlag(true);
        rewardService.deleteById(id);
        return RestResponse.success();
    }

    @RequiresPermissions("blog:reward:reply")
    @PostMapping("adminReplay")
    @ResponseBody
    @SysLog("教师评语")
    public RestResponse adminReplay(Reward blogComment) {
        if(null == blogComment.getId() || 0 == blogComment.getId()){
            return RestResponse.failure("ID不能为空");
        }
        if(StringUtils.isBlank(blogComment.getReplyContent())){
            return RestResponse.failure("回复内容不能为空");
        }
        String content = blogComment.getReplyContent().replace("\"", "\'");
        blogComment.setTeacherContent(content);
        rewardService.saveOrUpdateReward(blogComment);
        return RestResponse.success();
    }

}