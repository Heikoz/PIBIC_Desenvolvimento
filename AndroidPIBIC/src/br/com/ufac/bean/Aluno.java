package br.com.ufac.bean;

import java.io.Serializable;


/**
 * Classe responsável por conter os atributos do Objeto Cliente
 *
 * @author Vitor Lucas <lukassgm@gmail.com.br>
 * @since 23/06/2015
 * @version 1.0
 */

public final class Aluno implements Serializable{

	//[0]Nome João Josino Sobrinho Neto, Nome da Mãe Antonia Sebastiana de Oliveira, Nome da Pai Manoel de Melo Sobrinho
	//[3]Nascimento 09/05/1995, Sexo Masculino, Etnia Não Declarada, Deficiência Sem Deficiência, Tipo Sanguíneo não consta, 
	//[8]Fator RH não consta, Estado Civil Solteiro(a), Página Pessoal não consta, Nacionalidade Brasil, Estado Acre, 
	//[13]Naturalidade Rio Branco]
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome, nomeDaMae, nomeDoPai, nascimento, sexo, deficiencia, nacionalidade, estado, naturalidade;
	private String cpf, rg;
	
	public String getNomeDaMae() {
		return nomeDaMae;
	}
	
	public void setNomeDaMae(String nomeDaMae) {
		this.nomeDaMae = nomeDaMae;
	}
	
	public String getNomeDoPai() {
		return nomeDoPai;
	}
	
	public void setNomeDoPai(String nomeDoPai) {
		this.nomeDoPai = nomeDoPai;
	}
	
	public String getNascimento() {
		return nascimento;
	}
	
	public void setNascimento(String nascimento) {
		this.nascimento = nascimento;
	}
	
	public String getSexo() {
		return sexo;
	}
	
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public String getDeficiencia() {
		return deficiencia;
	}
	
	public void setDeficiencia(String deficiencia) {
		this.deficiencia = deficiencia;
	}
	
	public String getNacionalidade() {
		return nacionalidade;
	}
	
	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getNaturalidade() {
		return naturalidade;
	}
	
	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}
	
	public String getRg() {
		return rg;
	}
	
	public void setRg(String rg) {
		this.rg = rg;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getFirstName(){
		String[] firstName = nome.split(" ");
		return firstName[0];
	}

	@Override
	public String toString() {
		return "Aluno [nome=" + nome + ", nomeDaMae=" + nomeDaMae
				+ ", nomeDoPai=" + nomeDoPai + ", nascimento=" + nascimento
				+ ", sexo=" + sexo + ", deficiencia=" + deficiencia
				+ ", nacionalidade=" + nacionalidade + ", estado=" + estado
				+ ", naturalidade=" + naturalidade + ", cpf=" + cpf + ", rg="
				+ rg + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result
				+ ((deficiencia == null) ? 0 : deficiencia.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result
				+ ((nacionalidade == null) ? 0 : nacionalidade.hashCode());
		result = prime * result
				+ ((nascimento == null) ? 0 : nascimento.hashCode());
		result = prime * result
				+ ((naturalidade == null) ? 0 : naturalidade.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result
				+ ((nomeDaMae == null) ? 0 : nomeDaMae.hashCode());
		result = prime * result
				+ ((nomeDoPai == null) ? 0 : nomeDoPai.hashCode());
		result = prime * result + ((rg == null) ? 0 : rg.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aluno other = (Aluno) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (deficiencia == null) {
			if (other.deficiencia != null)
				return false;
		} else if (!deficiencia.equals(other.deficiencia))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (nacionalidade == null) {
			if (other.nacionalidade != null)
				return false;
		} else if (!nacionalidade.equals(other.nacionalidade))
			return false;
		if (nascimento == null) {
			if (other.nascimento != null)
				return false;
		} else if (!nascimento.equals(other.nascimento))
			return false;
		if (naturalidade == null) {
			if (other.naturalidade != null)
				return false;
		} else if (!naturalidade.equals(other.naturalidade))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (nomeDaMae == null) {
			if (other.nomeDaMae != null)
				return false;
		} else if (!nomeDaMae.equals(other.nomeDaMae))
			return false;
		if (nomeDoPai == null) {
			if (other.nomeDoPai != null)
				return false;
		} else if (!nomeDoPai.equals(other.nomeDoPai))
			return false;
		if (rg == null) {
			if (other.rg != null)
				return false;
		} else if (!rg.equals(other.rg))
			return false;
		if (sexo == null) {
			if (other.sexo != null)
				return false;
		} else if (!sexo.equals(other.sexo))
			return false;
		return true;
	}

}
