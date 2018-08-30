package com.makesailing.practice.demo;

import com.makesailing.practice.ApplicationTest;
import com.makesailing.practice.domain.Dish;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

/**
 * # Stream 筛选操作
 *
 * @author <a href="mailto:jamie.li@wolaidai.com">jamie.li</a>
 * @date 2018/8/30 13:42
 */
public class Demo3Test extends ApplicationTest {

	/**
	 * 使用 filter() 筛选元素
	 * 从菜单中筛选中 素菜菜肴
	 * 输出 :
	 * 	 Dish(name=薯条, vegetarian=true, calories=530, type=OTHER)
		 Dish(name=米饭, vegetarian=true, calories=350, type=OTHER)
		 Dish(name=水果, vegetarian=true, calories=120, type=OTHER)
		 Dish(name=比萨饼, vegetarian=true, calories=550, type=OTHER)
	 */
	@Test
	public void filterTest() {
		List<Dish> vegetarianMenu = menuList.stream()
			.filter(Dish::isVegetarian) // 过滤条件 是否是 素菜
			.collect(Collectors.toList());

		vegetarianMenu.forEach(System.out::println);
	}

	/**
	 * 使用 distinct() 方法筛选中重复的元素
	 * 输出 :
	 * 		2
	        4
	        6
	        8
	 */
	@Test
	public void distinctTest() {
		List<Integer> numbers = Arrays.asList(1, 2, 4, 6, 5, 4, 2, 7, 8);

		List<Integer> handlerNums = numbers.stream()
			.filter(i -> i % 2 == 0) // 是否是 偶娄
			.distinct() // 去除重复元素
			.collect(Collectors.toList());

		handlerNums.forEach(System.out::println);

	}

	/**
	 * 使用limit(n)方法，返回一个不超过给定长度的流
	 * 提取卡路里超过300的前3个的菜肴
	 * 输出 :
		   猪肉 : 800
	       牛肉 : 700
		   鸡肉 : 400
	 */
	@Test
	public void limitTest(){
		List<Dish> thressDishs = menuList.stream()
			.filter(dish -> dish.getCalories() > 300)
			.limit(3) // 截取出前 3
			.collect(Collectors.toList());

		thressDishs.forEach(dish -> System.out.println(dish.getName() + " : " + dish.getCalories()));

	}
}


