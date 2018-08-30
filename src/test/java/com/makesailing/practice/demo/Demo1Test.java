package com.makesailing.practice.demo;

import com.makesailing.practice.ApplicationTest;
import com.makesailing.practice.domain.Dish;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
* 
* @author <a href="mailto:jamie.li@wolaidai.com">jamie.li</a> 
* @since <pre>08/30/2018</pre> 
*/ 
public class Demo1Test extends ApplicationTest {


    @Before
    public void setUp() throws Exception {

    } 
    
    @After
    public void tearDown() throws Exception { 
    }

    /**
     * 从菜单中计算: 低热量的菜肴名称，并按照卡路里排序
     * 输出: 水果
             大虾
             米饭
             鸡肉
     */
    @Test
    public void java7Before() {
        // 1. 提取低卡路里(< 450)的菜肴名称
        List<Dish> caloriesDishs = new ArrayList<>();
        for (Dish dish : menuList) {
            if (dish.getCalories() < 450) {
                caloriesDishs.add(dish);
            }
        }

        // 2. 根据卡路里进行排序
        Collections.sort(caloriesDishs, new Comparator<Dish>() {
            @Override
            public int compare(Dish o1, Dish o2) {
                return Integer.compare(o1.getCalories(),o2.getCalories());
            }
        });

        // 3. 获取菜肴名称
        List<String> caloriesDishNames = new ArrayList<>();
        for (Dish dish : caloriesDishs) {
            caloriesDishNames.add(dish.getName());
        }

        for (String dishName : caloriesDishNames) {
            System.out.println(dishName);
        }
    }

    /**
     * 从菜单中计算: 低热量的菜肴名称，并按照卡路里排序
     * java 8
     * 输出: 水果
             大虾
             米饭
             鸡肉
     */
    @Test
    public void java8After() {
        List<String> list = menuList.stream()
            .filter(dish -> dish.getCalories() < 450) // 卡路里小于 450
            .sorted(Comparator.comparingInt(Dish::getCalories)) // 根据卡路里从小到大进行排序
            .map(Dish::getName) // 提取菜肴名称
            .collect( Collectors.toList()); // 获取菜肴名称集合

        list.forEach(System.out::println);
    }
 }
