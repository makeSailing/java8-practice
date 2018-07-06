package com.makesailing.practice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * #
 *
 *
 * @date 2018/7/5 15:21
 */
@RestController
public class PracticeController {

	@GetMapping("/hello")
	public String index() {
		return "hello world";
	}
}


