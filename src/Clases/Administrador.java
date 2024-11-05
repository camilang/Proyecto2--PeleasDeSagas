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
public class Administrador {
    
    
    
    
    
    
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
    
    public Cola qST5;
    
    public int rondasN;
    
    public Semaphore mutex; 
    
    
    public Administrador(){
        
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
            
          
            
        }else{
            this.nST++;
            
        }
         
        
    }

    
    
    public void exe(){
        for (int i = 0; i < 20; i++) {
            this.newPersonaje("Star Wars");
            this.newPersonaje("Star Trek");
        }
        
        try {
            this.mutex.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Administrador.class.getName()).log(Level.SEVERE, null, ex);
        }
        // terminar aqui
        
    }
    
    
    
 
    
}
