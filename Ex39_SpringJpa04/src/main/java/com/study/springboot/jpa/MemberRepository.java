package com.study.springboot.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> 
{
//	Page<Member> findByNameLike(String keyword, Pageable pageable);
	
	// 일반 JPQL쿼리, from뒤는 엔티티 명 (소문자로 할 시 에러)
    @Query("select m from JPAMEMBER03 m where m.name like :name1 order by m.id desc")
    List<Member> findMembers(@Param("name1") String name2);

    @Query("select m from JPAMEMBER03 m where m.name like :name1")
    List<Member> findMembers(@Param("name1") String name2, Sort sort);

    @Query("select m from JPAMEMBER03 m where m.name like :name1")
    Page<Member> findMembers(@Param("name1") String name2, Pageable pageable);
    
    // 일반 SQL쿼리 : 테이블명 등 대소문자 가리지 않는다.
 	@Query(value = "select * from jpamember03 where name like :name1 order by id desc",
 		   nativeQuery = true)
    List<Member> findMembersNative(@Param("name1") String name2);
}
