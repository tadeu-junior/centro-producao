package net.tj.cp.produto.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.tj.cp.funcionario.dto.Funcionario;
import net.tj.cp.materiaprima.dto.MateriaPrima;

@Builder
@Data
@AllArgsConstructor
public class Produto {

	public Produto(){}

	private Long id;
	private String nome;
	private Funcionario funcionario;
	private List<MateriaPrima> listaMateriaPrima;
}
