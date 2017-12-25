package ha.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ha.example.entity.Course;
import ha.example.entity.Instructor;
import ha.example.entity.InstructorDetail;

public class TestOneToManyMapping {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class)
				.addAnnotatedClass(Course.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try{
			//Add new courses
			session.beginTransaction();
			Instructor instructor = session.get(Instructor.class, 2);
			
			//Create some courses
			Course course1 = new Course("Algorithm");
			Course course2 = new Course("Web Programming");
			Course course3 = new Course("Software Engineer");
			
			instructor.add(course1);
			instructor.add(course2);
			instructor.add(course3);
			
			session.save(course1);
			session.save(course2);
			session.save(course3);
			
			session.getTransaction().commit();
			
			//Get courses of instructor
			session = factory.getCurrentSession();
			session.beginTransaction();
			Instructor instructor2 = session.get(Instructor.class, 2);
			System.out.println("Courses: " + instructor2.getCourses());
			
			//Delete a course
			Course course = session.get(Course.class, 3);
			session.delete(course);
			session.getTransaction().commit();
			
			//check course of instructor again
			session = factory.getCurrentSession();
			session.beginTransaction();
			Instructor instructor3 = session.get(Instructor.class, 2);
			System.out.println("Courses after delete: " + instructor3.getCourses());
			session.getTransaction().commit();
			
		}catch (Exception e) {
			e.printStackTrace();;
		} finally{
			session.close();
			factory.close();
		}
	}

}
