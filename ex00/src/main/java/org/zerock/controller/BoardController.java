package org.zerock.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageMaker;
import org.zerock.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	private BoardService service;
	
	@GetMapping("/register")
	public void registerGET(BoardVO board, Model model) throws Exception{
	}
	
	@PostMapping("/register")
	public String registerPOST(BoardVO board, RedirectAttributes rttr) throws Exception{
		service.regist(board);
		//model에 attribute2개 넘기면 2개 넘어감(get방식으로)
		rttr.addFlashAttribute("msg","success");
		return "redirect:/board/listAll";
	}
	
	@GetMapping("/listAll")
	public void listAll(Model model) throws Exception{
		model.addAttribute("list",service.listAll());
	}
	
	@GetMapping("/readPage")
	public void read(@RequestParam("bno")int bno,@ModelAttribute("cri")Criteria cri, Model model) throws Exception{
		model.addAttribute(service.read(bno));
	}
	
	@PostMapping("/removePage")
	public String remove(@RequestParam("bno")int bno,Criteria cri, RedirectAttributes rttr) throws Exception{
		service.remove(bno);
		rttr.addAttribute("page",cri.getPage());
		rttr.addAttribute("perPageNum",cri.getPerPageNum());
		rttr.addFlashAttribute("msg","success");
		return "redirect:/board/listPage";
	}
	
	@GetMapping("/modifyPage")
	public void modifyPagingGet(@RequestParam("bno")int bno, @ModelAttribute("cri")Criteria cri, Model model) throws Exception{
		model.addAttribute(service.read(bno));
	}
	
	@PostMapping("/modifyPage")
	public String modifyPOST(BoardVO board,Criteria cri, RedirectAttributes rttr) throws Exception{
		service.modify(board);
		rttr.addAttribute("page",cri.getPage());
		rttr.addAttribute("perPageNum",cri.getPerPageNum());
		rttr.addFlashAttribute("msg","success");
		return "redirect:/board/listPage";
	}
	
//	@GetMapping("/listCri")
//	public void listAll(Criteria cri, Model model) throws Exception{
//		model.addAttribute("list", service.listCriteria(cri));
//	}
	
	@GetMapping("/listPage")
	public void listPage(@ModelAttribute("cri")Criteria cri, Model model) throws Exception{
		model.addAttribute("list",service.listCriteria(cri));
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.listCountCriteria(cri));
		
		model.addAttribute("pageMaker",pageMaker);
		
	}
}
