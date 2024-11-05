/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.util.Random;

/**
 *
 * @author Camila Garcia
 */
public class Personaje {
    public String id;
    public String nombre;
    public int cMonto;
    public int nivel;
    public int valorHabilidades;
    public int valorPuntosVida;
    public int valorFuerza;
    public int valorAgilidad;
    public boolean habilidades, puntosVida, fuerza, agilidad;
    public String saga; 
    public String nombrePersonaje;


    public String[] personajesSW = {"Ackbar","Grogu","Lando Calrissian","Cassian Andor","Mando","Yoda", "Luke Skywalker","Darth Vader","Han Solo", "Obi-Wan Kenobi", "Boba Fett", "Chewbacca"};
    public String[] personajesST = {"Jean Luc Picard","Spock","Worf","Phlox","Data","Nog", "Odo","Tuvok","Elim Garak", "Harry Kim", "Tom Paris", "Kira Nerys", "Teniente Saavik" };

    
    private Personaje proximo; 
    public int contador; 
    public int rondasContador; 
    
    public Personaje(String saga, int contador){
        this.habilidades = (definicionCalidad(60));
        this.puntosVida = (definicionCalidad(70));
        this.fuerza = (definicionCalidad(50));
        this.agilidad = (definicionCalidad(40));
        
        this.saga = saga;
        this.cMonto = 0; 
        this.contador = contador;
        this.nombre = "";
        
        this.id = definicioId(saga, contador);
        
        this.nivel = definicionNivel(habilidades, puntosVida, fuerza, agilidad);
        
        this.rondasContador = 0;
    }
    
    public boolean definicionCalidad(int porcentaje){
        Random r = new Random(); 
        int rInt = r.nextInt(100);
        return (rInt < porcentaje);
    }
    
    public int definicionNivel(boolean habilidades, boolean puntosVida, boolean fuerza, boolean agilidad){
        
        if (habilidades){cMonto+=5;}
        if(habilidades) {valorHabilidades += 5;}
        if (puntosVida){cMonto+=4;}
        if(puntosVida) {valorPuntosVida += 4;}
        if (fuerza){cMonto+=3;}
        if(fuerza) {valorFuerza += 3;}
        if (agilidad){cMonto+=3;}
        if(agilidad) {valorAgilidad += 3;}
        
        if (cMonto >= 11){return 1;}
        if (cMonto >=5  && cMonto < 10){return 2;}
        return 3;
    }
    
    public String definicioId(String saga, int contador){
        String id;
        String[] listToRun;
        String nombre = null;
        if(saga.equals("Nick")){
            id = "AV";
            listToRun = this.personajesSW;
            this.nombre = listToRun[contador-1];
        }else{
            id = "US";
            listToRun = this.personajesST;
            this.nombre = listToRun[contador-1];
        }
        
        String num = Integer.toString(contador);
        
        if (num.length()==1){num=("0"+num);}
        id += num;

        return id;  
    }
    
    public Personaje getNext(){
        return proximo;
    }
    
    public void setNext(Personaje proximo){
        this.proximo = proximo;
    }
    
}
