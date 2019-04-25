package com.mysiteforme.admin.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.mysiteforme.admin.base.DataEntity;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 家访记录
 * </p>
 *
 * @author 王云
 */
@Data
@TableName("visit")
public class Visit extends DataEntity<Visit> {

    private static final long serialVersionUID = 1L;

    private Long userId;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 家访内容
     */
    private String content;

    /**
     * 发布时间
     */
    @TableField("publist_time")
    private Date publistTime;

}
