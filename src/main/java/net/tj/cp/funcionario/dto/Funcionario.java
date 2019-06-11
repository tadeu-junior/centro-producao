package net.tj.cp.funcionario.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class Funcionario {

	public Funcionario(){}

	private Long id;
	private String nome;
	private Integer jornada;
}
