package com.aderenchuk.enter.repo;


import com.aderenchuk.enter.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDaoJPA extends JpaRepository<Client, Integer> {
}
