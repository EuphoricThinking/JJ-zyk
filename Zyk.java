package Generacja;

import java.util.List;
import java.util.Map;

public interface Zyk extends Nazewnictwo {  //Interfejs dla JJęzyka
    //Słownik częstości liter po podanym prefiksie; Double stosowane do losowego wyboru według wag prawdopodobieństwa
    Map<Character, Double> częstość(String prefiks);
   /* void uczSię(String o, List<Character> c);
   Nie jest uwzględniona w interfejsie, ponieważ w JJęzyku służy jako prytwatna metoda pomocnicza, natomiast interfejs
   warunkuje publiczną widoczność metod
    */
    void uczSięZPliku(String nazwa);  //Zapisuje dane o pliku, na podstawie którego tworzy język
    String literyAlfabetu();  //Zwraca litery alfabetu utworzonego na podstawie pliku
}
