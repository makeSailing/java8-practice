package com.makesailing.practice.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * # 交易员
 *
 *
 * @date 2018/7/7 11:22
 */
@Data
@AllArgsConstructor
public class Trader implements Serializable {

	private static final long serialVersionUID = -7707856951489216411L;

	private final String name;

	private final String city;
}


