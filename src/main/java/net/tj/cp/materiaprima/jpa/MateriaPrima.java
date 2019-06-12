package net.tj.cp.materiaprima.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "MATERIA_PRIMA")

public class MateriaPrima implements Serializable {

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

	@Override
	public String toString() {
		return getId() + " - " + getNome() + " - Quantidade: " + getQuantidade();
	}
}
