package com.makesailing.practice.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * #
 *
 *
 * @date 2018/7/5 17:04
 */
@Data
public class Apple implements Serializable {

	private static final long serialVersionUID = -497120379815927237L;

	/**
	 * 颜色
	 */
	private String color;

	/**
	 * 重量
	 */
	private Integer weight;
}


