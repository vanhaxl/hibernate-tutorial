package ha.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ha.example.entity.Course;
import ha.example.entity.Instructor;
import ha.example.entity.InstructorDetail;
import ha.example.entity.Review;

//Test one course has many reviews
// It is uni mapping MANY to ONE

public class TestUNIManyToOneMapping {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Course.class)
				.addAnnotatedClass(Review.class).addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class).buildSessionFactory();
		Session session = factory.getCurrentSession();

		try {
			session.beginTransaction();
			Course course = session.get(Course.class, 2);

			Review a = new Review("Good");
			Review b = new Review("So so");
			Review c = new Review("Bad");
			course.addReview(a);
			course.addReview(b);
			course.addReview(c);
			session.save(a);
			session.save(b);
			session.save(c);

			session.getTransaction().commit();

			//delete course algorithm, then it will delete all its reviews
			session = factory.getCurrentSession();
			session.beginTransaction();
			Course course2 = session.get(Course.class, 2);
			session.delete(course2);
			session.getTransaction().commit();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
	}

}
