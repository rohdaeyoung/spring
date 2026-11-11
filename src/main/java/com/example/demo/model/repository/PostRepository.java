package com.example.demo.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.model.domain.Member;
import com.example.demo.model.domain.Post;

@Repository // 게시글 리포지토리 : save, findAll, findById, delete 는 이미 있다
public interface PostRepository extends JpaRepository<Post, Long> {
    // [9주차 연습문제 ② → 10주차 연습문제 ①] 내가 쓴 글 : 마지막에 Pageable 만 더하면 Page 가 된다
    // SELECT … FROM posts p JOIN member m … WHERE m.username = ? ORDER BY … LIMIT ?
    Page<Post> findByAuthor_Username(String username, Pageable pageable);

    // [10주차] 단계 2 : 제목 또는 내용에 검색어가 들어간 글 (JPQL)
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
    Page<Post> search(@Param("keyword") String keyword, Pageable pageable);

    // [10주차 연습문제 ②] 작성자 이름으로 검색 (글 → 작성자 → 이름, posts JOIN member)
    @Query("SELECT p FROM Post p WHERE p.author.name LIKE %:name%")
    Page<Post> searchByWriter(@Param("name") String name, Pageable pageable);

    boolean existsByAuthor(Member author); // [10주차] 단계 6 : 이 회원이 쓴 글이 있는지
}
