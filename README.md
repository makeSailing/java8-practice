## Java8实战

#### 1 . 简介

**什么是流（Stream）：**

流是Java API的新成员，它允许你以声明性方式处理数据集合（通过查询语句来表达，而不是临时编写一个实现）。就现在来说，你可以把它们看成遍历数据集的高级迭代器.

以下两段代码都是用来返回低热量的菜肴名称，并按照卡路里排序。一个是用Java 7写的，另一个是用Java 8的Stream API写的，大家可以观察一下它们有何区别.

**Java 7及之前的实现：**

```java
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

}
```

**Java8之后的实现 : **

```java
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
```

从上面代码可以看出,Java8的Stream API主要有以下两个优点:

- 代码是以声明性方式写,这样看起来更加简洁、易读. 这种方式下我们只需要表达我们想要达到什么目标(比如提取低卡路里的菜肴名称),而不是详细说明如何实现这样一个操作(利用if条件判断,循环遍历等控制流语句)
- 我们可以把几个基础操作链接起来,来表达复杂的数据处理流水线(在filter后面接上sorted 、map 和 collect操作等),同时保持代码清晰可读.

注 : 上面用到的Dish 类 : 

```java
package com.makesailing.practice.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * # 菜肴
 *
 *
 * @date 2018/7/6 16:25
 */
@Data
@AllArgsConstructor
public class Dish implements Serializable {

 private static final long serialVersionUID = 7115202165663444591L;
 /**
  * 名称
  */
 private final String name;
 /**
  * 是否是素菜
  */
 private final boolean vegetarian;
 /**
  * 卡路里 热量
  */
 private final int calories;
 /**
  * 菜肴类型
  */
 private final Type type;

 /**
  * 菜肴类型
  */
 public enum Type {
  /**
   * 肉
   */
  MEAT("肉"),
  /**
   * 肉
   */
  FISH("鱼"),
  /**
   * 其他
   */
  OTHER("其他");

  private String name;

  Type(String name) {
   this.name = name;
  }
 }
}
```

#### 2 . 用法总结 :

##### 2.1 第一个完整的简单实例 : 

```java
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
```

##### 2.2 流只能被遍历一次：

和迭代器类似，流只能遍历一次。遍历完之后，我们就可以说这个流已经被消费掉了。你可以从原始数据源那里再获得一个新的流来重新遍历一遍，就像迭代器一样（这里假设它是集合之类的可重复的源，如果是I/O通道就不行了）。例如，以下代码会抛出一个异常，表示流已被消费掉了。

```java
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
```

##### 2.3 筛选：

Stream API支持使用filter方法筛选。该操作会接受一个谓词（一个返回boolean的函数）作为参数，并返回一个包括所有符合谓词的元素的流。

```java
/**
 * 使用 filter() 筛选元素
 * 从菜单中筛选中 素菜菜肴
 * 输出 :
 *    Dish(name=薯条, vegetarian=true, calories=530, type=OTHER)
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
 *    2
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
```

##### 2.4 映射、匹配、查找：

流支持map方法，它会接受一个函数作为参数。这个函数会被应用到每个元素上，并将其映射成一个新的元素（使用映射一词，是因为它和转换类似，但其中的细微差别在于它是“创建一个新版本”而不是去“修改”）。

另一个常见的数据处理操作是查看数据集中的某些元素是否匹配一个给定的属性。 Stream API通过allMatch、 anyMatch、 noneMatch、 findFirst和findAny方法提供了这样的工具。

```java
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
 *    菜单中至少存在一个素菜
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
 *    菜单中所有菜的热量都低于1000卡路里
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
 *    Dish(name=薯条, vegetarian=true, calories=530, type=OTHER)
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
 *    Dish(name=薯条, vegetarian=true, calories=530, type=OTHER)
 */
@Test
public void findFirstTest() {
 Optional<Dish> dish = menuList.stream()
  .filter(Dish::isVegetarian)
  .findFirst(); // 返回第一个元素

 dish.ifPresent(System.out::println);
}
```

##### 2.5 归约：

前面的操作都是返回一个boolean（allMatch之类的）、 void（forEach）或Optional对象（findAny等）。在这里我们可以使用reduce方法来表达更复杂的操作，比如：使用reduce求和，求最大或最小元素。

reduce接受两个参数：

- 一个初始值
- 一个Lambda来把两个流元素结合起来并产生一个新值

```java
public List<Integer> numbers = Arrays.asList(1, 2, 3, 4);

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
```

##### 2.6 根据对象属性的去重 : 

就是List的去重，不想用双重for，感觉太low，不想用for+Map，可使用java8的stream流能完美解决这个问题 . 如下 : 

```java
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
```

menuList这个list的streamAPI的聚合操作collect可以让我们只关注结果，而collect方法里的collectingAndThen又是属 于  java.util.stream.Collector，collectingAndThen操作的解释是：先执行前面的操作，然后执行第二部操作后输出结果，这里我们执行的第一步操作就是Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Dish::getType)))，第二步就是将他输出为一个新的ArrayList。

第一步操作里又是用到Collectors接口，这次用的是toCollection方法，就是将方法里的函数或者参数转化为一个collection集合，这里，我们是将Comparator.comparing(Dish::getType)转化为一个collection，这个collection是一个TreeSet。也就是有序的。因为我们需要去掉重复的值，这个set可以做到，而我们又要保持转化出来的collection依旧有序，所以使用的是一个TreeSet。

Comparator.comparing(Dish::getType)这里呢，又用到了java.util.Comparator接口，这个接口倒是挺常用的。使用的是他的comparing方法，也就是比较参数的值是否相同，里面用到的是java8的新特性lambda表达式， :: 其实和.没太大区别，举个例子，最常用的System.out.println() 可以表达为System.out::println，可以达到一样的效果

#### 3 .综合实例 : 

首先定义一个学生类（Student）和奖学金类（Scholarship）：

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {

 private static final long serialVersionUID = -6441699369599239256L;

 /**
  * 姓名
  */
 private String name;
 /**
  * 性别：1为男性；2为女性
  */
 private  int sex;
 /**
  * 就读城市
  */
 private String city;
}
```

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Scholarship implements Serializable {

 private static final long serialVersionUID = 4407066593411176281L;

 /**
  * 学生信息
  */
 private  Student student;
 /**
  * 获奖年份
  */
 private  int year;
 /**
  * 奖金金额
  */
 private  int money;
}
```

以下是几个综合测试例子，可以用于检验自己对Stream API基本用法的掌握情况：

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentPracticeTest {

  public List<Scholarship> scholarships;

  public List<Student> students;

  @Before
  public void setUp() throws Exception {
    Student stu1 = new Student("张三", 1, "广州");
    Student stu2 = new Student("李四", 1, "广州");
    Student stu3 = new Student("王五", 1, "杭州");
    Student stu4 = new Student("张伟", 1, "重庆");
    Student stu5 = new Student("王芳", 2, "重庆");
    Student stu6 = new Student("李娜", 2, "成都");
    Student stu7 = new Student("刑静", 2, "成都");
    Student stu8 = new Student("刘强", 1, "成都");
    Student stu9 = new Student();
    stu9.setName("李白");
    stu9.setSex(1);

    students = Arrays.asList(stu1, stu2, stu3, stu4, stu5, stu6, stu7, stu8);

    scholarships = Arrays.asList(
        new Scholarship(stu1, 2016, 4000)
        , new Scholarship(stu2, 2016, 4200)
        , new Scholarship(stu3, 2016, 3500)
        , new Scholarship(stu4, 2016, 3600)
        , new Scholarship(stu2, 2017, 4400)
        , new Scholarship(stu3, 2017, 3000)
        , new Scholarship(stu5, 2017, 3800)
        , new Scholarship(stu6, 2017, 2000)
        , new Scholarship(stu3, 2018, 6000)
        , new Scholarship(stu5, 2018, 7000)
        , new Scholarship(stu7, 2018, 5500)
        , new Scholarship(stu8, 2018, 1000)
        , new Scholarship(stu9, 2018, 1000)
    );
  }

  /**
   * 找出2017年的奖学金获得情况，并按奖金金额排序 输出如下: Scholarship(student=Student(name=李娜, sex=2, city=成都), year=2017, money=2000)
   * Scholarship(student=Student(name=王五, sex=1, city=杭州), year=2017, money=3000) Scholarship(student=Student(name=王芳,
   * sex=2, city=重庆), year=2017, money=3800) Scholarship(student=Student(name=李四, sex=1, city=广州), year=2017,
   * money=4400)
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
   * 找出来至成都的学生并按学生姓名排序 输出如下: Student(name=刑静, sex=2, city=成都) Student(name=刘强, sex=1, city=成都) Student(name=李娜, sex=2,
   * city=成都)
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
   * 找出奖金金额最小的奖金获得情况 输出如下 : Scholarship(student=Student(name=刘强, sex=1, city=成都), year=2018, money=1000)
   */
  @Test
  public void test5() {
    Optional<Scholarship> scholarship = scholarships.stream()
        //.reduce((s1, s2) -> s1.getMoney() < s2.getMoney() ? s1 : s2); // 方式一
        .min(Comparator.comparingInt(Scholarship::getMoney)); // 方式二
    scholarship.ifPresent(System.out::println);
  }

  /**
   * 找出奖金金额最多的奖金金额 输出 :  7000
   */
  @Test
  public void test6() {
    Optional<Integer> max = scholarships.stream()
        .map(Scholarship::getMoney)
        .reduce(Integer::max); // 方式一
    //.max(Integer::compareTo); //方式二

    max.ifPresent(System.out::println);

  }

  /**
   * 计算2018年的奖金总额 输出如下 : 20500
   */
  @Test
  public void test7() {
    int sum = scholarships.stream()
        .filter(scholarship -> scholarship.getYear() == 2018)
        //.mapToInt(Scholarship::getMoney).sum(); // 方式一
        //.collect(Collectors.summingInt(Scholarship::getMoney)); //方式二
        .map(Scholarship::getMoney).reduce(Integer::sum).get(); //方式三

    System.out.println(sum);

  }

  /**
   * 按年份分组，并统计每年的奖金总额 输出如下 : 2016 : 15300 2017 : 13200 2018 : 20500
   */
  @Test
  public void test8() {
    Map<Integer, Integer> stuSumMap = scholarships.stream()
        .collect(Collectors.groupingBy(Scholarship::getYear, Collectors.summingInt(Scholarship::getMoney)));

    stuSumMap.forEach((key, value) -> {
      System.out.println(key + " : " + value);
    });
  }

  /**
   * 按年份分组，并获取每年获得奖金最高的学生信息
   * 输出如下 :
   * 2016 : Student(name=李四, sex=1, city=广州)
   * 2017 : Student(name=李四, sex=1, city=广州)
   * 2018 : Student(name=王芳, sex=2, city=重庆)
   */
  @Test
  public void test9() {
    Map<Integer, Student> studentMap = scholarships.stream().collect(Collectors.groupingBy(Scholarship::getYear, // 按年份进行分组
        Collectors.collectingAndThen( // 去除包装的Optional
            Collectors.maxBy(Comparator.comparingInt(Scholarship::getMoney)), // //按奖学金排序取最大值
            o -> o.isPresent() ? o.get().getStudent() : null)));

    studentMap.forEach((key,value) ->{
      System.out.println(key + " : " + value);
    });
  }

  /**
   * 按年份分组，再按照性别分组获取相应的学生信息
   * 输出如下 :
   * 2016 --> {男生=[Student(name=张三, sex=1, city=广州), Student(name=李四, sex=1, city=广州), Student(name=王五, sex=1, city=杭州), Student(name=张伟, sex=1, city=重庆)]}
   * 2017 --> {男生=[Student(name=李四, sex=1, city=广州), Student(name=王五, sex=1, city=杭州)], 女生=[Student(name=王芳, sex=2, city=重庆), Student(name=李娜, sex=2, city=成都)]}
   * 2018 --> {男生=[Student(name=王五, sex=1, city=杭州), Student(name=刘强, sex=1, city=成都), Student(name=李白, sex=1, city=null)], 女生=[Student(name=王芳, sex=2, city=重庆), Student(name=刑静, sex=2, city=成都)]}
   */
  @Test
  public void test10() {
    Map<Integer, Map<String, List<Student>>> stuSexMap  = scholarships.stream()
        .collect(Collectors.groupingBy(Scholarship::getYear, // 外层按年份分组
            Collectors.groupingBy(s -> { // 内层按学生性别分组
                int sex = s.getStudent().getSex();
                return sex == 1 ? "男生" : "女生"; //自定义返回内容
        }, Collectors.mapping(Scholarship::getStudent, Collectors.toList()))));//收集学生信息

    stuSexMap.forEach((key, value) -> {
      System.out.println(key + " --> " + value);
    });
  }

  /**
   * 根据学生所属城市进行去重
   * 输出如下 :
   * Student(name=张三, sex=1, city=广州)
   * Student(name=李娜, sex=2, city=成都)
   * Student(name=王五, sex=1, city=杭州)
   * Student(name=张伟, sex=1, city=重庆)
   */
  @Test
  public void test11() {
    List<Student> list = students.stream()
        .distinct() // 此方法去重是计算对象的 hashCode 和 toString
        .collect(Collectors.toList());

    list.forEach(System.out::println);
    // 上面的方法没有起到去重的作用

    System.out.println("-----------------");

    List<Student> afterStudens = students.stream().collect(Collectors
        .collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Student::getCity))),
            ArrayList::new));

    afterStudens.forEach(System.out::println);
  }
}
```



#### 4. 总结 

##### 4.1 流中间操作

|  操 作   | 类 型 | 返回类型  |        操作参数        |   函数描述符   |
| :------: | :---: | :-------: | :--------------------: | :------------: |
|  filter  | 中间  | Stream<T> |      Predicate<T>      |  T -> boolean  |
|   map    | 中间  | Stream<R> |     Function<T ,R>     |     T -> R     |
|  limit   | 中间  | Stream<T> |                        |                |
|  sorted  | 中间  | Stream<T> |     Comparator<T>      | (T ,T)  -> int |
| distinct | 中间  | Stream<T> |                        |                |
| flatMap  | 中间  | Stream<R> | Function<T ,Stream<R>> | T -> Stream<R> |
|          |       |           |                        |                |
|          |       |           |                        |                |
|          |       |           |                        |                |

##### 4.2 终端操作

|   操作    | 类型 |  返回类型   |      操作参数      |  函数描述符  |
| :-------: | :--: | :---------: | :----------------: | :----------: |
|  forEach  | 终端 |    void     |    Consumer<T>     |  T -> void   |
|   count   | 终端 |    Long     |                    |              |
|  collect  | 终端 |      R      | Collector<T ,A ,R> |              |
| anyMatch  | 终端 |   boolean   |    Predicate<T>    | T -> boolean |
| allMatch  | 终端 |   boolean   |   Predicate< T>    | T -> boolean |
| noneMatch | 终端 |   boolean   |    Predicate<T>    | T -> boolean |
|  findAny  | 终端 | Optional<T> |                    |              |
| findFirst | 终端 | Optional<T> |                    |              |
|  reduce   | 终端 | Optional<T> | BinaryOperator<T>  | (T, T) -> T  |



----

#### 5. 重新再来一遍

**初始化**

```java
@Data
@AllArgsConstructor
public class Employee implements Serializable {

   private static final long serialVersionUID = -8078550089934349371L;

   private Long id;
   /**
    * 姓名
    */
   private String name;
   /**
    * 年龄
    */
   private Integer age;

   /**
    * 部门
    */
   private String department;

   /**
    * 专业
    */
   private String profession;

   /**
    * 地址
    */
   private String Address;
}
```

```java
public static List<Employee> employees = Collections.unmodifiableList(Arrays.asList(
   new Employee(1L, "张三", 18, "技术开发中心", "计算机科学", "江西南昌"),
   new Employee(2L, "李四", 18, "技术开发中心", "计算机科学", "江西抚州"),
   new Employee(3L, "王五", 22, "人力行政中心", "土木工程", "北京"),
   new Employee(4L, "赵六", 23, "财务管理中心", "经济管理", "广东广州"),
   new Employee(5L, "田七", 28, "财务管理中心", "机械与自动化", "上海"),
   new Employee(5L, "田七", 28, "财务管理中心", "机械与自动化", "上海"),
   new Employee(6L, "孔明", 26, "战略合作事业部", "生化工程", "广东深圳"),
   new Employee(7L, "玄德", 40, "战略合作事业部", "生命科学", "江苏南京"),
   new Employee(8L, "云长", 33, "客户服务部", "社会学", "广东广州"),
   new Employee(9L, "翼德", 28, "智能金融事业部", "经济管理", "江苏南京"),
   new Employee(10L, "鲁肃", 36, "智能金融事业部", "经济管理", "上海")
));
```

**1.0 过滤**

- **filter**  筛选年龄小于25的员工

  ```java
  List<Employee> list = employees.stream()
  			.filter(employee -> employee.getAge() > 25)
  			.collect(Collectors.toList());
  ```

- **distinct** 筛选重复元素

    ```java
    List<Employee> list = employees.stream()
    			.filter(employee -> employee.getAge() > 25)
    			.distinct()
    			.collect(Collectors.toList());
    ```

- **limit** 截断流

 ```java
List<Employee> list = employees.stream()
			.filter(employee -> employee.getAge() > 25)
			.limit(3)
			.collect(Collectors.toList());
 ```

- ​ **skip** 跳过元素

 ```java
List<Employee> list = employees.stream()
    			.filter(employee -> employee.getAge() > 25)
    			.skip(2)
    			.collect(Collectors.toList());
 ```

**2.0 映射**

- **map** 获取所有员工的姓名

  ```java
  List<String> list = employees.stream()
  			.map(Employee::getName)
  			.collect(Collectors.toList());
  ```


- **flatMap** 扁平流

    ```java
    String[] arrays = {"Hello", "World"};
    List<String> list = Arrays.stream(arrays)
                .map(str -> str.split("")) // 映射成为Stream<String[]>
                .flatMap(Arrays::stream) // 扁平化为Stream<String>
                .distinct()
                .collect(Collectors.toList());
    ```

**3.0 查找和匹配**

- **anyMatch**  流中是否有一个元素能匹配给定的谓词 返回  boolean

  ```java
  boolean result = employees.stream()
  		   .anyMatch(employee -> employee.getAge() == 28);
  ```

- **allMatch** 流中的所有元素能否符合条件

     ```java
      boolean result = employees.stream()
              .allMatch(employee -> employee.getAge() < 35);
     ```

- **noneMatch** 可以确保流中没有任何元素与给定的谓词匹配

     ```java
     boolean result = employees.stream()
     		.noneMatch(employee -> employee.getAge() > 40);
     ```

- **findAny** 返回当前流中的任意元素

     ```java
     Optional<Employee> result = employees.stream()
     			.filter(employee -> Objects.equals(employee.getProfession(), "计算机科学"))
     			.findAny();
     ```

- **findFirst** 返回当前流中的第一个元素

     ```java
     Optional<Employee> result = employees.stream()
     			.filter(employee -> Objects.equals(employee.getProfession(), "计算机科学"))
     			.findFirst();
     ```


     你可能会想，为什么会同时有 findFirst 和 findAny 呢？答案是并行。找到第一个元素在并行上限制更多。如果你不关心返回的元素是哪个，请使用 findAny ，因为它在使用并行流时限制较少.

**4.0 归约**

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
// 计算数值总合
// 方式一
Integer result1 = numbers.stream().reduce(0, (a, b) -> a + b);
// 方式二
Integer result2 = numbers.stream().reduce(0, Integer::sum);
// 计算最大值
Optional<Integer> min = numbers.stream().reduce(Integer::min);
// 计算最小值
Optional<Integer> min = numbers.stream().reduce(Integer::max);
```

**5.0 收集器**

- **eg : 计算 员工总数**

 ```java

long count = employees.stream().count();

Long count = employees.stream().collect(Collectors.counting());
 ```

- **eg : 查找 员工中年龄最大的**

  ```java

  Optional<Employee> employee = employees.stream()
  			.collect(Collectors.maxBy(Comparator.comparing(Employee::getAge)));
  			
  Optional<Employee> employee = employees.stream()
               .max(Comparator.comparing(Employee::getAge));
  ```

- **eg: 员工年龄汇总**

  ```java
  Integer sum = employees.stream()
  			.mapToInt(Employee::getAge).sum();
  			
  Integer sum = employees.stream()
  			.collect(Collectors.summingInt(Employee::getAge));
  ```

- **eg : 员工平均值**

  ```java
  Double averaging = employees.stream().collect(Collectors.averagingInt(Employee::getAge))
  ```

**6.0 分组**

- **eg : 员工 按专业进行分组**

  ```java
  Map<String, List<Employee>> map = employees.stream()
  			.collect(Collectors.groupingBy(Employee::getProfession));
  ```

- **eg : 员工 按专业进行分组 并统计专业人数**

  ```java
  Map<String, Long> map = employees.stream()
  			.collect(Collectors.groupingBy(Employee::getProfession, Collectors.counting()));
  ```

- **eg : 按专业进行分组 并查找专业中年龄最大的那人**

 ```java
  Map<String, Optional<Employee>> map = employees.stream().collect(
  			Collectors.groupingBy(Employee::getProfession,                           								Collectors.maxBy(Comparator.comparing(Employee::getAge))));
 ```

- **eg : 将收集器结果转换成 另一种类型  Collectors.collectingAndThen**


  ```java
  Map<String, Employee> map = employees.stream().collect(
   Collectors.groupingBy(Employee::getProfession,Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(Employee::getAge)),Optional::get)));
  ```

- **eg : 将收集器映射成具体的对象**

  ```java
  Map<String, Set<Integer>> map = employees.stream().collect(
  			Collectors.groupingBy(Employee::getProfession, Collectors.mapping(Employee::getAge, Collectors.toSet())));
  ```

  ​