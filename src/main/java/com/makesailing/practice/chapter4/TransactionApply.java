package com.makesailing.practice.chapter4;

import static java.util.stream.Collectors.joining;

import com.makesailing.practice.domain.Trader;
import com.makesailing.practice.domain.Transaction;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * #
 *
 *
 * @date 2018/7/7 11:25
 */
public class TransactionApply {

	static Trader raoul = new Trader("Raoul", "Cambridge");
	static Trader mario = new Trader("Mario","Milan");
	static Trader alan = new Trader("Alan","Cambridge");
	static Trader brian = new Trader("Brian","Cambridge");

	static List<Transaction> transactions = Arrays.asList(
		new Transaction(brian, 2011, 300),
		new Transaction(raoul, 2012, 1000),
		new Transaction(raoul, 2011, 400),
		new Transaction(mario, 2012, 710),
		new Transaction(mario, 2012, 700),
		new Transaction(alan, 2012, 950)
	);

	/**
	 *  (1) 找出2011年发生的所有交易，并按交易额排序（从低到高）
	 * @return
	 */
	public static List<Transaction> test1(){
		List<Transaction> list = transactions.stream()
			.filter(transaction -> transaction.getYear() == 2011)
			.sorted(Comparator.comparing(Transaction::getValue))
			.collect(Collectors.toList());
		return list;
	}
//		(2) 交易员都在哪些不同的城市工作过？
	public static List<String> test2() {
		return transactions.stream()
			.map(transaction -> transaction.getTrader().getCity())
			.distinct()
			.collect(Collectors.toList());
	}
//		(3) 查找所有来自于剑桥的交易员，并按姓名排序。
	public static List<Trader> test3(){
		return transactions.stream()
			.map(Transaction::getTrader)
			.filter(trader -> Objects.equals(trader.getCity(),"Cambridge"))
			.distinct()
			.sorted(Comparator.comparing(Trader::getName))
			.collect(Collectors.toList());
	}
//		(4) 返回所有交易员的姓名字符串，按字母顺序排序。
	public static String test4(){
		return transactions.stream()
			.map(transaction -> transaction.getTrader().getName())
			.distinct()
			.sorted()
			.collect(joining());
	}
//		(5) 有没有交易员是在米兰工作的？
	public static boolean test5(){
		return transactions.stream()
			.anyMatch(transaction -> Objects.equals(transaction.getTrader().getCity(), "Milan"));
	}
//		(6) 打印生活在剑桥的交易员的所有交易额。
	public static Integer test6(){
		int sum = transactions.stream()
			.filter(transaction -> Objects.equals(transaction.getTrader().getCity(), "Cambridge"))
			.mapToInt(Transaction::getValue).sum();
		return sum;
	}
//		(7) 所有交易中，最高的交易额是多少？
	public static Optional<Integer> test7(){
		Optional<Integer> result = transactions.stream().map(Transaction::getValue).reduce(Integer::max);
		return result;
	}
//		(8) 找到交易额最小的交易。

	public static void main(String[] args) {
		/**
		 * (1) 找出2011年发生的所有交易，并按交易额排序（从低到高）
		 */
//		System.out.println(test1());
		/**
		 * (1) 交易员都在哪些不同的城市工作过
		 */
//		System.out.println(test2());
		/**
		 * (3) 查找所有来自于剑桥的交易员，并按姓名排序。
		 */
//		System.out.println(test3());

		/**
		 * 返回所有交易员的姓名字符串，按字母顺序排序
		 */
//		System.out.println(test4());
		/**
		 * (5) 有没有交易员是在米兰工作的？
		 */
//		System.out.println(test5());
		/**
		 * 打印生活在剑桥的交易员的所有交易额。
		 */
//		System.out.println(test6());

		/**
		 * 所有交易中，最高的交易额是多少？
		 */
		System.out.println(test7());
	}
}


