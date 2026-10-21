package exam3;

import java.time.LocalDate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Test01_Insert
{
	public static void main(String[] args)
	{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JpaEx01");
        EntityManager em = emf.createEntityManager();

        try 
        {
            em.getTransaction().begin();

            Member3 user = new Member3("test@test.com", "홍길동", LocalDate.now());
            System.out.println(111);		// SQL문 어디서 출력되는지 확인
            em.persist(user);				// 영속 컨텍스트에 반영
            System.out.println(222);
            
            em.getTransaction().commit();	// 실제 sql문 처리
            System.out.println(333);
            System.out.println("가입 요청을 처리했습니다.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }

        em.close();
        emf.close();
	}
}

