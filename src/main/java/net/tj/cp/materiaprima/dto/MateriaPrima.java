package net.tj.cp.materiaprima.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class MateriaPrima {

	public MateriaPrima(){}

	private Long id;
	private String nome;
	private Integer quantidade;

}
