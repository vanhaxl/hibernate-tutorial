package ha.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ha.example.entity.Instructor;
import ha.example.entity.InstructorDetail;

public class TestOneToOneMapping {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class).buildSessionFactory();
		Instructor tempInstructor = new Instructor("Ha", "Nguyen", "vhnguyen@mum.edu");
		InstructorDetail tempInstructorDetail = new InstructorDetail("http://youtube.com/vhnguyen", "Love to code");
		tempInstructor.setInstructorDetail(tempInstructorDetail);

		Session session = factory.getCurrentSession();
		try {
			// Add
			 session = factory.getCurrentSession();
			 session.beginTransaction();
			 session.save(tempInstructor);
			 session.getTransaction().commit();

			// Cascade Delete. Delete Instructor, then delete InstructorDetail
			session = factory.getCurrentSession();
			session.beginTransaction();
			Instructor tmpInstructor = session.get(Instructor.class, 1);
			if (tmpInstructor != null) {
				System.out.println("Deleting: " + 1);
				session.delete(tmpInstructor);
			}
			session.getTransaction().commit();

			// From InstructorDetail, get Instructor
			session = factory.getCurrentSession();
			session.beginTransaction();
			InstructorDetail instructorDetail = session.get(InstructorDetail.class, 3);
			System.out.println("Instructor: " + instructorDetail.getInstructor().getFirstName());
			System.out.println("Instructor Detail: " + instructorDetail);
			session.getTransaction().commit();

			// Delete only Instructor Detail
			// Modify Instructor Detail cascade type, all, except for Remove
			session = factory.getCurrentSession();
			session.beginTransaction();
			InstructorDetail instructorDetail2 = session.get(InstructorDetail.class, 2);
			//need to break bi-directional link
			instructorDetail2.getInstructor().setInstructorDetail(null);
			session.delete(instructorDetail2);
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
	}

}
