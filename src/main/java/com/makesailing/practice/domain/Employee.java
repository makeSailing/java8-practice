package com.makesailing.practice.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * #
 *
 *
 * @date 2018/7/6 17:12
 */
@Data
@AllArgsConstructor
public class Employee implements Serializable {

	private static final long serialVersionUID = -8078550089934349371L;

	private Long id;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 年龄
	 */
	private Integer age;

	/**
	 * 部门
	 */
	private String department;

	/**
	 * 专业
	 */
	private String profession;

	/**
	 * 地址
	 */
	private String Address;
}


