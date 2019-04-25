package com.mysiteforme.admin.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 受罚记录
 * </p>
 *
 * @author wangl
 * @since 2018-01-18
 */
@TableName("punished")
@Data
public class Punished {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Long userId;

    /**
     * 学生名称
     */
    private String studentName;

    /**
     * 班级
     */
    private String clazz;

    /**
     * 学号
     */
    private Integer studentNumber;


    /**
     * 受罚原因
     */
    private String reason;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date updateDate;


}
