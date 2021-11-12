package br.com.tt.chat.util;

import java.util.Optional;
import java.util.Scanner;

public class UserInterface {
    public static String pedirString(String descricao){
        System.out.println(descricao);
        return new Scanner(System.in).nextLine();
    }
    public static int pedirInt(String descricao){
        System.out.println(descricao);
        return new Scanner(System.in).nextInt();
    }
    public static void imprimir(String descricao){
        System.out.println(descricao);
    }

    public static Optional<String> pedirTexto(String descricao){
        System.out.println(descricao);

        String texto = new Scanner(System.in).nextLine();

        if(texto.isBlank()){
            return Optional.empty(); // Caixa vazia
        }else{
            return Optional.of(texto); // Caixa com texto
        }
    }
}
