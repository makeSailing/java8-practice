package com.makesailing.practice.demo;

import com.makesailing.practice.ApplicationTest;
import com.makesailing.practice.domain.Dish;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;

/**
 * #
 *
 * @author <a href="mailto:jamie.li@wolaidai.com">jamie.li</a>
 * @date 2018/8/30 11:31
 */
public class Demo2Test extends ApplicationTest {

	/**
	 * 从菜单中计算: 低热量( < 450 )的菜肴名称，并按照卡路里排序
	 * 输出 : 水果
			 大虾
			 米饭
			 鸡肉
	 */
	@Test
	public void caloricDishesNameTest() {
		//提取低卡路里的菜肴名称
		List<String> caloricDishesName = menuList.stream()
			.filter(m -> m.getCalories() < 450) //卡路里小于450
			.sorted(Comparator.comparing(Dish::getCalories)) //根据卡路里从小到大排序
			.map(Dish::getName) //提取菜肴名称
			.collect(Collectors.toList());  //获取菜肴名称集合

		caloricDishesName.forEach(System.out::println);
	}

	/**
	 * 提取卡路里最多的三个菜肴名称
	 * 输出 : 猪肉
			 牛肉
			 比萨饼
	 */
	@Test
	public void threeHighCaloricDishNamesTest() {
		List<String> threeHighCaloricDishNames = menuList.stream()
			.sorted(Comparator.comparingInt(Dish::getCalories).reversed()) //根据卡路里逆序排序
			.limit(3)
			.map(Dish::getName)
			.collect(Collectors.toList());

		threeHighCaloricDishNames.forEach(System.out::println);
	}

	/**
	 * 流只能消费一次,再次消费会抛出一个异常，表示流已被消费掉了
	 */
	@Test
	public void streamCunsumeOneTest3() {
		Stream<Dish> stream = menuList.stream();
		stream.forEach(dish -> System.out.println(dish.getName() + ":" + dish.getCalories()));
		//再次遍历报错：java.lang.IllegalStateException: stream has already been operated upon or closed
		stream.forEach(dish -> System.out.println(dish.getName() + ":" + dish.getCalories()));

	}
}


