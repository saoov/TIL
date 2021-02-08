package org.zerock.web;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.persistence.BoardDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/root-context.xml" })
public class BoardDAOTest {

	@Inject
	private BoardDAO dao;

	private static Logger logger = LoggerFactory.getLogger(BoardDAOTest.class);

//	@Test
//	public void testCreate() throws Exception{
//		BoardVO board = new BoardVO();
//		board.setTitle("새로운 제목을 넣습니다.");
//		board.setContent("새로운 글을 넣습니다.");
//		board.setWriter("user000");
//		dao.create(board);
//	}
//	
//	
//	@Test
//	public void testRead() throws Exception{
//		logger.info(dao.read(2).toString());
//	}
//	
//	@Test
//	public void testUpdate() throws Exception{
//		BoardVO board = new BoardVO();
//		board.setBno(2);
//		board.setTitle("수정된 제목입니다.");
//		board.setContent("수정 테스트");
//		dao.update(board);
//	}
//	
//	@Test
//	public void testDelete() throws Exception{
//		dao.delete(2);
//	}
//	
//	@Test
//	public void testList() throws Exception{
//		List<BoardVO> list = dao.listAll();
//		for(int i = 0; i<list.size(); i++) {
//			logger.info(list.get(i).toString());
//		}
//	}

	
	/*
	 * 페이지당 10개 출력되는지 테스트
	 */
	@Test
	public void testListPage() throws Exception {
		int page = 3;

		List<BoardVO> list = dao.listPage(page);
		for (BoardVO boardVO : list) {
			logger.info(boardVO.getBno() + ":" + boardVO.getTitle());
		}
	}

	/*
	 * Page와 PerPage정해서 출력하는 테스트
	 */
	@Test
	public void testListCriteria() throws Exception {
		Criteria cri = new Criteria();
		cri.setPage(2);
		cri.setPerPageNum(20);

		List<BoardVO> list = dao.listCriteria(cri);

		for (BoardVO boardVO : list) {
			logger.info(boardVO.getBno() + ":" + boardVO.getTitle());
		}
	}
}
