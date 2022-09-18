package org.zerock.ex2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex2.entity.Memo;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * packageName : org.zerock.ex2.repository
 * className : MemoRepositoryTests
 * user : jwlee
 * date : 2022/09/17
 */
@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void 클래스테스트(){
        System.out.println(memoRepository.getClass().getName());
    }

    @Test
    public void 등록100개테스트(){
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..." + i).build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void 조회테스트() {

        Memo memo = Memo.builder().memoText("Sample..." + 1).build();
        memoRepository.save(memo);

        //데이터베이스에 존재하는 mno
        Long mno = 1L;

        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("====================================");

        if(result.isPresent()) {
            Memo findMemo = result.get();
            System.out.println(findMemo);
        }
    }

    @Transactional
    @Test
    public void 조회테스트2() {

        Memo memo = Memo.builder().memoText("Sample..." + 1).build();
        memoRepository.save(memo);

        //데이터베이스에 존재하는 mno
        Long mno = 1L;

        Memo findMemo = memoRepository.getOne(mno);
        System.out.println("====================================");
        System.out.println(findMemo);
    }

    @Test
    public void 업데이트테스트(){

        Memo memo = Memo.builder().memoText("Sample..." + 1).build();
        memoRepository.save(memo);

        System.out.println(memo);
        memo.changeMemoText("Update Text");
        System.out.println(memoRepository.save(memo));
    }

    @Test
    public void 삭제테스트() {

        Memo memo = Memo.builder().memoText("Sample..." + 1).build();
        memoRepository.save(memo);

        memoRepository.deleteById(memo.getMno());
    }

    @Test
    public void 기본페이지테스트() {

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..." + i).build();
            memoRepository.save(memo);
        });

        // 1페이지 10개
        Pageable pageable = PageRequest.of(0, 10);
        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println(result);
        System.out.println("====================================");

        System.out.println("Total Pages: " + result.getTotalPages());   // 총 몇페이지
        System.out.println("Total Count: " + result.getTotalElements());    // 전체 개수
        System.out.println("Page Number: " + result.getNumber());       // 현재 페이지 번호
        System.out.println("Page Size: " + result.getSize());           // 페이지당 데이터 개수
        System.out.println("has next page?: " + result.hasNext());      // 다음 페이지 존재 여부
        System.out.println("first page?: " + result.isFirst());         // 시작 페이지(0) 여부

        System.out.println("====================================");
        for (Memo memo : result.getContent()) {
            System.out.println(memo);
        }
    }

    @Test
    public void 정렬테스트() {

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..." + i).build();
            memoRepository.save(memo);
        });

//        Sort sort1 = Sort.by("mno").descending();
//
//        Pageable pageable = PageRequest.of(0, 10, sort1);
//        Page<Memo> result = memoRepository.findAll(pageable);
//        System.out.println("====================================");
//        for (Memo memo : result.getContent()) {
//            System.out.println(memo);
//        }

        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2);

        Pageable pageable = PageRequest.of(0, 10, sortAll);
        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println("====================================");
        for (Memo memo : result.getContent()) {
            System.out.println(memo);
        }
    }

    @Test
    public void 쿼리메서드테스트() {

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..." + i).build();
            memoRepository.save(memo);
        });

        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);

        System.out.println("====================================");
        for (Memo memo : list) {
            System.out.println(memo);
        }
    }

    @Test
    public void 쿼리메서드페이징처리테스트() {

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..." + i).build();
            memoRepository.save(memo);
        });

        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
        Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageable);
        System.out.println("====================================");
        result.get().forEach(memo -> System.out.println(memo));
    }

    @Commit             // 테스트 코드의 deleteBy 는 기본적으로 롤백 처리되어서 결과를 반영하지 않음
    @Transactional      // 조회와 삭제 작업을 같이하기때문에 트랜잭션 어노테이션이 필요하다.
    @Test
    public void testDeleteQueryMethods() {

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..." + i).build();
            memoRepository.save(memo);
        });

        memoRepository.deleteMemoByMnoLessThan(10L);
    }
}
