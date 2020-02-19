package Generacja;

import java.util.*;

import static java.lang.Double.compare;
import static java.util.Collections.shuffle;

public class RandomWeighed<E, Double> {
    /*W Javie nie występuje metoda umożliwiająca losowy wybór elementów z uwzględnieniem wag, czyli wybór liter
    z prawdopodobieństwem wprost proporcjonalnym do częstości występowania, dlatego przedstawiam najcudowniejszą jak każde
    dziecko dumnego rodzica, choć nieefektywną (studencką) pod względem złożoności asymptotycznej i wykorzystania pamięci
    klasę wzbogacającą operacje losowego wyboru Javy - według własnego pomysłu.
     */
    private Map<E, Double> first;  //Słownik liter występujących po danym prefiksie

    public RandomWeighed(Map<E, Double> map){  //Argument: słownik liter występujących po danym prefiksie
        this.first = map;
    }

    public E randomize(){
        TreeMap<E, Double> r = new TreeMap<E, Double>();
        r.putAll(this.first);   //Kopiujemy słownik do umożliwiwającego uporządkowanie elementów TreeMap
        E s = r.firstKey();  //Pierwszy (najmniejszy) klucz
        List<E> shuf = new ArrayList();
        double d = (double) r.get(s);  //Inicjalizacja porównawczej liczby na podstawie wartości pierwszego klucza

        for (Map.Entry<E, Double> en: r.entrySet()){  //Wybór najmniejszej wartości wśród kluczy
            /*
            W parametrach obiektów Map nie są dozwolone typy prymitywne, np. double, dlatego w poniższym przykładzie,
            w celu przeprowadzenia niezbędnych operacji, wykonano rzutowanie z klasy Double na typ prymitywny double
             */
            if(compare((double)en.getValue(), d)<0){d = (double)en.getValue();}}  //Ustalenie nowej najmniejszej wartości

        //Wartość każdego klucza dzielona przez najmniejszą wartość, by ustalić wielokrotność najmniejszej wartości.
        for (Map.Entry<E, Double> entry: r.entrySet()){
            double howMany = Math.round((double)entry.getValue()/d); //Dzielenie wartości klucza przez najmniejszą watość
            for(int i =0; i<howMany; i++){  //Liczba powtórzeń klucza na liście równa wielokrotności najmnijeszej wartości
                shuf.add(entry.getKey());
            }
        }
        shuffle(shuf);  //Losowe mieszanie listy
        return shuf.get(0);  //Po losowym przemieszanu pierwszy element jest zawsze losowy
    }
}
