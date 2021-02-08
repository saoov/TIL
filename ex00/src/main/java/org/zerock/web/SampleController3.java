package org.zerock.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.zerock.domain.ProductVO;

@Controller
public class SampleController3 {

	private static final Logger logger = LoggerFactory.getLogger(SampleController3.class);
	
	@GetMapping("doD")
	public String doD(Model model) {
		ProductVO product = new ProductVO("Sample Product",20000);
		
		logger.info("doD~~~~~");
		
		model.addAttribute(product);
		return "productDetail";
	}
}
