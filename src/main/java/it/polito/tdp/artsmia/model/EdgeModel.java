package it.polito.tdp.artsmia.model;

import java.util.Objects;

public class EdgeModel {
	ArtObject source;
	ArtObject target;
	Integer peso;
	public EdgeModel(ArtObject source, ArtObject target, Integer peso) {
		super();
		this.source = source;
		this.target = target;
		this.peso = peso;
	}
	public ArtObject getSource() {
		return source;
	}
	public void setSource(ArtObject source) {
		this.source = source;
	}
	public ArtObject getTarget() {
		return target;
	}
	public void setTarget(ArtObject target) {
		this.target = target;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	@Override
	public int hashCode() {
		return Objects.hash(peso, source, target);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EdgeModel other = (EdgeModel) obj;
		return Objects.equals(peso, other.peso) && Objects.equals(source, other.source)
				&& Objects.equals(target, other.target);
	}
	@Override
	public String toString() {
		return "edgeModel [source=" + source + ", target=" + target + ", peso=" + peso + "]";
	}
	
	
}
