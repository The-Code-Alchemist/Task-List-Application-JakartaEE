package com.codealchemists.tasklistapplication.repository;

import com.codealchemists.tasklistapplication.model.Task;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class TaskRepository {
    private static final Logger log = LoggerFactory.getLogger(TaskRepository.class);

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("taskListPU");

    public void save(Task task) {
        log.debug("Saving task: {}", task.getTitle());
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            if (task.getId() == null) {
                em.persist(task);
            } else {
                em.merge(task);
            }
            em.getTransaction().commit();
            log.debug("Task saved successfully.");
        } catch (Exception e) {
            log.error("Error saving task: {}", task.getTitle(), e);
            throw e;
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
        log.debug("Finding all tasks for user ID: {}", userId);
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
                log.debug("Task {} removed from database", id);
            } else {
                log.warn("Attempted to delete non-existent task: {}", id);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            log.error("Error deleting task: {}", id, e);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}