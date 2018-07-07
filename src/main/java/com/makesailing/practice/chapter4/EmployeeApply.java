package com.makesailing.practice.chapter4;

import com.alibaba.fastjson.JSON;
import com.makesailing.practice.domain.Employee;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * #
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
	 */
	public static List<Employee> filterAge() {
		return employees.stream()
			.filter(employee -> employee.getAge() > 25)
			.collect(Collectors.toList());
	}

	/**
	 * 筛选重复元素
	 */
	public static List<Employee> distinct() {
		return employees.stream()
			.filter(employee -> employee.getAge() > 25)
			.distinct()
			.collect(Collectors.toList());
	}

	/**
	 * 截断流
	 */
	public static List<Employee> limit() {
		return employees.stream()
			.filter(employee -> employee.getAge() > 25)
			.limit(3)
			.collect(Collectors.toList());
	}

	/**
	 * 跳过元素
	 */
	public static List<Employee> skip() {
		return employees.stream()
			.filter(employee -> employee.getAge() > 25)
			.skip(2)
			.collect(Collectors.toList());
	}

	/**
	 * 映射流
	 */
	public static List<String> map() {
		return employees.stream()
			.map(Employee::getName)
			.collect(Collectors.toList());
	}

	/**
	 * 流的扁平化
	 */
	public static List<String> flatMap() {
		String[] arrays = {"Hello", "World"};
		return Arrays.stream(arrays)
			.map(str -> str.split("")) // 映射成为Stream<String[]>
			.flatMap(Arrays::stream) // 扁平化为Stream<String>
			.distinct()
			.collect(Collectors.toList());
	}

	// ------------------------------------------------------------------------------------

	/**
	 * 查找和匹配 anyMatch 流中是否有一个元素能匹配给定的谓词 返回  boolean
	 */
	public static void anyMatch() {
		if (employees.stream().anyMatch(employee -> employee.getAge() == 28)) {
			System.out.println("存在员工年龄在28的员工");
		}
	}

	/**
	 * 查找和匹配 allMatch 流中的元素是否都能匹配给定的元素
	 * @return
	 */
	public static boolean allMatch() {
		if (employees.stream().allMatch(employee -> employee.getAge() < 35)) {
			System.out.println("所有员工年龄都小于35岁");
			return true;
		}
		return false;
	}

	/**
	 * 查找和匹配 noneMatch 可以确保流中没有任何元素与给定的谓词匹配
	 * @return
	 */
	public static boolean noneMatch() {
		if (employees.stream().noneMatch(employee -> employee.getAge() > 40)) {
			System.out.println("所有员工没有大于40岁");
			return true;
		}
		return false;
	}

	// anyMatch allMatch noneMaych 都用到我们所谓的短路,这就是Java中熟悉的 && || 运算符在流中的短路版本

	/**
	 * findAny 方法将返回当前流中的任意元素
	 * @return
	 */
	public static Optional<Employee> findAny() {
		Optional<Employee> result = employees.stream()
			.filter(employee -> Objects.equals(employee.getProfession(), "计算机科学")).findAny();
		return result;
	}

	/**
	 * findFirst 查找 流中的第一个元素
	 */
	public static void findFirst(){
		List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
		Optional<Integer> first =
			someNumbers.stream()
				.map(x -> x * x)
				.filter(x -> x % 3 == 0)
				.findFirst();
	}

 	// 你可能会想，为什么会同时有 findFirst 和 findAny 呢？答案是并行。找到第一个元素
	//在并行上限制更多。如果你不关心返回的元素是哪个，请使用 findAny ，因为它在使用并行流
	//时限制较少

	// ---------------------------------------------------------

	// 归约

	List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

	/**
	 * 归约
	 * @return
	 */
	public void sum() {
		Integer result1 = numbers.stream().reduce(0, (a, b) -> a + b);
		System.out.println("结果1 " + result1);
		Integer result2 = numbers.stream().reduce(0, Integer::sum);
		System.out.println("结果2" + result2);
		Optional<Integer> min = numbers.stream().reduce(Integer::min);
	}

	//  数值流  ----------------------

	// 用流收集数据

	/**
	 * 计算 员工总数
	 * @return
	 */
	public static Long employeeSum(){
		long count = employees.stream().count();
		Long collect = employees.stream().collect(Collectors.counting());
		System.out.println(collect);

		return count;
	}

	/**
	 * 查找 员工中年龄最大的
	 */
	public static void max(){
		Optional<Employee> employee1 = employees.stream()
			.collect(Collectors.maxBy(Comparator.comparing(Employee::getAge)));
		System.out.println(employee1);
		Optional<Employee> employee2 = employees.stream().max(Comparator.comparing(Employee::getAge));
		System.out.println(employee2);
	}

	/**
	 *  员工年龄汇总
	 */
	public static void ageSum(){
		Integer ageSum1 = employees.stream().mapToInt(Employee::getAge).sum();
		System.out.println(ageSum1);
		Integer ageSum2 = employees.stream().collect(Collectors.summingInt(Employee::getAge));
		System.out.println(ageSum2);

	}

	/**
	 * 员工平均值
	 */
	public static void averaging(){
		Double collect = employees.stream().collect(Collectors.averagingInt(Employee::getAge));
		System.out.println(collect);
	}

	/**
	 * 员工 按专业进行分组
	 */
	public static void grouping(){
		Map<String, List<Employee>> map = employees.stream()
			.collect(Collectors.groupingBy(Employee::getProfession));
		System.out.println(JSON.toJSONString(map));
	}

	/**
	 *  员工 按专业进行分组 并统计专业人数
	 */
	public static void test1(){
		Map<String, Long> map = employees.stream()
			.collect(Collectors.groupingBy(Employee::getProfession, Collectors.counting()));
		System.out.println(JSON.toJSONString(map));
	}

	/**
	 * 按专业进行分组 并查找专业中年龄最大的那人
	 */
	public static void test2(){
		Map<String, Optional<Employee>> map = employees.stream().collect(
			Collectors.groupingBy(Employee::getProfession, Collectors.maxBy(Comparator.comparing(Employee::getAge))));
		System.out.println(map);
	}
	/**
	 * 将收集器结果转换成 另一种类型  Collectors.collectingAndThen
	 * 按专业进行分给 并查找专业中年龄最大的那人
	 *
	 */
	public static void test3(){
		Map<String, Employee> map = employees.stream().collect(
			Collectors.groupingBy(Employee::getProfession,Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(Employee::getAge)),Optional::get)));
		System.out.println(map);
	}

	public static void test4(){
		Map<String, Set<Integer>> map = employees.stream().collect(
			Collectors.groupingBy(Employee::getProfession, Collectors.mapping(Employee::getAge, Collectors.toSet())));
		System.out.println(map);
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
//		System.out.println(map());
		/**
		 * 流的扁平化
		 */
//		System.out.println(flatMap());
		/**
		 *  anyMatch 流中是否有一个元素能匹配给定的谓词 返回  boolean
		 */
//		anyMatch();
		/**
		 * 查找和匹配 allMatch 流中的元素是否都能匹配给定的元素
		 */
//		System.out.println(allMatch());
		/**
		 * 查找和匹配 noneMatch 可以确保流中没有任何元素与给定的谓词匹配
		 */
//		System.out.println(noneMatch());
		/**
		 * findAny 方法将返回当前流中的任意元素
		 */
//		System.out.println(findAny());

		/**
		 * 计算员工总数
		 */
//		System.out.println(employeeSum());

		/**
		 * 查找 员工中年龄最大的
		 */
//		max();
		/**
		 *  员工年龄汇总
		 */
//		ageSum();
		/**
		 * 员工平均值
		 */
//		averaging();
		/**
		 * 员工 按专业进行分组
		 */
//		grouping();
		/**
		 * 员工 按专业进行分组 并统计专业人数
		 */
//		test1();
		/**
		 * 按专业进行分给 并查找专业中年龄最大的那人
		 */
//		test2();
		/**
		 * 将收集器结果转换成 另一种类型  Collectors.collectingAndThen
		 * 按专业进行分给 并查找专业中年龄最大的那人
		 *
		 */
//		test3();
		test4();
	}

}


