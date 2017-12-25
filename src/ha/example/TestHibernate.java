package ha.example;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ha.example.entity.Student;

public class TestHibernate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.buildSessionFactory();
		Session session;
		try {
			// CREATE
//			Session session = factory.getCurrentSession();
//			Student tempStudent = new Student("Tam", "nguyen", "tamnguyen@gmail.com");
//			session.beginTransaction();
//			session.save(tempStudent);
//			session.getTransaction().commit();

			// READ
//			session = factory.getCurrentSession();
//			session.beginTransaction();
//			Student student = session.get(Student.class, tempStudent.getId());
//			System.out.println("GET complete: " + student);
//			session.getTransaction().commit();

			// READ all
			session = factory.getCurrentSession();
			session.beginTransaction();
			List<Student> theStudents = session.createQuery("from Student").getResultList();
			System.out.println("Test read all student");
			for (Student s : theStudents) {
				System.out.println(s);
			}
			session.getTransaction().commit();

			// READ all student lastname is Pham
			session = factory.getCurrentSession();
			session.beginTransaction();
			List<Student> studentsPham = session.createQuery("from Student s where s.lastName='Pham'").getResultList();
			System.out.println("\nTest read all student with last name is Pham");
			for (Student s : studentsPham) {
				System.out.println(s);
			}
			session.getTransaction().commit();
			
			// READ all student lastname is Pham or firstname is Nam
			session = factory.getCurrentSession();
			session.beginTransaction();
			List<Student> students = session.createQuery("from Student s where s.lastName='Pham' or s.firstName='Nam'").getResultList();
			System.out.println("\nTest read all student with last name is Pham or firstname is Nam");
			for (Student s : students) {
				System.out.println(s);
			}
			session.getTransaction().commit();
			
			// READ all student email like @mum.edu
			session = factory.getCurrentSession();
			session.beginTransaction();
			List<Student> muStudents = session.createQuery("from Student s where s.email like '%mum.edu'").getResultList();
			System.out.println("\nTest read all student at MUM");
			for (Student s : muStudents) {
				System.out.println(s);
			}
			session.getTransaction().commit();
			
			// update student with student ID = 1
			session = factory.getCurrentSession();
			session.beginTransaction();
			Student student2 = session.get(Student.class, 1);
			student2.setFirstName("Van HA");
			session.getTransaction().commit();
			
			//delete student with student id = 6
			session = factory.getCurrentSession();
			session.beginTransaction();
			Student student3 = session.get(Student.class, 6);
			session.delete(student3);
			session.getTransaction().commit();

		} finally {
			factory.close();
		}

	}

}
