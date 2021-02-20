package org.zerock.mreview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.zerock.mreview.entity.Member;

import javax.transaction.Transactional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertMembers(){
        IntStream.rangeClosed(1,100).forEach(i->{
           Member member = Member.builder()
                   .email("r"+i+"@zerock.org")
                   .pw("1111")
                   .nickname("reviewer"+i).build();
           memberRepository.save(member);
        });
    }
    @Commit
    @Transactional
    @Test
    public void testDeleteMember(){
        Long mid = 3L;

        Member member = Member.builder().mid(mid).build();

        //기존-> 회원을 먼저 삭제시킬 경우 FK를 가지기 떄문에 에러
//        memberRepository.deleteById(mid);
//        reviewRepository.deleteByMember(member);

        //순서 주의
        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(mid);
    }




}
