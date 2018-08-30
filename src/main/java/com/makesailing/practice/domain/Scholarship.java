package com.makesailing.practice.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * #
 *
 * @author <a href="mailto:jamie.li@wolaidai.com">jamie.li</a>
 * @date 2018/8/30 15:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Scholarship implements Serializable {

	private static final long serialVersionUID = 4407066593411176281L;

	/**
	 * 学生信息
	 */
	private  Student student;
	/**
	 * 获奖年份
	 */
	private  int year;
	/**
	 * 奖金金额
	 */
	private  int money;
}


