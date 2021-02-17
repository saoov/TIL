package org.zerock.guestbook.entity;


import lombok.*;

import javax.persistence.*;

/*
    1.Entity클래스와 Querydsl설정
    -> 기존의 엔티티 클래스와 달리 BaseEntity클래스를 상속한다.
    2. 엔티티 클래스는 가능하면 setter 관련 기능을 만들지 않는 것을 권장한다
    -> 엔티티 객체가 앱 내부에서 변경되면 jpa를 관리하는 쪽이 복잡해질 우려
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Guestbook extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gno;

    @Column(length = 100, nullable = false)

    private String title;

    @Column(length = 1500, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }
}
