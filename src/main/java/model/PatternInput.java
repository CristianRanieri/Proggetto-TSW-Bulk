package model;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternInput {
    public static boolean email(String email){
        return Pattern.compile("^[a-zA-Z0-9]+@[a-zA-Z]+\\.[a-zA-Z]{1,10}$",  Pattern.CASE_INSENSITIVE).matcher(email).matches() && email.length()<=30;
    }

    // Password di 8-16 caratteri con almeno una cifra, almeno una
    // lettera minuscola, almeno una lettera maiuscola, almeno un
    // carattere speciale senza spazi bianchi
    public static boolean password(String pass){
        return Pattern.compile("^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,16}$",  Pattern.CASE_INSENSITIVE).matcher(pass).matches();
    }

    public static boolean nome(String nome){
        return Pattern.compile("^[a-zA-Z ]{3,29}[a-zA-Z]$",  Pattern.CASE_INSENSITIVE).matcher(nome).matches() && nome.length()<=30;
    }

    public static boolean gusto(String nome){
        return Pattern.compile("^[a-zA-Z]{4,30}$",  Pattern.CASE_INSENSITIVE).matcher(nome).matches() && nome.length()<=30;
    }


    public static boolean numero(String numero){
        return Pattern.compile("^[0-9]{1,10}$", Pattern.CASE_INSENSITIVE).matcher(numero).matches();
    }

    public static boolean numeroCarta(String numeroCarta){
        return Pattern.compile("^\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}$", Pattern.CASE_INSENSITIVE).matcher(numeroCarta).matches();
    }

    public static boolean numeroCCV(String numeroCCV){
        return Pattern.compile("^\\d{3}$", Pattern.CASE_INSENSITIVE).matcher(numeroCCV).matches();
    }

    public static boolean data(Date date){
        if(date.before(new Date()))
            return false;
        return true;
    }

    public static boolean stringaConSPazzi(String s){
        return Pattern.compile("^[a-zA-Z0-9 ]{3,30}$", Pattern.CASE_INSENSITIVE).matcher(s).matches() && s.length()<=30;
    }

    public static boolean numeroCivico(String civico){
        return Pattern.compile("^\\d{1,5}$", Pattern.CASE_INSENSITIVE).matcher(civico).matches();
    }

    public static boolean numeroCAP(String cap){
        return Pattern.compile("^\\d{5}$", Pattern.CASE_INSENSITIVE).matcher(cap).matches();
    }

    public static boolean prezzo(String prezzo){
        return Pattern.compile("^\\d{1,3}\\.\\d{2}$", Pattern.CASE_INSENSITIVE).matcher(prezzo).matches();
    }

    public static boolean numeri2_4Cifre(String prezzo){
        return Pattern.compile("^\\d{2,4}$", Pattern.CASE_INSENSITIVE).matcher(prezzo).matches();
    }

    public static boolean numeri1_4Cifre(String prezzo){
        return Pattern.compile("^\\d{1,4}$", Pattern.CASE_INSENSITIVE).matcher(prezzo).matches();
    }

    public static boolean descrizione(String des){
        return des.length()<=255;
    }

    public static boolean stringaConNumeri(String des){
        return Pattern.compile("^[a-zA-Z0-9 ]{4,40}$", Pattern.CASE_INSENSITIVE).matcher(des).matches();
    }
}
