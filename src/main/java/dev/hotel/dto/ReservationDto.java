package dev.hotel.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * classe qui permet de récupérer les informations d'une réservation a partir
 * d'un POST
 * 
 * @author antoinelabeeuw
 *
 */
public class ReservationDto {
	/** dateDebut */
	@NotNull
	private LocalDate dateDebut;
	/** dateFin */
	@NotNull
	private LocalDate dateFin;
	/** UuidClient */
	@NotNull
	@JsonProperty("clientId")
	private UUID uuidClient;
	/** chambres */
	@NotNull
	@JsonProperty("chambres")
	private List<UUID> uuidChambres;

	/**
	 * Constructeur
	 * 
	 */
	public ReservationDto() {
		super();
	}

	/** Getter
	 * @return the dateDebut
	 */
	public LocalDate getDateDebut() {
		return dateDebut;
	}

	/** Setter
	 * @param dateDebut the dateDebut to set
	 */
	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	/** Getter
	 * @return the dateFin
	 */
	public LocalDate getDateFin() {
		return dateFin;
	}

	/** Setter
	 * @param dateFin the dateFin to set
	 */
	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}

	/** Getter
	 * @return the uuidClient
	 */
	public UUID getUuidClient() {
		return uuidClient;
	}

	/** Setter
	 * @param uuidClient the uuidClient to set
	 */
	public void setUuidClient(UUID uuidClient) {
		this.uuidClient = uuidClient;
	}

	/** Getter
	 * @return the uuidChambres
	 */
	public List<UUID> getUuidChambres() {
		return uuidChambres;
	}

	/** Setter
	 * @param uuidChambres the uuidChambres to set
	 */
	public void setUuidChambres(List<UUID> uuidChambres) {
		this.uuidChambres = uuidChambres;
	}

	
}
