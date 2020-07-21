package dev.hotel.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.hotel.entite.Client;

public interface ClientRepository extends JpaRepository<Client, UUID> {
	@Query("SELECT c FROM Client c WHERE LOWER(c.nom)=LOWER(?1) AND LOWER(c.prenoms)=LOWER(?2)")
	public Optional<Client> findByNomPrenoms(String nom, String prenoms);
}
