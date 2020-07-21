package dev.hotel.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import dev.hotel.entite.Client;
import dev.hotel.repository.ClientRepository;

@Service
public class ClientService {
	/** repo */
	private ClientRepository repo;

	/**
	 * Constructeur
	 * 
	 * @param repo
	 */
	public ClientService(ClientRepository repo) {
		super();
		this.repo = repo;
	}

	public List<Client> findAll(Integer start, Integer size) {
		return repo.findAll(PageRequest.of(start, size)).toList();
	}

	public Optional<Client> findByUuid(UUID uuid) {
		return repo.findById(uuid);
	}

	@Transactional
	public Client CreerClient(String nom, String prenom) {
		Client cl = new Client();
		cl.setNom(nom);
		cl.setPrenoms(prenom);
		return this.repo.save(cl);

	}
}
