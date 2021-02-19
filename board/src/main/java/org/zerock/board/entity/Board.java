package org.zerock.board.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer") //연관 관계가 존재할 경우 writer변수로 선언된 Member객체 출력해야하고 이 때 Member객체의 toString()이 호출되기 위해 DB연결이 필요로 하게됨 ->지연로딩시 반드시 exclude지정
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY) //명시적 지연 로딩 지정 -> 지연로딩 미지정시 testRead1()이 자동 left outer join됨
    private Member writer;

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }
}
