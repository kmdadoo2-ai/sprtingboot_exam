package exam4;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class Test02_TypedQuery
{
	public static void main(String[] args)
	{
		// hibernate.hbm2ddl.auto의 value : none
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JpaEx01");
        EntityManager em = emf.createEntityManager();

        try 
        {
            em.getTransaction().begin();
            
            TypedQuery<Member4> query =
                    em.createQuery(
                            "select m from Member4 m order by m.name",
                            Member4.class);
            List<Member4> result = query.getResultList();
            
            em.getTransaction().commit();

            if (result.isEmpty()) 
            {
                System.out.println("사용자가 없습니다.");
            }
            else 
            {
            	result.forEach(user ->
                        System.out.printf(
                                "| %s | %s | %tY-%<tm-%<td |\n",
                                user.getEmail(), user.getName(), user.getCreateDate()));
            	
            	// 다른 표현 : 아래는 리스트를 순회하는 다양한 다른 표현 방법들 (참고용 주석) ---
            	// 방법 1: 일반 for 문을 사용한 순회
//            	for (int i=0; i <result.size(); i++)
//            	{
//            		System.out.println( result.get(i) );
//            	}
            	
            	// 방법 2: 향상된 for 문(Enhanced for-loop)을 사용한 순회
//            	for (Member4 member : result)
//            	{
//            		System.out.println( member );
//            	}
            	
            	// 방법 3: Iterator(반복자)를 사용한 순회
//              for(Iterator<Member4> itr = result.iterator(); itr.hasNext(); )
//                    System.out.print(itr.next());
            }
        
        } catch(Exception e) {
            em.getTransaction().rollback();
            throw e;
        }

        em.close();
        emf.close();
	}
}

