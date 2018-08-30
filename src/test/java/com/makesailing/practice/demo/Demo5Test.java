package com.makesailing.practice.demo;

import com.makesailing.practice.ApplicationTest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;

/**
 * # 归约: reduce方法来表达更复杂的操作，比如：使用reduce求和，求最大或最小元素
 *
 * @author <a href="mailto:jamie.li@wolaidai.com">jamie.li</a>
 * @date 2018/8/30 14:37
 */
public class Demo5Test extends ApplicationTest{

	public List<Integer> numbers;

	@Before
	public void setUp() throws Exception {
		numbers = Arrays.asList(1, 2, 3, 4);
	}

	/**
	 * 使用reduce归约元素
	 * 输出 : 10
	 */
	@Test
	public void reduceTest() {
		Integer sum = numbers.parallelStream() // 并行流
			.reduce(0, (a, b) -> a + b);

		System.out.println(sum);
	}

	/**
	 * 使用reduce归约元素,考虑到流中没有任何元素,可以不接受初始值但是会返回一个Optional对象
	 * 输出 : 10
	 */
	@Test
	public void reduceOptionalTest() {
		Optional<Integer> sum = numbers.parallelStream()
		    //.reduce((a, b) -> a + b) // 方式一
			.reduce(Integer::sum); // 方式二

		sum.ifPresent(System.out::println);

	}

	/**
	 * 使用 reduce(),求最大或最小值
	 * 输出 :
	 *	    最大值为: 4
	        最大值为: 1
	 */
	@Test
	public void reduceMaxOrMinTest() {
		Optional<Integer> maxNum = numbers.parallelStream().reduce(Integer::max);

		Optional<Integer> minNum = numbers.parallelStream().reduce(Integer::min);

		maxNum.ifPresent(m -> System.out.println("最大值为: " + maxNum.get()));

		minNum.ifPresent(m -> System.out.println("最大值为: " + minNum.get()));

	}

	/**
	 * 测试同时使用Map和Reduce
	 * 输出如下：
	 * 4
	 */
	@Test
	public void testMapReduce(){
		int count = numbers.parallelStream()
			.map(m -> 1) //把每个元素映射为1
			.reduce(0, Integer::sum); //求和

		System.out.println(count);
	}
}


