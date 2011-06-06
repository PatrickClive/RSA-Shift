package edu.nova.log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;


public class AlgoritmoEVCN {

	private static int alfabeto = 26;
	private static int letraInicial = 'a';
	
	public static void main(String[] args) {
		//encriptar();
		desencriptar();
	}
	
	public static void encriptar() {
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(System.in);
			BufferedReader reader = new BufferedReader(inputStreamReader);
			System.out.println("Digite el texto claro a cifrar:");
			String cadena = reader.readLine();
			System.out.println("Longitud del texto leido:"+cadena.length());
			
			BigInteger cifrarRSA = toNumbers(cadena);
			System.out.print("Texto a cifrar en RSA:\t" + cifrarRSA
					+ "\n");

			System.out.println("Digite la salida del RSA:");
			String encriptadoRSA = reader.readLine();

			BigDecimal bigDecimal = new BigDecimal(encriptadoRSA);
			String cifradoRSA = toLetters(bigDecimal.toBigIntegerExact(), cadena
					.length()+1);
			System.out.println("Texto cifrado para enviar por la red: "
					+ cifradoRSA);
			System.out.println("\n\n\n\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void desencriptar() {
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(System.in);
			BufferedReader reader = new BufferedReader(inputStreamReader);
			System.out.println("Digite el texto cifrado enviado por la red:");
			BigInteger cifradoRSA2 = toNumbers(reader.readLine());
			
			System.out.println("Digite la longitud del mensaje original");
			String longitud = reader.readLine();
			
			System.out.println("Texto a des-cifrar en RSA:"+cifradoRSA2);
			System.out.println("Digite la salida del RSA:");
			BigDecimal bigDecimal = new BigDecimal(reader.readLine());
			String desencriptadoRSA = toLetters(bigDecimal.toBigIntegerExact(), Integer.parseInt(longitud)-1);
			
			System.out.println("Texto Claro:"+desencriptadoRSA);
			System.out.println("\n\n\n\n\n\n\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String encriptarShift(String cadena, String clave) {
		Character[][] tabla = new Character[alfabeto][alfabeto];
		for(int i=0; i < alfabeto; i++) {
			for(int j=0; j < alfabeto; j++) {
				tabla[i][j] = Character.valueOf((char) (((i+j)%alfabeto)+letraInicial));
			}
		}
		
		String reemplazo = "";
		for(int i=0; i < cadena.length(); i++)
			reemplazo+=clave.charAt(i%clave.length());
		System.out.println("Reemplazado:\t"+reemplazo);
		
		String encriptado = "";
		for(int i=0; i < reemplazo.length(); i++) {
			char a = reemplazo.charAt(i);
			char b = cadena.charAt(i);
			encriptado += tabla[a-letraInicial][b-letraInicial];
		}
		
		return encriptado;
	}
	
	public static String desencriptarShift(String cadena, String clave) {
		System.out.println("Texto:\t\t"+cadena);
		System.out.println("Clave:\t\t"+clave);
		
		Character[][] tabla = new Character[alfabeto][alfabeto];
		for(int i=0; i < alfabeto; i++) {
			for(int j=0; j < alfabeto; j++) {
				tabla[i][j] = Character.valueOf((char) (((i+j)%alfabeto)+letraInicial));
			}
		}
		
		String reemplazo = "";
		for(int i=0; i < cadena.length(); i++)
			reemplazo+=clave.charAt(i%clave.length());
		System.out.println("Reemplazado:\t"+reemplazo);
		
		String desencriptado = "";
		for(int i=0; i < reemplazo.length(); i++) {
			char a = reemplazo.charAt(i);
			char b = cadena.charAt(i);
			
			Character[] fila = tabla[a-letraInicial];
			for(int j=0; j < fila.length; j++) {
				if(fila[j].equals(b))
					desencriptado += tabla[0][j];
			}
		}
		
		return desencriptado;
	}

	public static BigInteger toNumbers(String cadena) {
		BigDecimal bigDecimal = new BigDecimal(0);
		
		for(int i=0; i < cadena.length(); i++) {
			int c = cadena.charAt(i)-letraInicial;
			double d = c*Math.pow(alfabeto, (cadena.length()-i)-1);
			bigDecimal = bigDecimal.add(BigDecimal.valueOf(d));
		}
		
		return bigDecimal.toBigIntegerExact();
	}
	
	public static String toLetters(BigInteger number, int tamano) {
		String cadena = "";
		for(int i=tamano; i >= 0; i--) {
			BigDecimal potencia = new BigDecimal(Math.pow(alfabeto, i));
			BigInteger[] operado = number.divideAndRemainder(potencia.toBigIntegerExact());
			
			BigInteger residuo = operado[0];
			cadena += (char)(residuo.intValue()+letraInicial);
			number = operado[1];
		}
		return cadena;
	}
}
