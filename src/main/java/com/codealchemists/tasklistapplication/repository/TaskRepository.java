package com.codealchemists.tasklistapplication.repository;

import com.codealchemists.tasklistapplication.model.Task;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.UUID;

public class TaskRepository {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("taskListPU");

    public void save(Task task) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            if (task.getId() == null) {
                em.persist(task);
            } else {
                em.merge(task);
            }
            em.getTransaction().commit();
        }
    }

    public Task findById(UUID id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Task.class, id);
        } finally {
            em.close();
        }
    }

    public List<Task> findAllByUser(UUID userId) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT t FROM Task t WHERE t.user.id = :userId", Task.class)
                    .setParameter("userId", userId)
                    .getResultList();
        }
    }

    public void delete(UUID id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Task task = em.find(Task.class, id);
            if (task != null) {
                em.remove(task);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}