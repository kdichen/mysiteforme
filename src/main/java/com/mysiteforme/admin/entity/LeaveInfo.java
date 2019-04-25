package com.mysiteforme.admin.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.mysiteforme.admin.base.DataEntity;
import lombok.Data;

/**
 * <p>
 * 请假信息
 * </p>
 *
 * @author wangl
 * @since 2018-01-24
 */
@Data
@TableName("leaveinfor")
public class LeaveInfo extends DataEntity<LeaveInfo> {

	private static final long serialVersionUID = 1L;

	/**
	 * 请假参数key
	 */
	@TableField(exist = false)
	public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";
	/**
	 * 学生名称
	 */
	private Long id;
	/**
	 * 学生名称
	 */
	private String studentName;
	/**
	 * 学号
	 */
	private String studentNumber;
	/**
	 * 班级
	 */
	private String clazz;
	/**
	 * 原因
	 */
	private String reason;
	/**
	 * 执行参数
	 */
	private String params;
	/**
	 * 任务状态 0:正常  1：暂停
	 */
	private Integer status;
	/**
	 * 用户ID
	 */
	private Long userId;
}
