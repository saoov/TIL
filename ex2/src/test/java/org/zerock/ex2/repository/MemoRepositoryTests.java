package org.zerock.ex2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.annotation.Commit;
import org.zerock.ex2.entity.Memo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    /*
    본격적인 test를 진행하기 전 memoRepository의 주입이 잘 되었는지 확인하는 것이 좋다
     */
    @Test
    void testClass(){
        System.out.println(memoRepository.getClass().getName());
    }

    /*
    Stream을 이용해 1~100개 Memo 객체를 생성, 데이터를 넣어주고 MemoRepository를 이용해 insert한다
     */
    @Test
    void testInsertDummies(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Memo memo = Memo.builder().memoText("sample..." + i).build();
            memoRepository.save(memo);
        });
    }

    /*
    findByid의 경우 Optional타입으로 반환되기 때문에 한번 더 결과가 존재하는지 체크해야한다.
    findByid()메서드 실행 순간에 sql은 처리된다. (=======이전에)
    아래의 getOne()과 비교
     */
    @Test
    void testSelect(){
        //데이터 베이스에 존재하는 mno의 값
        Long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("=================================");

        if (result.isPresent()) {
            Memo memo = result.get();
            System.out.println("memo = " + memo);
        }
    }

    /*
    getOne()의 경우 트랜잭션 처리를 위해 @Transactional 어노테이션이 필요하고
    리턴 값은 해당 객체이지만 실제 객체가 필요한 순간까지(memo를 프린트) sql을 실행하지 않는다.
    따라서 ========이후 sql을 실행하게 된다.
     */
    @Transactional
    @Test
    void testSelect2(){
        //데이터 베이스에 존재하는 mno의 값
        Long mno = 100L;

        Memo memo = memoRepository.getOne(mno);

        System.out.println("=================================");

            System.out.println("memo = " + memo);
        }

        /*
        수정도 insert와 동일하게 save()를 이용하여 처리한다. 내부적으로 @Id의 값이 일치하는지 확인해서 insert 또는 update를 처리한다.
        jpa는 엔티티 객체를 메모리상에 보관하기 때문에 특정 엔티티 객체가 존재하는지 확인하는 select가 먼저 실행되고
        해당 @Id를 가진 엔티티 객체가 있다면 update, 없으면 insert를 실행(auto_increment의 다음번호로)하여 생성한다.
         */
    @Test
    void testUpdate(){
        Memo memo = Memo.builder().mno(100L).memoText("UPdate Text!!").build();
        System.out.println(memoRepository.save(memo));
    }

    /*
    삭제의 경우 삭제하려는 @Id값이 있는지 확인하고 삭제하려고 하므로 없으면 EmptyResultDataAccessException를 발생한다.
     */
    @Test
    void testDelete(){
        Long mno = 201L;
        memoRepository.deleteById(mno);
    }

    /*
    findAll()의 리턴타입을 Page로 지정하기 위해서는 파라미터는 반드시 Pageable타입이어야 한다.
    첫번쨰 쿼리에서 MariaDB에서 페이징 처리에 사용하는 limit 구문이 사용되고 두번째 쿼리에서 count()를 이용해 전체 개수를 처리한다.
     */
    @Test
    void testPageDefault(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println("result = " + result);
        System.out.println("========================================");

        System.out.println("Total Pages : " + result.getTotalPages());
        System.out.println("Total Count : " + result.getTotalElements());
        System.out.println("Page Number : " + result.getNumber());
        System.out.println("Page Size : " + result.getSize());
        System.out.println("has next page? : " + result.hasNext());
        System.out.println("first page?:" + result.isFirst());

        System.out.println("========================================");
        for (Memo memo : result.getContent()) {
            System.out.println(memo);
        }

    }

    /*
    페이징 처리 담당하는 PageRequest에 정렬과 관련된 Sort타입을 파라미터로 전달할 수 있다.
    mno필드의 값을 역순으로 정렬하도록 Sort.by().descending()를 이용.
    Sort타입을 이용할 경우 여러 정렬조건을 and()를 이용해 결합할 수 있다.
     */
    @Test
    void testSort(){
        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2);
        Pageable pageable = PageRequest.of(0,10, sortAll);
        Page<Memo> result = memoRepository.findAll(pageable);
        result.get().forEach(memo->{
            System.out.println(memo);
        });
    }

    /*
    JpaRepository의 쿼리메서드를 이용
    MemoRepository에 추가한 쿼리메서드를 이용해 특정 조건을 가지는 객체 구하기
    findByMnoBetweenOrderByMnoDesc()는 mno를 기준으로 between을 사용하고 orderby가 적용되는 것을 명시했다.
    실제 쿼리의 경우 mno between ? and ? order by mno desc로 실행된다
    
     */
    @Test
    void testQueryMethods(){
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);

        for (Memo memo : list) {
            System.out.println(memo);
        }
    }

    /*
    쿼리 메서드 사용할 때 OrderBy키워드 등의 사용이 메서드 이름이 길어지고 혼동이 생기기 떄문에
    Pageable파라미터를 결합해서 사용할 수 있다.
     */

    @Test
    void testQueryMethodWithPageable(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
        Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageable);
        result.get().forEach(memo -> System.out.println(memo));
    }

    /*
    deleteBy로 시작하는 삭제처리는 select문으로 엔티티 객체를 가져와서 각 엔티티를 삭제하는 작업이 진행되기 때문에
    @Transactional이 필요하고 테스트 코드의 deleteBy는 기본적으로 롤백처리되기 때문에 @Commit을 통해 커밋한다.
    deleteBy는 실제 개발에 많이 사용되지 않는데 그 이유는 sql을 이용하듯 한번에 삭제되는게 아니라
    하나하나 각 엔티티 객체를 삭제하기 때문이다. 삭제할 엔티티 수만큼 sql문 실행되므로 효율적이지 않은 것 같음
    ->@Query를 이용해 비효율을 개선한다.
     */
    @Commit
    @Transactional
    @Test
    void testDeleteQueryMethods(){
        memoRepository.deleteMemoByMnoLessThan(100L);
    }


    /*
    @Query를 이용할 경우 DB의 테이블 대신 엔티티 클래스와 멤버 변수를 이용해서 SQL과 비슷한 JPQL을 작성한다.
    JPQL이 SQL과 유사하듯 실제 SQL에서 사용되는 함수도 JPQL에서 동일하게 사용할 수 있다.
     */
    @Test
    void testQueryMethodWithQuery(){
        List<Memo> list = memoRepository.getListDesc();
        for (Memo memo : list) {
            System.out.println(memo);
        }
    }

    @Test
    void testListJQPL(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<Memo> result = memoRepository.getListWithQuery(20L, pageable);
        result.get().forEach(memo -> System.out.println(memo));
    }

    @Test
    void testUpdateJQPL(){
        System.out.println(memoRepository.updateMemoText(22L, "무야호"));
    }


}
