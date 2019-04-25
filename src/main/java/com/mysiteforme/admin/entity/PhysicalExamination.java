package com.mysiteforme.admin.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.mysiteforme.admin.base.DataEntity;
import lombok.Data;

/**
 * 体检
 *
 * @Author: 王云
 * @Date: 2019/3/3 16:45
 */
@TableName("physical")
@Data
public class PhysicalExamination extends DataEntity<PhysicalExamination> {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 学生名称
     */
    private String studentName;
    /**
     * 标签名
     */
    private String physicalExaminationResult;
    /**
     * 复查
     */
    private String review;
}
