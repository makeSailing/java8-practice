package com.makesailing.practice.chapter4;

import com.makesailing.practice.domain.Employee;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * #
 *
 *
 * @date 2018/7/6 17:17
 */
public class EmployeeApply {

	public static List<Employee> employees = Collections.unmodifiableList(Arrays.asList(
		new Employee(1L, "张三", 18, "技术开发中心", "计算机科学", "江西南昌"),
		new Employee(2L, "李四", 18, "技术开发中心", "计算机科学", "江西抚州"),
		new Employee(3L, "王五", 22, "人力行政中心", "土木工程", "北京"),
		new Employee(4L, "赵六", 23, "财务管理中心", "经济管理", "广东广州"),
		new Employee(5L, "田七", 28, "财务管理中心", "机械与自动化", "上海"),
		new Employee(5L, "田七", 28, "财务管理中心", "机械与自动化", "上海"),
		new Employee(6L, "孔明", 26, "战略合作事业部", "生化工程", "广东深圳"),
		new Employee(7L, "玄德", 40, "战略合作事业部", "生命科学", "江苏南京"),
		new Employee(8L, "云长", 33, "客户服务部", "社会学", "广东广州"),
		new Employee(9L, "翼德", 28, "智能金融事业部", "经济管理", "江苏南京"),
		new Employee(10L, "鲁肃", 36, "智能金融事业部", "经济管理", "上海")
	));

	/**
	 * 过滤掉 年龄小于25的员工
	 * @return
	 */
	public static List<Employee> filterAge() {
		List<Employee> list = employees.stream().filter(employee -> employee.getAge() > 25)
			.collect(Collectors.toList());
		return list;
	}

	/**
	 *  筛选重复元素
	 */
	public static List<Employee> distinct() {
		return employees.stream().filter(employee -> employee.getAge() > 25).distinct().collect(Collectors.toList());
	}
	/**
	 *  截断流
	 */
	public static List<Employee> limit() {
		return employees.stream().filter(employee -> employee.getAge() > 25).limit(3).collect(Collectors.toList());
	}
	/**
	 *  跳过元素
	 */
	public static List<Employee> skip() {
		return employees.stream().filter(employee -> employee.getAge() > 25).skip(2).collect(Collectors.toList());
	}
	/**
	 *  映射流
	 */
	public static List<String> map() {
		return employees.stream().map(Employee::getName).collect(Collectors.toList());
	}





	public static void main(String[] args) {
		/**
		 * 过滤掉 年龄小于25的员工
		 */
//		System.out.println(JSON.toJSONString(filterAge()));
		/**
		 *  筛选重复元素
		 */
//		System.out.println(JSON.toJSONString(distinct()));
		/**
		 *  截断流
		 */
//		System.out.println(JSON.toJSONString(limit()));
		/**
		 * 跳过元素
		 */
//		System.out.println(JSON.toJSONString(skip()));
		/**
		 *  映射流
		 */
		System.out.println(map());
	}

}


