package org.zerock.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zerock.domain.ProductVO;

@Controller
public class SampleController5 {

	@RequestMapping("/doJSON")
	public @ResponseBody List<ProductVO> doJSON() {
		List<ProductVO> list = new ArrayList<>();
		ProductVO vo1 = new ProductVO("sample notebook1", 10000);
		ProductVO vo2 = new ProductVO("sample notebook2", 20000);
		ProductVO vo3 = new ProductVO("sample notebook3", 30000);
		list.add(vo1);
		list.add(vo2);
		list.add(vo3);
		return list;
	}
}
