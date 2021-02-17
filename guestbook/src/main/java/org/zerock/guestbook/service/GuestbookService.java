package org.zerock.guestbook.service;

import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;

public interface GuestbookService {

    Long register(GuestbookDTO dto);

    PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO);

    /*
    기존의 DTO클래스와 entity 클래스를 가능하면 변경하고 싶지 않기 대문에 default메서드를 이용해 이를 처리하도록 한다.
     */
    default Guestbook dtoToEntity(GuestbookDTO dto) {
        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }

    /*
    entity 객체를 DTO 객체로 변환
     */
    default GuestbookDTO entityToDto(Guestbook entity) {
     GuestbookDTO dto = GuestbookDTO.builder()
             .gno(entity.getGno())
             .title(entity.getTitle())
             .content(entity.getContent())
             .writer(entity.getWriter())
             .regDate(entity.getRegDate())
             .modDate(entity.getModDate())
             .build();
     return dto;
    }
    
    /*
    게시글 내용 확인
     */
    GuestbookDTO read(Long gno);


    /*
    게시글 수정과 삭제
     */
    void remove(Long gno);
    void modify(GuestbookDTO dto);
}
