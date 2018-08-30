package com.makesailing.practice.demo;

import com.makesailing.practice.ApplicationTest;
import com.makesailing.practice.domain.Dish;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.Test;

/**
 * # stream 映射、匹配、查找
 *
 * @author <a href="mailto:jamie.li@wolaidai.com">jamie.li</a>
 * @date 2018/8/30 14:03
 */
public class Demo4Test extends ApplicationTest {

	/**
	 * 映射 : 使用 faltMap 将流进行扁平化
	 * 输出 : H e l o W r d
	 */
	@Test
	public void faltMapTest() {
		List<String> wordList  = Arrays.asList("Hello", "World");

		List<String> uniqueCharacters = wordList.stream()
			.map(s -> s.split("")) // 将单词转换由其字母组合成的数组
			.flatMap(Arrays::stream) // 将各个生成流扁平化成为一个流
			.distinct()
			.collect(Collectors.toList());

		uniqueCharacters.forEach(s -> System.out.print(s + " "));

	}

	/**
	 * anyMatch() 检查至少匹配一个元素
	 * 输出如下:
	 *  		菜单中至少存在一个素菜
	 */
	@Test
	public void anyMatchTest() {
		if (menuList.stream().anyMatch(Dish::isVegetarian)) {
			System.out.println("菜单中至少存在一个素菜");
		}
	}

	/**
	 * allMatch() 检查至少匹配一个元素
	 * 输出 :
	 * 		菜单中所有菜的热量都低于1000卡路里
	 */
	@Test
	public void allMatchTest() {

		if (menuList.stream().allMatch(dish -> dish.getCalories() < 1000)) {
			System.out.println("菜单中所有菜的热量都低于1000卡路里");
		}
		// 或者使用 noneMatch
		if (menuList.stream().noneMatch(dish -> dish.getCalories() >= 1000)) {
			System.out.println("菜单中所有菜的热量都低于1000卡路里");
		}
	}

	/**
	 * findAny() : 查找元素,不关心返回的元素是哪个
	 * 输出 :
	 * 		Dish(name=薯条, vegetarian=true, calories=530, type=OTHER)
	 */
	@Test
	public void findAnyTest() {
		Optional<Dish> dish = menuList.stream()
			.filter(Dish::isVegetarian)
			.findAny(); // 取出任意一个元素

		dish.ifPresent(System.out::println);

	}

	/**
	 * findFirst() : 查找元素,返回元素列表中的第一个
	 * 输出 :
	 * 		Dish(name=薯条, vegetarian=true, calories=530, type=OTHER)
	 */
	@Test
	public void findFirstTest() {
		Optional<Dish> dish = menuList.stream()
			.filter(Dish::isVegetarian)
			.findFirst(); // 返回第一个元素

		dish.ifPresent(System.out::println);
	}
}


