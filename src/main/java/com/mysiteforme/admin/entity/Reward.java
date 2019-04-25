package com.mysiteforme.admin.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.mysiteforme.admin.base.DataEntity;
import lombok.Data;

/**
 * <p>
 * 获奖内容
 * </p>
 *
 * @author wangl
 * @since 2018-01-18
 */
@Data
@TableName("reward")
public class Reward extends DataEntity<Reward> {

    private static final long serialVersionUID = 1L;

    private Long userId;

    /**
     * 获奖学生
     */
    private String studentName;

    /**
     * 奖励内容
     */
    @TableField("award_content")
    private String awardContent;

    /**
     * 获奖地点
     */
    private String address;

    /**
     * 获奖级别
     */
    private Integer rewardLevel;

    /**
     * 教师评语
     */
    @TableField("teacher_content")
    private String teacherContent;

    /**
     * 评论ID
     */
    private String articleId;

    /**
     * 修改回复内容
     */
    private String replyContent;

}
