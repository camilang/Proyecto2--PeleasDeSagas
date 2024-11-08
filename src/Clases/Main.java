/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Clases;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Camila Garcia
 */
public class Main {
    public static Semaphore exclusionMutua = new Semaphore(1);
    public static Interfaz interfaz = new Interfaz();
    public static Administrador administrador = new Administrador();
    public static InteligenciaArtificial ai = new InteligenciaArtificial();
    public static int duracion = Integer.parseInt(interfaz.getDuracion())*1000;
   

    public static void main(String[] args) {
        administrador.exe();
        interfaz.setVisible(true);
        
    }

    
}
