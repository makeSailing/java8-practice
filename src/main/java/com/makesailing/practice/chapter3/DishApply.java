package com.makesailing.practice.chapter3;

import com.makesailing.practice.domain.Dish;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * #
 *
 *
 * @date 2018/7/6 16:28
 */
public class DishApply {

	private static List<Dish> menus = Arrays.asList(
		new Dish("pork", false, 800, Dish.Type.MEAT),
		new Dish("beef", false, 700, Dish.Type.MEAT),
		new Dish("chicken", false, 400, Dish.Type.MEAT),
		new Dish("french fries", true, 530, Dish.Type.OTHER),
		new Dish("rice", true, 350, Dish.Type.OTHER),
		new Dish("season fruit", true, 120, Dish.Type.OTHER),
		new Dish("pizza", true, 550, Dish.Type.OTHER),
		new Dish("prawns", false, 300, Dish.Type.FISH),
		new Dish("salmon", false, 450, Dish.Type.FISH) );

	/**
	 * 返回低热量的菜肴名称的 400，并按照卡路里排序
	 */
	public static List<String> lowCaloricDishesName() {
		long start = System.currentTimeMillis();
		List<String> names = menus.stream().filter(dish -> dish.getCalories() < 400)
			.sorted(Comparator.comparing(Dish::getCalories)).map(Dish::getName).collect(
				Collectors.toList());
		long end = System.currentTimeMillis();
		System.out.println("时间差 --->" + (end - start));


		long start2 = System.currentTimeMillis();
		/**
		 * 并行流并一定快 存在 折装箱情况
		 */
		List<String> names2 = menus.parallelStream().filter(dish -> dish.getCalories() < 400)
			.sorted(Comparator.comparing(Dish::getCalories)).map(Dish::getName).collect(
				Collectors.toList());
		long end2 = System.currentTimeMillis();
		System.out.println("时间差 --->" + (end2 - start2));
		return names;
	}

	public static void main(String[] args) {
		List<String> list = lowCaloricDishesName();
		System.out.println(list);
	}

}


