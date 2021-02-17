package org.zerock.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {

    private int page;
    private int size;
    private String type;
    private String keyword;

    public PageRequestDTO() {
        this.page = 1;
        this.size = 10;
    }
/*
JPA를 이용하면 페이지 번호가 0부터 시작하는 점을 감안해서 1페이지의 경우 0이 되도록 page - 1을 함
 */
    public Pageable getPageable(Sort sort){
        return PageRequest.of(page - 1, size, sort);
    }

}
