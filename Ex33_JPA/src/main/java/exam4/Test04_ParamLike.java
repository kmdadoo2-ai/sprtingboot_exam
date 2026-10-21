package exam4;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class Test04_ParamLike
{
	public static void main(String[] args)
	{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JpaEx01");
        EntityManager em = emf.createEntityManager();

        try 
        {
            em.getTransaction().begin();
            
            TypedQuery<Member4> query =
                    em.createQuery(
                            "select m from Member4 m "
                          + " where m.email like :email "
                          + " order by m.name",
                            Member4.class)
                    .setParameter("email", "%test.com%");
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
            }
        
        } catch(Exception e) {
            em.getTransaction().rollback();
            throw e;
        }

        em.close();
        emf.close();
	}
}

