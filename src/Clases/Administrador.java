/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MAURICIO MENDEZ
 */
public class Administrador extends Thread {
    
    
    
    
    
    
    public final Random r = new Random();
    public int nSW;
    public int nST;
    
    public Cola qSW1;
    public Cola qSW2;
    public Cola qSW3;
    public Cola qSW4;
    
    public Cola qST1;
    public Cola qST2;
    public Cola qST3;
    public Cola qST4;
    
    
    
    public int rondasN;
    
    public Semaphore exclusionMutua; 
    public InteligenciaArtificial ia;
    
    
    public Administrador(){
        this.exclusionMutua = Main.exclusionMutua;
        this.nSW = 0;
        this.nST = 0;
        
        this.qSW1 = new Cola();
        this.qSW2 = new Cola();
        this.qSW3 = new Cola();
        this.qSW4 = new Cola();
        
        this.qST1 = new Cola();
        this.qST2 = new Cola();
        this.qST3 = new Cola();
        this.qST4 = new Cola();
        
        this.rondasN = 0;
 
    }
    
    public void probsNewPersonaje(String saga){
        int resultado = r.nextInt(100);
        if (resultado<=80){
                this.newPersonaje(saga);
            
        }
    }
    
    public void newPersonaje(String saga){
        if (saga.equals("Star Wars")){
            this.nSW++;
            Personaje newPersonaje = new Personaje(saga,nSW);
            this.colaPersonaje(newPersonaje, qSW1, qSW2, qSW3);
            
            printPersonaje(newPersonaje);
            
            
          
            
        }else{
            this.nST++;
            Personaje newPersonaje = new Personaje(saga,nST);
            this.colaPersonaje(newPersonaje, qST1, qST2, qST3);
            
            printPersonaje(newPersonaje);
            
        }

    }
    
    public void colaPersonaje(Personaje p, Cola q1, Cola q2, Cola q3){
        switch (p.nivel) {
            case 1:
                q1.encolar(p);
                break;
            case 2:
                q2.encolar(p);
                break;
            case 3:
                q3.encolar(p);
                break;
            default:
                break;
        }
    }    

    public void printPersonaje(Personaje equis){
        System.out.println("\nADDED: " + equis.id + " //Habilidades:  " + equis.habilidades + " //Vida:  " + equis.puntosVida + " //Fuerza:  " + equis.fuerza + " //Agilidad:  " + equis.agilidad + " //LEVEL: "+ equis.nivel);
        
    }
    
    public void enviarRefuerzo(Personaje p, Cola q){
        q.encolar(p);
    }
    
    public void exe(){
        for (int i = 0; i < 20; i++) {
            this.newPersonaje("Star Wars");
            this.newPersonaje("Star Trek");
        }
        this.ia = Main.ai;
        try {
            this.exclusionMutua.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Administrador.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.start();
        ia.start();
       
        
    }
    
    private Personaje getFromQ(Cola q1, Cola q2, Cola q3){
        if (!q1.esVacio()){
            Personaje seleccionarP = q1.getCabeza();
            q1.desencolar();
            return seleccionarP;
        } else if (!q2.esVacio()){
            Personaje seleccionarP = q2.getCabeza();
            q2.desencolar();
            return seleccionarP;
        } else if (!q3.esVacio()){
            Personaje seleccionarP = q3.getCabeza();
            q3.desencolar();
            return seleccionarP;
        }
        return null;
    }
    
    private void actualizarContador(Cola q){
        Personaje aux = q.getCabeza();
        while (aux!=null){
            aux.rondasContador++;
            aux = aux.getNext();
        }
    }
    
    private void verificarPrioridad(Cola q1, Cola q2, Cola q3){
        Personaje cabeza = q2.getCabeza();
        while (cabeza != null && cabeza.rondasContador == 8){
            q2.desencolar();
            cabeza.setNext(null);   
            cabeza.nivel--;
            cabeza.rondasContador = 0;
            this.colaPersonaje(cabeza, q1, q2, q3);
            cabeza = q2.getCabeza();
        }
        
        cabeza = q3.getCabeza();
        while (cabeza != null && cabeza.rondasContador == 8){
            q3.desencolar();
            cabeza.setNext(null);
            cabeza.nivel--;
            cabeza.rondasContador = 0;
            this.colaPersonaje(cabeza, q1, q2, q3);
            cabeza = q3.getCabeza();
        }
    }
    
    private void probsSacarRefuerzo(Cola refuerzo, Cola q1){
        int result = r.nextInt(100);
        if (result<=40 && !refuerzo.esVacio()){
            Personaje p = refuerzo.getCabeza();
            refuerzo.desencolar();
            p.setNext(null);
            q1.encolar(p);
            //actualizar interfaz
            
        }
     
    }
    
    @Override
    public void run(){
        try {
            sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Administrador.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (true){
            try {
                
                this.probsSacarRefuerzo(qST4, qST1);
                this.probsSacarRefuerzo(qSW4, qSW1);
                
                //interffazx
                
                sleep(2000);
                if (this.rondasN != 0 &&  rondasN%2 == 0){
                    this.probsNewPersonaje("Star Wars");
                    this.probsNewPersonaje("Star trek");
                    
                }
                
                Personaje pSW = this.getFromQ(this.qSW1,this.qSW2,this.qSW3);
                pSW.setNext(null);
                Personaje pST = this.getFromQ(this.qST1,this.qST2,this.qST3);
                pST.setNext(null);
                ia.pSW = pSW;
                ia.pST = pST;
                // ACTUALIZAR INTERFAZ
            
                this.exclusionMutua.release();
                sleep(1000);
                this.exclusionMutua.acquire();
 
                
                actualizarContador(this.qSW1);
                actualizarContador(this.qSW2);
                actualizarContador(this.qSW3);
                actualizarContador(this.qST1);
                actualizarContador(this.qST2);
                actualizarContador(this.qST3);
               
                verificarPrioridad(this.qSW1,this.qSW2,this.qSW3);
                verificarPrioridad(this.qST1,this.qST2,this.qST3);
                
                this.rondasN++;
                
                // MODIFICAR INTERFAZ
                
            } catch (InterruptedException ex) {
            Logger.getLogger(Administrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
 
    
}
