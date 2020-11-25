/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afd;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author Cristian
 */
public class AFD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        LeerPartiendo l = new LeerPartiendo(4, "C:\\Users\\mcris\\Desktop\\second.txt");
//        LeerPartiendo l = new LeerPartiendo(4, "C:\\Users\\mcris\\Desktop\\previo.txt");
        
        String matriz[][] = l.getMatriz();
        
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j]+";");
            }
            System.out.println("");
        }
//        
//        System.out.println("***********IMPRIMIENDO AUTOMATA***************");
//        
        Automata a = new Automata(matriz);
        LinkedList<LinkedList<String>> aut = a.getAutomataDeterministico();
//        
//        for (LinkedList<String> lista : aut) {
//            for (String dato : lista) {
//                System.out.print(dato+"-");
//            }
//            System.out.println("");
//        }
        System.out.println("");
        System.out.println("***************Transiciones****************");
        LinkedList<String> tran = a.getTransiciones();
        for (String dato : tran) {
            System.out.println(dato);
        }
        System.out.println("el automata tiene "+aut.size()+" estados");
        
        
        
//        System.out.println(a.seRepite("f,r", "r,p"));
//        String ar = a.getParejas("zr")[1];
//        System.out.println(ar);
//        a.crearAutomata();
        Scanner sc = new Scanner(System.in);
        
        int salir = 0;
        while(true){
            
            System.out.println("");
            String s = sc.nextLine();
            System.out.println("");
            String s2 = sc.nextLine();
            String rta = a.getParejaMinimizacion(s, s2);
            System.out.println(rta);
//            System.out.println("salir? \n0)no \n1)si");
//            salir = sc.nextInt();
//            sc.nextLine();
        }
        
    }
    
}
