/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afd;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * @author Cristian
 */
public class Automata {

    private String matriz[][];
    private LinkedList<LinkedList<String>> automataDeterministico;
    private LinkedList<String> transiciones;
    
    public Automata(String matriz[][]) {
        
        this.matriz = matriz;
        String estadoInicial = this.matriz[1][0];
        this.automataDeterministico = new LinkedList();
        LinkedList<String> ini = new LinkedList<>();
        ini.add(estadoInicial);
        this.automataDeterministico.add(ini);
        this.transiciones = new LinkedList<>();
        crearAutomata();
        crearExcel();
    }
    
    public void crearAutomata(){
        
        for (int i = 0; i<automataDeterministico.size(); i++) {
            llenarParejas(automataDeterministico.get(i));//lleno la fila con sus estados
            crearEstadosNuevos(automataDeterministico.get(i)); //creo los nuevos estados
        }
        
    }
    
    public String getParejaMinimizacion(String est, String est2){
        
        est = est.toUpperCase();
        est2 = est2.toUpperCase();
        
        String rta = est+","+est2+"\n";
        LinkedList<LinkedList<String>> deterministico = this.getAutomataDeterministico();
        int cont = 0;
            for (LinkedList<String> lista: deterministico) {
                
                if(lista.getFirst().equals(est)){//si es igual a p
                    for (int i = 1; i<lista.size(); i++) {
                        String estad = lista.get(i).replace(",","");
                       rta+=est+" ,"+cont+" : "+estad+"    ";
                        cont++;
                    }
                    rta += "\n";
                }
                
                else if(lista.getFirst().equals(est2)){//si es igual a p
                    for (int i = 1; i<lista.size(); i++) {
                       String estad = lista.get(i).replace(",","");
                       rta+=est2+" ,"+cont+" : "+estad+"    ";
                        cont++;
                    }
                    rta += "\n";
                }
                
                cont = 0;
            }
        
//        System.out.println("get parejas devuelve : "+rta[1]);
        return rta;
    }
    
    private void llenarParejas(LinkedList<String> l){
        
        String result[] = getParejas(l.getFirst()); // arreglo con estados[hgfh,gjhgj]
        String rta[] = result[1].split(":");
        transiciones.add(result[0]);
        String maEst[][] = new String[rta.length][];
//        for (int i = 0; i < maEst.length; i++) {  //mientras sea menor al len de parejas
            
            for (int j = 0; j < rta.length; j++) {
                String estadosArreglo[] = rta[j].split("-");
                maEst[j] =  estadosArreglo;  //la primera fila de estados
            }
//        }
        
        for (int i = 0; i < maEst[0].length; i++) {//comenzamos a pegar todos los 0 con 0 y unos con 1 quitando repetidos
            String cad ="";
            for (int j = 0; j < maEst.length; j++) {
                String estado[] = maEst[j][i].split(",");
                
                for (int k = 0; k < estado.length; k++) {
                    String com = estado[k];
                    if(!seRepite(com, cad)){
                    cad+=com+","; //pego hacia abajo
                }
                }
                
            }
            l.add(cad);
        }
    
    }
    
    private boolean seRepite(String estado, String cad){
    
            for (int i = 0; i < cad.length(); i++) {
            String c = cad.charAt(i)+"";
            if(c.equals(estado)){
                return true;
            }
        }
        
        return false;
    }
    
    private void crearEstadosNuevos(LinkedList<String> l){
    
        for (int i = 1; i < l.size(); i++) {
            String buscar = l.get(i);   
            buscar = buscar.replace(",", "");   //BUSCO SI ESE ESTADO EXISTE 
            if(!esta(buscar)){  //si no esta el estado lo agrego
                LinkedList<String> nuevo = new LinkedList<>();
                nuevo.add(buscar);
                automataDeterministico.add(nuevo);
            }
            
        }
    }
    private boolean esta(String buscar){
        for (LinkedList<String> lista : automataDeterministico) {
            String primero = lista.getFirst();
            if(sonIguales(buscar, primero)) //NO PUEDO COMPARAR ASI
                return true;
        }
        return false;
    }
    
    private boolean sonIguales(String a, String b){
    
        if(a.length()-b.length() != 0)
            return false;
        String ar[] = a.split("");
        String br[] = b.split("");
        Stack<String> pila = new Stack();
        for (int i = 0; i < br.length; i++) { //PASO UN STRING A UNA PILA
            pila.push(br[i]);
        }//comienzo a comparar
        for (int i = 0; i < ar.length; i++) {
            Stack<String> aux = new Stack<>();
            
            while(!pila.empty()){
                String pop = pila.pop();
                if(!ar[i].equals(pop))
                    aux.push(pop);
            }
            pila = aux;
        }
        if(pila.empty())
            return true;
        else
            return false;
    }
    
    private void crearExcel(){
    
            FileOutputStream salidaDeArchivo = null;
        
            Workbook libro = new XSSFWorkbook();
            //creo la hoja
            Sheet hoja = libro.createSheet("automata");
            //creo la fila
            LinkedList<LinkedList<String>> automatas = this.getAutomataDeterministico();
            int cont = 0;
            for (LinkedList<String> lista : automatas) {
                Row fila = hoja.createRow(cont);
                int col = 0;
                for (String dato : lista) {
                    fila.createCell(col).setCellValue(dato.replace(",", ""));
                    col++;
                }
                cont++;
            }   
            try {
                salidaDeArchivo = new FileOutputStream("C:\\Users\\mcris\\Desktop\\punto2nUEVO.xlsx");
                libro.write(salidaDeArchivo);
                salidaDeArchivo.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Automata.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
            Logger.getLogger(Automata.class.getName()).log(Level.SEVERE, null, ex);
           } finally {
            try {
                salidaDeArchivo.close();
            } catch (IOException ex) {
                Logger.getLogger(Automata.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private String[] getParejas(String estado){
        String rta[] = new String[2];
        rta[0] = "";
        rta[1] = "";
        estado = estado.toUpperCase();
        for (int i = 0; i < estado.length(); i++) { //tomo la cadena de estados
            String letra = estado.charAt(i)+"";//prx  (p)
            for (int j = 0; j < this.matriz.length; j++) {
                String est = matriz[j][0];   //si es igual a p
                if(letra.equals(est)){ //si es igual entro a esa fila sino no
                    rta[0]+="\n";
                    
                    for (int k = 1; k < this.matriz[j].length; k++) {
                        rta[0]+=letra+","+matriz[0][k]+" >"+this.matriz[j][k]+"   ";
                        rta[1]+=""+this.matriz[j][k]+"-";
                    }
                    rta[1]+=":";
                }
            }
        }
//        System.out.println("get parejas devuelve : "+rta[1]);
        return rta;
    }

    public String[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(String[][] matriz) {
        this.matriz = matriz;
    }

    public LinkedList<LinkedList<String>> getAutomataDeterministico() {
        return automataDeterministico;
    }

    public void setAutomataDeterministico(LinkedList<LinkedList<String>> automataDeterministico) {
        this.automataDeterministico = automataDeterministico;
    }

    public LinkedList<String> getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(LinkedList<String> transiciones) {
        this.transiciones = transiciones;
    }
    
    
    
    
}
