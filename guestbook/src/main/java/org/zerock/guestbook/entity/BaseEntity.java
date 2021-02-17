package org.zerock.guestbook.entity;


import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/*
객체 입장에서 볼때 생성,수정날짜는 계속해서 나오므로 공통 매핑 정보를 부모클래스에 지정하고 속성만 상속 받아서 사용할 때
@MappedSuperclass가 선언된 클래스는 엔티티가 아니기 때문에 테이블과 매핑되지 않는다.
->자식클래스에 매핑 정보만 제공
->엔티티는 엔티티만 상속받을 수 있기 때문에 엔티티가 아닌 클래스를 상속받기 위해 @MappedSuperclass을 사용
->직접 생성해서 사용할 일이 없으므로 방어차원에서 추상 클래스로 작성을 권장->실수 방지
 */
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
abstract class BaseEntity {

    @CreatedDate
    @Column(name = "regdate", updatable = false) //updatable = false 속성으로 entity 객체를 DB에 반영시 regdate값은 변경되지 않음
    private LocalDateTime regDate;

    /*
    애노테이션 속성 차이로 update시 modDate만 테이블에서 변경됨
     */
    @LastModifiedDate
    @Column(name = "moddate")
    private LocalDateTime modDate;
}
