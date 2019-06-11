package net.tj.cp.materiaprima.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.tj.cp.produto.jpa.ProdutoJpa;

@Builder
@Data
@AllArgsConstructor
@Entity
@Table(name = "MATERIA_PRIMA")
public class MateriaPrimaJpa implements Serializable {

	public MateriaPrimaJpa(){}

	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column(name = "NOME", nullable = false, unique = false)
	private String nome;

	@NotNull
	@Column(name = "QUANTIDADE", nullable = false, unique = false)
	private Integer quantidade;

	@ManyToOne
	private ProdutoJpa produto;

	@Override
	public String toString() {
		return getId() + " - " + getNome() + " - Quantidade: " + getQuantidade();
	}
}
