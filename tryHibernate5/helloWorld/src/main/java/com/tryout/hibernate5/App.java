package com.tryout.hibernate5;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import java.util.List;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        System.out.println("Hello World!");

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try (SessionFactory factory =
                     new MetadataSources(registry).buildMetadata().buildSessionFactory()) {

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

            Message find1;
            try (Session session = factory.openSession()) {
                find1 = session.find(Message.class, message.getId());
            }

            Message find2;
            try (Session session = factory.openSession()) {
                find2 = session.find(Message.class, message.getId());
            }

            try (Session session = factory.openSession()) {
                session.getTransaction().begin();
                Message message1 = session.find(Message.class, message.getId());
                message1.setVersion(message1.getVersion() + 1);
                try (Session session2 = factory.openSession()) {
                    session2.getTransaction().begin();
                    Message message2 = session2.find(Message.class, message.getId());
                    message2.setVersion(message2.getVersion() + 1);
                    session2.getTransaction().commit();
                }

                session.getTransaction().commit();
            }

            try (Session session = factory.openSession()) {
                System.out.println(session.find(Message.class, message.getId()));
            }

            System.out.println(find1 == find2);
        }
    }
}
