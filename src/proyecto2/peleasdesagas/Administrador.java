/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2.peleasdesagas;

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
    
    public void newPersonaje(String company){
        if (company.equals("Nick")){
            this.nNick++;
            Personaje newPersonaje = new Personaje(company, nNick);
            this.queuePersonaje(newPersonaje, this.qAV1,this.qAV2,this.qAV3);
            
            printPersonaje(newPersonaje);
            
        }else{
            this.nCartoon++;
            Personaje newPersonaje = new Personaje(company, nCartoon);
            this.queuePersonaje(newPersonaje, this.qUS1, this.qUS2, this.qUS3);
            printPersonaje(newPersonaje);
        }
        Main.interfaz.updateQueues(this.qAV1,this.qAV2,this.qAV3,this.qAV4, this.qUS1, this.qUS2, this.qUS3, this.qUS4);   
        
    }

    public void queuePersonaje(Personaje p, Queue q1, Queue q2, Queue q3){
        switch (p.level) {
            case 1:
                q1.enqueue(p);
                break;
            case 2:
                q2.enqueue(p);
                break;
            case 3:
                q3.enqueue(p);
                break;
            default:
                break;
        }
    } 
    
    public void sendRefuerzo(Personaje p, Queue q){
        q.enqueue(p);
    }
    
    public void exe(){
        for (int i = 0; i < 20; i++) {
            this.newPersonaje("Nick");
            this.newPersonaje("Cartoon");
        }
        this.ai = Main.ai;
        try {
            this.mutex.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.start();
        ai.start();
    }
    
    public void printPersonaje(Personaje equis){
        System.out.println("\nADDED: " + equis.id + " //Habilidades:  " + equis.skills + " //Vida:  " + equis.stamina + " //Fuerza:  " + equis.strength + " //Agilidad:  " + equis.agility + " //LEVEL: "+ equis.level);
        
    }
    
    private Personaje getFromQ(Queue q1, Queue q2, Queue q3){
        if (!q1.isEmpty()){
            Personaje selectedP = q1.getHead();
            q1.dequeue();
            return selectedP;
        } else if (!q2.isEmpty()){
            Personaje selectedP = q2.getHead();
            q2.dequeue();
            return selectedP;
        } else if (!q3.isEmpty()){
            Personaje selectedP = q3.getHead();
            q3.dequeue();
            return selectedP;
        }
        return null;
    }
    
    private void counterUpdates(Queue q){
        Personaje aux = q.getHead();
        while (aux!=null){
            aux.roundsCounter++;
            aux = aux.getNext();
        }
    }
    
    private void priorityCheck(Queue q1, Queue q2, Queue q3){
        Personaje head = q2.getHead();
        while (head != null && head.roundsCounter == 8){
            q2.dequeue();
            head.setNext(null);
            head.level--;
            head.roundsCounter = 0;
            this.queuePersonaje(head, q1, q2, q3);
            head = q2.getHead();
        }
        
        head = q3.getHead();
        while (head != null && head.roundsCounter == 8){
            q3.dequeue();
            head.setNext(null);
            head.level--;
            head.roundsCounter = 0;
            this.queuePersonaje(head, q1, q2, q3);
            head = q3.getHead();
        }
    }
    
    private void probsSacarRefuerzo(Queue refuerzo, Queue q1){
        int result = r.nextInt(100);
        if (result<=40 && !refuerzo.isEmpty()){
            Personaje p = refuerzo.getHead();
            refuerzo.dequeue();
            p.setNext(null);
            q1.enqueue(p);
            Main.interfaz.updateQueues(qAV1,this.qAV2,this.qAV3,this.qAV4,this.qUS1,this.qUS2,this.qUS3,this.qUS4); 
        }
     
    }
    
    @Override
    public void run(){
        try {
            sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (true){
            try {
                
                this.probsSacarRefuerzo(qAV4, qAV1);
                this.probsSacarRefuerzo(qUS4, qUS1);
                
                Main.interfaz.state("seleccionando personaje");
                
                sleep(2000);
                if (this.roundsN != 0 &&  roundsN%2 == 0){
                    this.probsNewPersonaje("Nick");
                    this.probsNewPersonaje("Cartoon");
                    
                }
                
                Personaje pAV = this.getFromQ(this.qAV1,this.qAV2,this.qAV3);
                pAV.setNext(null);
                Personaje pUS = this.getFromQ(this.qUS1,this.qUS2,this.qUS3);
                pUS.setNext(null);
                ai.pAV = pAV;
                ai.pUS = pUS;
                Main.interfaz.updateSelected(pAV, pUS); 
                
                Main.interfaz.state("simulando combate");
                
                Main.interfaz.updateQueues(qAV1,this.qAV2,this.qAV3,this.qAV4,this.qUS1,this.qUS2,this.qUS3,this.qUS4);       
                //System.out.println("\n-- Admin running");
            
                this.mutex.release();
                sleep(1000);
                this.mutex.acquire();
 
                
                counterUpdates(this.qAV1);
                counterUpdates(this.qAV2);
                counterUpdates(this.qAV3);
                counterUpdates(this.qUS1);
                counterUpdates(this.qUS2);
                counterUpdates(this.qUS3);
                
                priorityCheck(this.qAV1,this.qAV2,this.qAV3);
                priorityCheck(this.qUS1,this.qUS2,this.qUS3);
                
                this.roundsN++;
                
                Main.interfaz.updateQueues(this.qAV1,this.qAV2,this.qAV3,this.qAV4,this.qUS1,this.qUS2,this.qUS3,this.qUS4);
                
                
            } catch (InterruptedException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
