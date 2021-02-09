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
import org.zerock.domain.PageMaker;
import org.zerock.domain.SearchCriteria;
import org.zerock.service.BoardService;

@Controller
@RequestMapping("/sboard/*")
public class SearchBoardController {

	private static final Logger logger = LoggerFactory.getLogger(SearchBoardController.class);
	
	@Inject
	private BoardService service;
	
	@GetMapping("/list")
	public void listPage(@ModelAttribute("cri")SearchCriteria cri, Model model)throws Exception{
//		model.addAttribute("list",service.listCriteria(cri));
		model.addAttribute("list",service.listSearchCriteria(cri));
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
//		pageMaker.setTotalCount(service.listCountCriteria(cri));
		pageMaker.setTotalCount(service.listSearchCount(cri));
		model.addAttribute("pageMaker",pageMaker);
	}
	
	@GetMapping("/readPage")
	public void read(@RequestParam("bno")int bno, @ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception{
		model.addAttribute(service.read(bno));
	}
	
	@PostMapping("/removePage")
	public String remove(@RequestParam("bno")int bno, SearchCriteria cri, RedirectAttributes rttr) throws Exception{
		service.remove(bno);
		
		rttr.addAttribute("page",cri.getPage());
		rttr.addAttribute("perPageNum",cri.getPerPageNum());
		rttr.addAttribute("searchType",cri.getSearchType());
		rttr.addAttribute("keyword",cri.getKeyword());
		
		rttr.addFlashAttribute("msg","success");
		return "redirect:/sboard/list";
	}
	
	@GetMapping("/modifyPage")
	public void modifyPagingGET(int bno, @ModelAttribute("cri")SearchCriteria cri, Model model) throws Exception{
		model.addAttribute(service.read(bno));
	}
	
	@PostMapping("/modifyPage")
	public String modifyPagingPOST(BoardVO board, SearchCriteria cri, RedirectAttributes rttr)throws Exception{
		service.modify(board);
		
		rttr.addAttribute("page",cri.getPage());
		rttr.addAttribute("perPageNum",cri.getPerPageNum());
		rttr.addAttribute("searchType",cri.getSearchType());
		rttr.addAttribute("keyword",cri.getKeyword());
		
		rttr.addFlashAttribute("msg","success");
		return "redirect:/sboard/list";
	}
	
	@GetMapping("/register")
	public void registGET() throws Exception{
	}
	
	@PostMapping("/register")
	public String registPOST(BoardVO board, RedirectAttributes rttr) throws Exception{
		service.regist(board);
		rttr.addFlashAttribute("msg","success");
		
		return "redirect:/sboard/list";
	}
}
