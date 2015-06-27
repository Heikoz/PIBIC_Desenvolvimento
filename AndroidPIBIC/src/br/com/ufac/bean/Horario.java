package br.com.ufac.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Horario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> tempo;
	private List<String> segunda;
	private List<String> terca;
	private List<String> quarta;
	private List<String> quinta;
	private List<String> sexta;
	private List<String> sabado;
	
	public Horario(){
		tempo = new ArrayList<String>();
		segunda = new ArrayList<String>();
		terca = new ArrayList<String>();
		quarta = new ArrayList<String>();
		quinta = new ArrayList<String>();
		sexta = new ArrayList<String>();
		sabado = new ArrayList<String>();
	}
	
	public List<String> getTempo() {
		return tempo;
	}
	
	public void setTempo(List<String> tempo) {
		this.tempo = tempo;
	}
	
	public List<String> getSegunda() {
		return segunda;
	}
	
	public void setSegunda(List<String> segunda) {
		this.segunda = segunda;
	}
	
	public List<String> getTerca() {
		return terca;
	}
	
	public void setTerca(List<String> terca) {
		this.terca = terca;
	}
	
	public List<String> getQuarta() {
		return quarta;
	}
	
	public void setQuarta(List<String> quarta) {
		this.quarta = quarta;
	}
	
	public List<String> getQuinta() {
		return quinta;
	}
	
	public void setQuinta(List<String> quinta) {
		this.quinta = quinta;
	}
	
	public List<String> getSexta() {
		return sexta;
	}
	
	public void setSexta(List<String> sexta) {
		this.sexta = sexta;
	}
	
	public List<String> getSabado() {
		return sabado;
	}
	
	public void setSabado(List<String> sabado) {
		this.sabado = sabado;
	}

	@Override
	public String toString() {
		return "Horarios [tempo=" + tempo + ", segunda=" + segunda + ", terca="
				+ terca + ", quarta=" + quarta + ", quinta=" + quinta
				+ ", sexta=" + sexta + ", sabado=" + sabado + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((quarta == null) ? 0 : quarta.hashCode());
		result = prime * result + ((quinta == null) ? 0 : quinta.hashCode());
		result = prime * result + ((sabado == null) ? 0 : sabado.hashCode());
		result = prime * result + ((segunda == null) ? 0 : segunda.hashCode());
		result = prime * result + ((sexta == null) ? 0 : sexta.hashCode());
		result = prime * result + ((tempo == null) ? 0 : tempo.hashCode());
		result = prime * result + ((terca == null) ? 0 : terca.hashCode());
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
		Horario other = (Horario) obj;
		if (quarta == null) {
			if (other.quarta != null)
				return false;
		} else if (!quarta.equals(other.quarta))
			return false;
		if (quinta == null) {
			if (other.quinta != null)
				return false;
		} else if (!quinta.equals(other.quinta))
			return false;
		if (sabado == null) {
			if (other.sabado != null)
				return false;
		} else if (!sabado.equals(other.sabado))
			return false;
		if (segunda == null) {
			if (other.segunda != null)
				return false;
		} else if (!segunda.equals(other.segunda))
			return false;
		if (sexta == null) {
			if (other.sexta != null)
				return false;
		} else if (!sexta.equals(other.sexta))
			return false;
		if (tempo == null) {
			if (other.tempo != null)
				return false;
		} else if (!tempo.equals(other.tempo))
			return false;
		if (terca == null) {
			if (other.terca != null)
				return false;
		} else if (!terca.equals(other.terca))
			return false;
		return true;
	}

}
