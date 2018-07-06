package com.makesailing.practice.chapter1;

import com.makesailing.practice.domain.Apple;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * #
 *
 *
 * @date 2018/7/5 17:07
 */
public class AppleApply {

	File[] hiddenFiles = new File(".").listFiles(new FileFilter() {
		@Override
		public boolean accept(File file) {
			return file.isHidden();
		}
	});

	File[] getHiddenFiles = new File(".").listFiles(File::isHidden);

	/**
	 *  筛选所有绿的苹果
	 * @param inventory
	 * @return
	 */
	public static List<Apple> filterGreenApples(List<Apple> inventory) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if (Objects.equals("green", apple.getColor())) {
				result.add(apple);
			}
		}
		return result;
	}
	/**
	 *  有人可能想要选出重的苹果，比如超过150克
	 * @param inventory
	 * @return
	 */
	public static List<Apple> filterHeavyApples(List<Apple> inventory) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if (apple.getWeight() > 150) {
				result.add(apple);
			}
		}
		inventory.sort(Comparator.comparing(Apple::getWeight));

		return result;
	}





}


