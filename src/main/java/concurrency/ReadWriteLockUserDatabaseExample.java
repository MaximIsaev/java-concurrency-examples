package concurrency;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockUserDatabaseExample {
    public static void main(String[] args) {

        List<String> users = new ArrayList<>();
        users.add("Dan");
        users.add("Max");
        users.add("Andrew");
        users.add("John");
        users.add("Qwerty");
        users.add("Qazwsx");
        users.add("Магомед");
        users.add("Денис");
        users.add("Пидор");
        users.add("Саша");

        UserDatabase userDatabase = new UserDatabase();

        Runnable writer = () -> {
            for (String name : users) {
                userDatabase.addUser(name, name + "@gmail.com");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Exception occurred while sleeping " + e.getMessage());
                }
            }
        };

        Runnable reader = () -> {
            for (String name : users) {
                System.out.println(Thread.currentThread().getName() + " got user email=" + userDatabase.getEmail(name));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Exception occurred while sleeping " + e.getMessage());
                }
            }
        };

        Thread writerThread = new Thread(writer, "Writer");
        Thread readerThread1 = new Thread(reader, "Reader 1");
        Thread readerThread2 = new Thread(reader, "Reader 2");
        Thread readerThread3 = new Thread(reader, "Reader 3");

        writerThread.start();
        readerThread1.start();
        readerThread2.start();
        readerThread3.start();

    }
}

class UserDatabase {
    private final Map<String, String> map = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public void addUser(String username, String email) {
        lock.writeLock().lock();
        try {
            map.put(username, email);
            System.out.println(Thread.currentThread().getName() + " added new user[name=" + username + ", email=" + email + "]");
        } catch (Exception e) {
            System.out.println("Exception occurred while adding new user " + username + ". " + e.getMessage());
        } finally {
            lock.writeLock().unlock();
        }
    }

    public String getEmail(String username) {
        lock.readLock().lock();
        try {
            return map.get(username);
        } catch (Exception e) {
            System.out.println("Exception occurred while getting email for user " + username + ". " + e.getMessage());
        } finally {
            lock.readLock().unlock();
        }
        return null;
    }

    public String removeUser(String username) {
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " removed the user[name=" + username + "]");
            return map.remove(username);
        } catch (Exception e) {
            System.out.println("Exception occurred while removing the user " + username + ". " + e.getMessage());
        } finally {
            lock.writeLock().unlock();
        }

        return null;
    }

    public Set<String> getAllUsers() {
        lock.readLock().lock();
        try {
            return map.keySet();
        } catch (Exception e) {
            System.out.println("Exception occurred while getting all users. " + e.getMessage());
        } finally {
            lock.readLock().unlock();
        }
        return null;
    }
}
