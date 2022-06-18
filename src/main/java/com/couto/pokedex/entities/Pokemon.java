package com.couto.pokedex.entities;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("TB_POKEMON")
public class Pokemon {
	
	@Id
	private Long id;
	private String name;
	private String type;

}
