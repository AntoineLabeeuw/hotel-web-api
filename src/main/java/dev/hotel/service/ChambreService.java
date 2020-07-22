package dev.hotel.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.hotel.entite.Chambre;
import dev.hotel.repository.ChambreRepository;

@Service
public class ChambreService {
	private ChambreRepository chaRepo;

	/** Constructeur
	 * @param chaRepo
	 */
	public ChambreService(ChambreRepository chaRepo) {
		super();
		this.chaRepo = chaRepo;
	}
	
	public Optional<Chambre> findById(UUID chambreId) {
		return chaRepo.findById(chambreId);
	}
	
	public List<Chambre> findListeChambre(List<UUID> chambresIds) {
		return chaRepo.findAllById(chambresIds);
		
	}
}
