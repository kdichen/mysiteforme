package com.mysiteforme.admin.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.mysiteforme.admin.base.DataEntity;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 评价记录
 * </p>
 *
 * @author wangl
 * @since 2018-01-17
 */
@Data
@TableName("evaluate")
public class Evaluate extends DataEntity<Evaluate> {

    private static final long serialVersionUID = 1L;

    /**
     * 名字
     */
    private String name;

    /**
     * 评价内容
     */
    private String evaluationContent;

    /**
     * 班级
     */
    private String clazz;

    /**
     * 评价时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date updateDate;

    /**
     * 排序
     */
    private Integer sort;

    @TableField(exist = false)
    private Integer tagsUseCount;

    private Long userId;


}
