/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afd;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author Cristian
 */
public class LeerPartiendo {
    
    private int div;
    private String direccion;
    
    public LeerPartiendo(int div, String direccion){
        this.div = div;
        this.direccion = direccion;
    }
    
    public String [][] getMatriz(){
        try{
            System.out.println(getDireccion());
        BufferedReader b = new BufferedReader(new FileReader(getDireccion()));
        int cont = 0;
        
        String linea = b.readLine();
        String lineas = "";
        
            while(linea!=null){
                cont++;
                lineas+=linea+"-";
                if(cont == getDiv()){
                    cont = 0;
                    lineas+=";";
                }
                linea = b.readLine();
            }
            b.close();
            String filas[] = lineas.split(";");
            String matriz[][] = new String[filas.length][];
            for (int i = 0; i < matriz.length; i++) {
                matriz[i] = filas[i].split("-");
            }
            return matriz;
        }catch(Exception e){
            System.out.println("no se encuentra");
        }
          return null;
            
        }

    public int getDiv() {
        return div;
    }

    public void setDiv(int div) {
        this.div = div;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    
  
    }

