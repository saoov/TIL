package org.zerock.guestbook.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.entity.QGuestbook;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository guestbookRepository;

    /*
    Querydsl을 이용해 300개의 data를 넣는 테스트
     */
    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1,300).forEach(i -> {
            Guestbook guestbook = Guestbook.builder()
                    .title("Title~~~" + i)
                    .content("content......" + i)
                    .writer("user~~" + (i % 10))
                    .build();
            System.out.println(guestbookRepository.save(guestbook));
        });
    }

    /*
    Guestbook에 change메서드를 생성하고 moddate값이 수정되는지 확인하는 테스트
     */
    @Test
    public void updateTest(){
        Optional<Guestbook> result = guestbookRepository.findById(300L);

        if (result.isPresent()) {
            Guestbook guestbook = result.get();

            guestbook.changeTitle("Changed Title");
            guestbook.changeContent("Changed Content");

            guestbookRepository.save(guestbook);
        }
    }

    @Test
    public void testQuery1(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        /*
        동적 처리를 위해 Q도메인 클래스를 가져온다.
        -> Q도메인 클래스를 이용하면 엔티티 클래스에 선언된 title,content 같은 필드들을 변수로 활용할 수 있다.
         */
        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = "1";

        /*
        BooleanBuilder는 where문에 들어가는 조건들을 넣어주는 컨테이너로 보자
         */
        BooleanBuilder builder = new BooleanBuilder();

        /*
        원하는 조건은 필드 값과 같이 결합해서 생성.
         */
        BooleanExpression expression = qGuestbook.title.contains(keyword);

        /*
        만들어진 조건은 where문에 and나 or같은 키워드와 결합.
         */
        builder.and(expression);

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });


    }

    /*
    다중항목 검색 테스트
     */
    @Test
    public void testQuery2(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression exTitle = qGuestbook.title.contains(keyword);
        BooleanExpression exContent = qGuestbook.content.contains(keyword);
        /*
        BooleanExpression을 결합하는 부분
         */
        BooleanExpression exAll = exTitle.or(exContent);
        /*
        결합 내용을 BooleanBuilder에 추가
         */
        builder.and(exAll);
        /*
        gno>0 조건을 추가
         */
        builder.and(qGuestbook.gno.gt(0L));

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }
}
