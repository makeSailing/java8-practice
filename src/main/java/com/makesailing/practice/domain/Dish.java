package com.makesailing.practice.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * # 菜肴
 *
 *
 * @date 2018/7/6 16:25
 */
@Data
@AllArgsConstructor
public class Dish implements Serializable {

	private static final long serialVersionUID = 7115202165663444591L;
	/**
	 * 名称
	 */
	private final String name;
	/**
	 * 是否是素菜
	 */
	private final boolean vegetarian;
	/**
	 * 卡路里 热量
	 */
	private final int calories;
	/**
	 * 菜肴类型
	 */
	private final Type type;

	/**
	 * 菜肴类型
	 */
	public enum Type {
		/**
		 * 肉
		 */
		MEAT("肉"),
		/**
		 * 肉
		 */
		FISH("鱼"),
		/**
		 * 其他
		 */
		OTHER("其他");

		private String name;

		Type(String name) {
			this.name = name;
		}
	}
}


