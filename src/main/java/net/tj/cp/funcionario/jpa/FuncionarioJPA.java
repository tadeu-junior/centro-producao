package net.tj.cp.funcionario.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.tj.cp.funcionario.dto.Funcionario;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Builder
@Data
@AllArgsConstructor
@Entity
@Table(name = "FUNCIONARIO")
public class FuncionarioJPA implements Serializable {

	public FuncionarioJPA(){}

	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column(name = "NOME", nullable = false, unique = false)
	private String nome;

	@NotNull
	@Column(name = "JORNADA", nullable = false, unique = false)
	private Integer jornada;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

}
