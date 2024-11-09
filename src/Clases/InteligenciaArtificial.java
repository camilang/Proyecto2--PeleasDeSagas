/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;
import static java.lang.Math.abs;
import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Camila Garcia
 */
public class InteligenciaArtificial extends Thread{
    public Semaphore exclusionMutua; 
    public Personaje pSW;
    public Personaje pST;
    public Administrador administrador;
    public int ganaSW;
    public int ganaST;
    
    public InteligenciaArtificial() {
        this.exclusionMutua = Main.exclusionMutua;
        this.administrador = Main.administrador;
        this.ganaSW = 0;
        this.ganaST = 0;
        
    }

    
    public int resultado(){
        int probsResultado = random();
        if (probsResultado <= 40){
            return 1;//GANA
        } else if (probsResultado>40 && probsResultado<=67){
            return 2;//Empata
        } else {
            return 3;//Refuerzo
        }
    }
    
    public Personaje ganador(Personaje SW, Personaje ST){
        if ( SW.cMonto == ST.cMonto ){
            if (random()<50){
                return SW;
            }
            return ST;
        }
        
        Personaje mayorQ;
        Personaje menorQ;
        if (SW.cMonto>ST.cMonto){
            mayorQ = SW;
            menorQ = ST;
        }else{
            mayorQ = ST;
            menorQ = SW;
        }
        
        int diferencia = abs(SW.cMonto-ST.cMonto);
        
        if (diferencia > 0 && diferencia <=2){
            if (random()<=60){
                return mayorQ; 
            } else {
                return menorQ;
            }
        } else if (diferencia > 2 && diferencia <=6 ){
            if (random()<=70){
                return mayorQ; 
            } else {
                return menorQ;
            }
        }else if (diferencia > 6 && diferencia <=10 ){
            if (random()<=80){
                return mayorQ; 
            } else {
                return menorQ;
            }
        } else { 
            if (random()<=90){
                return mayorQ; 
            } else {
                return menorQ;
            }
        }
    }
    
    public int random(){
        Random random = new Random();
        int randomInt = random.nextInt(100);
        return randomInt;
    }
    
    
   
       @Override
    public void run(){
        try {
            sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(InteligenciaArtificial.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (true){
            try { 
                this.exclusionMutua.acquire();
                
                
                System.out.println("\n Ronda: " + Integer.toString(this.administrador.rondasN) + "\nInteligencia Artificial -- Selected:   " +pSW.id + " "+ pSW.nombre + "nivel:"+ pSW.nivel+ pSW.cMonto+"  y  " + pST.id+ " "+ pST.nombre+"nivel:"+ pST.nivel + pST.cMonto);
                
                int resultado = resultado();
                String resultadoStr;
                sleep(Main.duracion);
               // Main.interfaz.estado(""); 
               
                switch (resultado) {
                    case 1:
                        resultadoStr = "Ganador: ";
                       
                        Personaje ganador = ganador(pSW, pST);
                        if (ganador==pSW){
                            this.ganaSW++;
                            
                        }else{
                            this.ganaST++;
                            
                        }
                        System.out.println("\tGanador: " + ganador.id); 
                        
                        break;

                    case 2:
                        resultadoStr = "Empate";
                       
                        System.out.println("Empate ");
                        pSW.nivel = pST.nivel = 1;
                        
                        break;
                    
                    case 3: 
                        resultadoStr = "Suspendida";
                       
                        System.out.println("Refuerzo ");
                        administrador.enviarRefuerzo(pSW, administrador.qSW4);
                        administrador.enviarRefuerzo(pST, administrador.qST4);
                        break;
                }
                
                sleep(Main.duracion/2);
                
                this.exclusionMutua.release();
                sleep(1000);
                
                
            }catch (InterruptedException ex) {      
                Logger.getLogger(InteligenciaArtificial.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
