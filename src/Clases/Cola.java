/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author MAURICIO MENDEZ
 */
public class Cola {
    private Personaje cabeza; 
    private Personaje cola; 
    public int tamano; 
    
    public Cola(){
        this.cabeza = null; 
        this.cola = null; 
        this.tamano = 0; 
    }
    
    public boolean esVacio(){
        return this.cabeza == null;
    }
    
    public void vacio(){
        this.cabeza = this.cola = null;
        this.tamano = 0;
    }
    
    public void encolar(Personaje nuevo){
        if(this.esVacio()){
            cabeza = cola = nuevo;
        }else{
            cola.setNext(nuevo);
            cola = nuevo;
        }
        tamano++;
    }
    
    public void desencolar(){
        if(this.esVacio()){
        }else if(tamano == 1){
            
            this.vacio();
        }else{
            cabeza = cabeza.getNext();
            tamano--;
        }
    }
    
    public Personaje getCabeza() {
        return cabeza;
    }

    public void setCabeza(Personaje cabeza) {
        this.cabeza = cabeza;
    }

    public Personaje getCola() {
        return cola;
    }

    public void setCola(Personaje cola) {
        this.cola = cola;
    }
}
