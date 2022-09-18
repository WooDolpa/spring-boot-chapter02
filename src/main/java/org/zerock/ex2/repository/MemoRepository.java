package org.zerock.ex2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.ex2.entity.Memo;

import java.util.List;

/**
 * packageName : org.zerock.ex2.repository
 * className : MemoRepository
 * user : jwlee
 * date : 2022/09/17
 */
public interface MemoRepository extends JpaRepository<Memo, Long> {

    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);
    Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);

    void deleteMemoByMnoLessThan(Long num);


}


