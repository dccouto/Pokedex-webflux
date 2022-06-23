package com.couto.pokedex.entities;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("TB_POKEMON")
public class Pokemon {
	
	@Id
	private Long id;
	@NotEmpty(message = "The name of Pokemon cannot be empty")
	private String name;
	private String type;

}
