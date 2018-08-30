package com.makesailing.practice.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * # 学生
 *
 * @author <a href="mailto:jamie.li@wolaidai.com">jamie.li</a>
 * @date 2018/8/30 15:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {

	private static final long serialVersionUID = -6441699369599239256L;

	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 性别：1为男性；2为女性
	 */
	private  int sex;
	/**
	 * 就读城市
	 */
	private String city;
}


