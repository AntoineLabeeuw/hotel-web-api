package dev.hotel.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * classe ClientDto pour la récupération des infos d'un POST /clients
 * 
 * @author antoinelabeeuw
 *
 */
public class ClientDto {
	/** nom */
	@NotNull
	@NotBlank
	@JsonProperty("nom")
	private String nom;
	/** prenoms */
	@NotNull
	@NotBlank
	@JsonProperty("prenoms")
	private String prenoms;

	/**
	 * Getter
	 * 
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Setter
	 * 
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Getter
	 * 
	 * @return the prenoms
	 */
	public String getPrenoms() {
		return prenoms;
	}

	/**
	 * Setter
	 * 
	 * @param prenoms the prenoms to set
	 */
	public void setPrenoms(String prenoms) {
		this.prenoms = prenoms;
	}

	/** Constructeur
	 * 
	 */
	public ClientDto() {
	}
	

}
