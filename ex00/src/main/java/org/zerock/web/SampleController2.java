package org.zerock.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class SampleController2 {

	private static final Logger logger = LoggerFactory.getLogger(SampleController2.class);
	
	@GetMapping("doC")
	public String doC(@ModelAttribute("msg")String msg) {
		logger.info("doC called~~~~~~~~~~~~~~~");
		return "result";
	}
}
