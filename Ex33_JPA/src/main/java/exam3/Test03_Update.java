package exam3;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Test03_Update
{
	public static void main(String[] args)
	{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JpaEx01");
        EntityManager em = emf.createEntityManager();

        try 
        {
            em.getTransaction().begin();
            
            Member3 user = em.find(Member3.class, "test@test.com");
            if (user == null) 
            {
            	System.out.println("존재하지 않습니다.");
                em.getTransaction().rollback();
            	return;
            }
            user.changeName("전우치1");	// 자바 객체를 통해 영속 컨텍스트의 값을 변경 
            
            em.getTransaction().commit(); 	// sql문을 실행해 db에 값의 변경 반영
            System.out.println("이름을 변경했습니다.");
        } catch(Exception e) {
            em.getTransaction().rollback();
            throw e;
        }

        em.close();
        emf.close();
	}
}

