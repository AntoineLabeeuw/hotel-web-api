package dev.hotel.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.hotel.entite.Client;

public interface ClientRepository extends JpaRepository<Client, UUID> {
	@Query("SELECT c FROM Client c WHERE LOWER(c.nom)=LOWER(?1)")
	List<Client> findByNom(String nom);
}
