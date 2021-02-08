package org.zerock.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SampleController {

	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);
	
	@GetMapping("doA")
	public void doA() {
		logger.info("doA called~~~~~~");
	}
	
	@GetMapping("doB")
	public void doB() {
		logger.info("doB called~~~~~~");
	}
}
