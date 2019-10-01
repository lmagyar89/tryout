package com.tryout.hibernate5;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import java.util.List;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
		
		StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        SessionFactory factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		
		Message message = new Message("Hello, world");
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(message);
            tx.commit();
        }
		
		try (Session session = factory.openSession()) {
            List<Message> list = session.createQuery("from Message", Message.class).list();

            for (Message m : list) {
                System.out.println(m);
            }
        }
		
		factory.close();
    }
}
