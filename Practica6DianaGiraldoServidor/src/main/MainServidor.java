package main;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.Gson;

import processing.core.PApplet;

public class MainServidor extends PApplet{
	
	
	private Socket socket;
	private BufferedReader reader;
	private BufferedWriter writer;
	private Usuario[] usuarios;
	private String texto;

	public static void main(String[] args) {
		PApplet.main("main.MainServidor");
		
		
	
	}
	
	public boolean verififyUser(String usuario,String contra) {
		boolean stop=false;
		boolean response=false;
		for(int i=0;i<usuarios.length && !stop;i++) {
			Usuario u = usuarios[i];
			if(u.getUsuario().equals(usuario)) {
				stop=true;
				if(u.getContra().equals(contra)) {
					response=true;
				}
			}
		}
		return response;
	}
	
	public void settings() {
		size(500,500);
	}

	public void setup() {
		usuarios = new Usuario[2];
		usuarios[0]= new Usuario("Juan", "Hola123");
		usuarios[1] = new Usuario("Diana", "Adios456");
		texto="Ingrese su usuario y contraseña en su celular";
		initServer();
		
	}
	
	
	public void draw() {
		background(255);
		fill(0);
		text(texto,150,250);
		
		
		
	}
	
	public void initServer() {
		
		new Thread(
				
				()->{
					
				try {
					//1.Esperar una conexion
					ServerSocket server = new ServerSocket(5000);
					System.out.println("Esperando conexion");
					socket = server.accept();
					
					//3.Cliente y Server Conectados
					System.out.println("Cliente conectado");
					
					InputStream is = socket.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					reader = new BufferedReader(isr);
					
					OutputStream os = socket.getOutputStream();
					OutputStreamWriter osw = new OutputStreamWriter(os);
					writer = new BufferedWriter(osw);
					
					
					while(true) {
						System.out.println("Reciviendo");
						String datos = reader.readLine();
						System.out.println(datos);
						Gson g = new Gson();
						Usuario u = g.fromJson(datos, Usuario.class);
						if(verififyUser(u.getUsuario(),u.getContra())) {
							sendMessage("Password correcta");
							texto="Bienvenido";
						}
						else {
							sendMessage("Password incorrecta");
						}
						
					}
					
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				}
				
				).start();
	}
	
	public void sendMessage(String msg) {
		
		new Thread(
				()->{
				
					try {
						
						writer.write(msg+"\n");
						writer.flush();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
				).start();
		
	}
}
		
