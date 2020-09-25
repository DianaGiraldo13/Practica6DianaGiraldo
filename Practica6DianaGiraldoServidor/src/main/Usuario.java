package main;

public class Usuario {
	
	private String usuario;
	private String contra;
	
	
	public Usuario(String usuario, String contra) {
		super();
		this.usuario = usuario;
		this.contra = contra;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getContra() {
		return contra;
	}
	public void setContra(String contra) {
		this.contra = contra;
	}
	
	

}
