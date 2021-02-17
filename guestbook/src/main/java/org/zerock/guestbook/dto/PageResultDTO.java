package org.zerock.guestbook.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> {

    //DTO리스트
    private List<DTO> dtoList;
    //총 페이지 번호
    private int totalPage;
    //현재 페이지 번호
    private int page;
    //목록 사이즈
    private int size;
    //시작 페이지 번호, 끝 페이지 번호
    private int start, end;
    //이전, 다음
    private boolean prev, next;
    //페이지 번호 목록
    private List<Integer> pageList;


    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        /*
        JPA를 이용하는 Repository에서는 페이지 처리 결과를 Page<Entity>타입으로 반환.
        따라서 DTO객체로 변환해서 자료구조로 담아야 한다.
        */
        dtoList = result.stream().map(fn).collect((Collectors.toList()));
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();
        
        //마지막 페이지 (임시)
        int tempEnd = (int)(Math.ceil(page / 10.0)) * 10;

        start = tempEnd - 9;

        prev = start > 1;

        end = totalPage > tempEnd ? tempEnd : totalPage;

        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }

}
