package com.makesailing.practice.demo;

import com.makesailing.practice.ApplicationTest;
import com.makesailing.practice.domain.Dish;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.Test;

/**
 * # 数值流 IntStream、 DoubleStream和LongStream
 *  分别用于将流中的元素特化为int、 long和double，从而避免了暗含的装箱成本
 *
 * @author <a href="mailto:jamie.li@wolaidai.com">jamie.li</a>
 * @date 2018/8/30 14:56
 */
public class Demo6Test extends ApplicationTest {

	/**
	 * 构建数值流
	 * 输出如下：
	 * 2
	 * 4
	 * 6
	 * 8
	 * 10
	 * 3, 4, 5
	 * 5, 12, 13
	 * 6, 8, 10
	 * 7, 24, 25
	 * 8, 15, 17
	 */
	@Test
	public void testIntStream(){
		//生成偶数
		IntStream evenNumbers = IntStream.rangeClosed(1, 10)
			.filter(n -> n % 2 == 0);

		evenNumbers.forEach(System.out::println);

		//生成勾股数
		Stream<int[]> pythagoreanTriples = IntStream.rangeClosed(1, 100).boxed() //外层生成0-100的自然数
			.flatMap(a -> IntStream.rangeClosed(a, 100) //内层再次生成a-100的自然数
				.filter(b -> Math.sqrt(a * a + b * b) % 1 == 0 ) //筛选符合勾股定理
				.mapToObj(b -> new int[]{a, b, (int)Math.sqrt(a * a + b * b)}) //构建勾股数
			);

		pythagoreanTriples.limit(5).forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));
	}

	/**
	 * 几种构建流的方式
	 * 输出如下：
	 * Java 8
	 * Lambdas
	 * In
	 * Action
	 */
	@Test
	public void testCreateStream(){
		//字符串流
		Stream<String> stringStream = Stream.of("Java 8 ", "Lambdas ", "In ", "Action");
		//空流
		Stream<String> emptyStream = Stream.empty();

		stringStream.forEach(System.out::println);
	}

	/**
	 * 通过迭代生成流
	 * 输出如下：
	 * 1
	 * 3
	 * 5
	 * 7
	 * 9
	 */
	@Test
	public void testIterate(){
		Stream.<Integer>iterate(1, n -> n + 2).limit(5)
			.forEach(System.out::println);
	}

	/**
	 * 通过generate方法生成流，与iterate方法类似，
	 * 但是generate方法不是依次对每个新生成的值应用函数，而是接受一个Supplier<T>类型的Lambda提供新的值
	 * 输出如下：
	 * 0.8761217090781523
	 * 0.2723966370610904
	 * 0.0018463146970718602
	 * 0.6037673908012483
	 * 0.5766660405584716
	 */
	@Test
	public void testGenerate(){
		Stream.<Double>generate(Math::random).limit(5)
			.forEach(System.out::println);
	}

	/**
	 * 根据菜肴类型进行去重
	 * 输出如下 :
	 * Dish(name=猪肉, vegetarian=false, calories=800, type=MEAT)
	 * Dish(name=大虾, vegetarian=false, calories=300, type=FISH)
	 * Dish(name=薯条, vegetarian=true, calories=530, type=OTHER)
	 */
	@Test
	public void test11() {
		List<Dish> list = menuList.stream()
				.distinct() // 此方法去重是计算对象的 hashCode 和 toString
				.collect(Collectors.toList());

		list.forEach(System.out::println);
		// 上面的方法没有起到去重的作用

		System.out.println("-----------------");

		List<Dish> afterStudens = menuList.stream().collect(Collectors
				.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Dish::getType))),
						ArrayList::new));

		afterStudens.forEach(System.out::println);
	}
}



