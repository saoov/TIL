package org.zerock.mreview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.zerock.mreview.entity.Member;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertMovieReviews() {
        //200개 리뷰 등록
        IntStream.rangeClosed(1, 200).forEach(i -> {
            //영화 번호
            Long mno = (long) (Math.random() * 100) + 1;

            //리뷰어 번호
            Long mid = ((long) (Math.random() * 100) + 1);
            Member member = Member.builder().mid(mid).build();

            Review movieReview = Review.builder()
                    .member(member)
                    .movie(Movie.builder().mno(mno).build())
                    .grade((int) (Math.random() * 5) + 1)
                    .text("이 영화에 대한 느낌은..." + i)
                    .build();
            reviewRepository.save(movieReview);
        });
    }

    @Test
    public void testGetMovieReviews(){
        Movie movie = Movie.builder().mno(100L).build();

        List<Review> result = reviewRepository.findByMovie(movie);

        result.forEach(movieReview ->{
            System.out.println(movieReview.getReviewnum());
            System.out.println("\t"+movieReview.getGrade());
            System.out.println("\t"+movieReview.getText());
            System.out.println("\t"+movieReview.getMember().getEmail());
            System.out.println("--------------------------------------");
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
