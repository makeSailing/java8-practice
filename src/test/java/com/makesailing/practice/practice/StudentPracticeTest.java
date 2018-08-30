package com.makesailing.practice.practice;

import com.makesailing.practice.ApplicationTest;
import com.makesailing.practice.domain.Scholarship;
import com.makesailing.practice.domain.Student;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;

/**
 * # 学生实例综合练习
 *
 * @author <a href="mailto:jamie.li@wolaidai.com">jamie.li</a>
 * @date 2018/8/30 15:03
 */
public class StudentPracticeTest extends ApplicationTest {

	public List<Scholarship> scholarships ;

	@Before
	public void setUp() throws Exception {
		Student stu1 = new Student("张三",1,"广州");
		Student stu2 = new Student("李四",1,"广州");
		Student stu3 = new Student("王五",1,"杭州");
		Student stu4 = new Student("张伟",1,"重庆");
		Student stu5 = new Student("王芳",2,"重庆");
		Student stu6 = new Student("李娜",2,"成都");
		Student stu7 = new Student("刑静",2,"成都");
		Student stu8 = new Student("刘强",1,"成都");
		Student stu9 = new Student();
		stu9.setName("李白");
		stu9.setSex(1);

		scholarships = Arrays.asList(
			 new Scholarship(stu1, 2016, 4000)
			,new Scholarship(stu2, 2016, 4200)
			,new Scholarship(stu3, 2016, 3500)
			,new Scholarship(stu4, 2016, 3600)
			,new Scholarship(stu2, 2017, 4400)
			,new Scholarship(stu3, 2017, 3000)
			,new Scholarship(stu5, 2017, 3800)
			,new Scholarship(stu6, 2017, 2000)
			,new Scholarship(stu3, 2018, 6000)
			,new Scholarship(stu5, 2018, 7000)
			,new Scholarship(stu7, 2018, 5500)
			,new Scholarship(stu8, 2018, 1000)
			,new Scholarship(stu9, 2018, 1000)
		);
	}

	/**
	 * 找出2017年的奖学金获得情况，并按奖金金额排序
	 * 输出如下:
	 			Scholarship(student=Student(name=李娜, sex=2, city=成都), year=2017, money=2000)
	 			Scholarship(student=Student(name=王五, sex=1, city=杭州), year=2017, money=3000)
	 			Scholarship(student=Student(name=王芳, sex=2, city=重庆), year=2017, money=3800)
	 			Scholarship(student=Student(name=李四, sex=1, city=广州), year=2017, money=4400)
	 */
	@Test
	public void test1() {
		List<Scholarship> scholarshipList = scholarships.stream()
			.filter(scholarship -> scholarship.getYear() == 2017) // 筛选年份 2017
			.sorted(Comparator.comparingInt(Scholarship::getMoney)) // 排序 : 按奖学金进行从小到大排序
			.collect(Collectors.toList());

		scholarshipList.forEach(System.out::println);

	}
	/**
	 * 找出学生都来至于哪些城市
	 */
	@Test
	public void test2() {
		List<String> citys = scholarships.stream()
			.map(scholarship -> scholarship.getStudent().getCity()) //此级联操作,如是其中的一个学生没有填写所属城市,会出现城市为null的情况
			.distinct()
			.collect(Collectors.toList());

		citys.forEach(System.out::println);

		// 输出如下 : 广州
					//杭州
					//重庆
					//成都
					//null
		System.out.println("-------------------------------------------");

		// 解决 学生中城市为 null 的情况
		List<String> citys2 = scholarships.stream().map(scholarship -> {
			String city = scholarship.getStudent().getCity();
			return Objects.nonNull(city) ? city : "";
		}).distinct().collect(Collectors.toList());

		citys2.forEach(System.out::println);

		// 输出如下 : 广州
		           //杭州
		           //重庆
		           //成都
	}
	/**
	 * 找出来至成都的学生并按学生姓名排序
	 * 输出如下:
	 *         Student(name=刑静, sex=2, city=成都)
	           Student(name=刘强, sex=1, city=成都)
	           Student(name=李娜, sex=2, city=成都)
	 */
	@Test
	public void test3() {
		List<Student> students = scholarships.stream()
			.map(Scholarship::getStudent) // 收集学生信息
			.filter(student -> Objects.equals(student.getCity(), "成都")) // 过渡来致成都的学生
			.sorted(Comparator.comparing(Student::getName))// 按学生名称进行排序
			.distinct() // 去重
			.collect(Collectors.toList()); // 收集

		students.forEach(System.out::println);
	}
	/**
	 * 获取所有学生姓名构成的字符串
	 */
	@Test
	public void test4() {
		String name = scholarships.stream()
			.map(scholarship -> scholarship.getStudent().getName()) // 收集学生信息的学生姓名
			.distinct() // 去重
			.collect(Collectors.joining(",  ")); // 拼接字符窜
		System.out.println(name);
	}
	/**
	 * 找出奖金金额最小的奖金获得情况
	 * 输出如下 :
	 * Scholarship(student=Student(name=刘强, sex=1, city=成都), year=2018, money=1000)
	 */
	@Test
	public void test5() {
		Optional<Scholarship> scholarship = scholarships.stream()
			//.reduce((s1, s2) -> s1.getMoney() < s2.getMoney() ? s1 : s2); // 方式一
			.min(Comparator.comparingInt(Scholarship::getMoney)); // 方式二
		scholarship.ifPresent(System.out::println);
	}
	/**
	 * 找出奖金金额最多的奖金金额
	 */
	@Test
	public void test6() {

	}
	/**
	 * 计算2018年的奖金总额
	 */
	@Test
	public void test7() {

	}
	/**
	 * 按年份分组，并统计每年的奖金总额
	 */
	@Test
	public void test8() {

	}
	/**
	 *  按年份分组，并获取每年获得奖金最高的学生信息
	 */
	@Test
	public void test9() {

	}
	/**
	 * 按年份分组，再按照性别分组获取相应的学生信息
	 */
	@Test
	public void test10() {

	}
}


