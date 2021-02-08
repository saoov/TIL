package org.zerock.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
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
	
	@GetMapping("/read")
	public void read(@RequestParam("bno")int bno, Model model) throws Exception{
		model.addAttribute(service.read(bno));
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("bno")int bno, RedirectAttributes rttr) throws Exception{
		service.remove(bno);
		rttr.addFlashAttribute("msg","success");
		return "redirect:/board/listAll";
	}
	
	@GetMapping("/modify")
	public void modifyGet(int bno, Model model) throws Exception{
		model.addAttribute(service.read(bno));
	}
	
	@PostMapping("/modify")
	public String modifyPOST(BoardVO board, RedirectAttributes rttr) throws Exception{
		service.modify(board);
		rttr.addFlashAttribute("msg","success");
		return "redirect:/board/listAll";
	}
}
