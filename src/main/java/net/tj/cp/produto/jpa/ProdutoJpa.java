package net.tj.cp.produto.jpa;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.tj.cp.funcionario.jpa.FuncionarioJPA;
import net.tj.cp.materiaprima.jpa.MateriaPrimaJpa;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Builder
@Data
@AllArgsConstructor
@Entity
@Table(name = "PRODUTO")
public class ProdutoJpa implements Serializable {

	public ProdutoJpa() {}

	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column(name = "NOME", nullable = false, unique = false)
	private String nome;

	@OneToMany(mappedBy = "produto")
	private Set<MateriaPrimaJpa> materiaPrimaJpa = new HashSet<>();

	@OneToOne
	private FuncionarioJPA funcionario;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

}
