package com.makesailing.practice.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * # 交易
 *
 *
 * @date 2018/7/7 11:23
 */
@Data
@AllArgsConstructor
public class Transaction implements Serializable {

	private static final long serialVersionUID = 4982786153207641590L;

	private final Trader trader;

	private final int year;

	private final int value;
}


