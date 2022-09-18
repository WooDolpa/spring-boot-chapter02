package org.zerock.ex2.entity;

import lombok.*;

import javax.persistence.*;
import static javax.persistence.GenerationType.*;
/**
 * packageName : org.zerock.ex2.entity
 * className : Memo
 * user : jwlee
 * date : 2022/09/17
 */

@Entity
@Table(name = "tb1_memo")
@Getter
@ToString
@NoArgsConstructor
public class Memo {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long mno;

    @Column(length = 200, nullable = false)
    private String memoText;

    @Builder
    public Memo(String memoText) {
        this.memoText = memoText;
    }

    public void changeMemoText(String memoText) {
        this.memoText = memoText;
    }

}
