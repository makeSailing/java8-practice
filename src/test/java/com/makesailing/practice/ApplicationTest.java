package com.makesailing.practice;

import com.makesailing.practice.domain.Dish;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

	@Test
	public void contextLoads() {
	}

	public List<Dish> menuList ;

	@Before
	public void setUp() throws Exception {
		menuList = Arrays.asList(
			new Dish("猪肉", false, 800, Dish.Type.MEAT),
			new Dish("牛肉", false, 700, Dish.Type.MEAT),
			new Dish("鸡肉", false, 400, Dish.Type.MEAT),
			new Dish("薯条", true, 530, Dish.Type.OTHER),
			new Dish("米饭", true, 350, Dish.Type.OTHER),
			new Dish("水果", true, 120, Dish.Type.OTHER),
			new Dish("比萨饼", true, 550, Dish.Type.OTHER),
			new Dish("大虾", false, 300, Dish.Type.FISH),
			new Dish("龙利鱼", false, 450, Dish.Type.FISH) );

	}

}
