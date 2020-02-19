package Generacja;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.util.Collections.shuffle;

public class Generator implements Gen{
    //Klasa tworząca obiekty generujące nowe słowa na podstawie informacji o języku, przekazanych przez JJęzyk.

    private JJęzyk mowa;          //JJęzyk, na podstawie którego Generator generuje słowa
    //Maksymalna długość słowa, określona na podstawie informacji przekazanej prze JJęzyk: średnia długość słowa + 2
    private int długośćSłowa;
    private String imięMocy;      //Nazwa generatora

    public Generator(JJęzyk j){
        this.długośćSłowa = j.długośćSłowa();
        this.mowa = j;
        this.imięMocy = "Generator żartów o programistach";
    }


    public String dajSłowo(){    //Generuje słowa na podstawie informacji przekazanych przez język
        JJęzyk j = this.mowa;
        List<Character> pierwsze = j.listaPierwszych();  //Lista pierwszych liter;
        shuffle(pierwsze);  //Miesza listę pierwszych liter wyrazów, by zapewnić różnorodność.
        String słowo = ""+pierwsze.get(0);  //Konstrukt, do którego będą dodawane następne znaki do pierwszej wylosowanej litery
        Map<String, Map<Character, Double>> prefiksy = j.słownikJęzykaAntypolskiego();   //Słownik prefiksów
        Random num = new Random();
        int długość = num.nextInt(this.długośćSłowa)+1;  //Losowo ustala długość słowa od jeden do (losowej liczby+1)
        for (int i=0; i<długość; i++){
            if (prefiksy.containsKey(słowo)){  //Jeśli dotychczas wygenerowane słowo występuje jako prefiks
                Map<Character, Double> litery = j.częstość(słowo);  //Słownik znaków występujących po danym prefiksie
                /*Obiekt własnej kreatywności, umożliwiające losowy wybór znaków z uwzględnieniem wag - wybór
                wprost proporcjonalny do częstości występowania danego znaku
                 */
                RandomWeighed<Character, Double> mojeDziecko = new RandomWeighed<Character, Double>(litery);
                //randomize() zwraca obiekt klasy Character, który poprzez dodanie do napisu ulega konwersji do String
                słowo = słowo + mojeDziecko.randomize();}
            else{   //Jeśli dotychczas utworzone słowo nie występuje jako prefiks, losuje znaki
                słowo = słowo + komoraLosowaniaJestPustaZwalniamyBlokadę(prefiksy);
            }

        }
        return słowo;
    }

    //Metoda pomocnicza, umożliwiająca losowy wybór znaków; argument - słownik prefiksów
    private Character komoraLosowaniaJestPustaZwalniamyBlokadę(Map<String, Map<Character, Double>> słowik){
        List<String> klucze = new ArrayList<String>(słowik.keySet());  //Tworzy listę prefiksów.
        shuffle(klucze);   //Miesza listę.
        /*Inicjalizacja obiektu do losowego wyboru na podstawie wag. Znaki są wybierane ze słownika częstości znaków
        dla losowo wybranego prefiksu: klucze.get(0). Pierwszy prefiks z losowo przemieszanej listy prefiksów jest losowy.
         */
        RandomWeighed<Character, Double> mojePrzypadkoweDziecko= new RandomWeighed<Character, Double>(słowik.get(klucze.get(0)));
        return mojePrzypadkoweDziecko.randomize();   //Zwraca losowo wylosowany z uwzględnieniem wag znak
    }

    public String nazwa() {return this.imięMocy;}  //Zwraca nazwę generatora
}
