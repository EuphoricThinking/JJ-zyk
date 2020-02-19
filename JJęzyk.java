package Generacja;

import java.io.File;
import java.util.*;

import static java.util.Collections.sort;

public class JJęzyk implements Zyk{
    //Klasa tworząca obiekt zapamiętujący dane o języku na podstawie pliku tekstowego


    /*Słownik prefiksów z zagnieżdżonym słownikiem znaków występujących po danym prefiksie z przyporządkowaną częstością.
    Nie zastosowano skalowania, a proste zliczanie, ponieważ sumy zostaną wykorzystane w metodzie randomize() obiektu klasy
    według własnego pomysłu RandomWeighed, umożliwiającej losowy wybór znaków z uwzględnieniem wag, czyli wprost proporcjonalnie
    do częstości występowania poszczególnych znaków po danym prefiksie. Wykorzystano obiekty Double, aby zastosować rzutowanie
    w celu zapobieżenia błędom numerycznym wskutek dzielenia w klasie RandomWeighed.
     */
    private Map<String, Map<Character, Double>> prefiksy;
    private List<Character> pierwsze;   //Lista liter występujących na początku wyrazów - bez powtórzeń w obiekcie
    private double długość = 0;  //Długość pojedynczego słowa; liczbaSłów, długość - możliwa inicjalizacja w metodzie jako zmienne
    private double liczbaSłów = 0;  //Liczba słów w utworze, wykorzystywana do obliczenia maksymalnej długości słowa
    private String litery = "";   //Litery alfabetu
    private String nazwa = "Język ";  //Nazwa języka


    public JJęzyk(){   //Inicjalizacja obiektów
        this.prefiksy = new HashMap();
        this.pierwsze = new ArrayList();
    }


    /*
    Pomocnicza metoda analizująca dane słowo i uzupełniająca listę pierwszych liter. Każdy znak danego wyrazu jest dodawany
    do napisu, rozwijając prefiks, który będzie służyć za klucz w słowniku prefiksów. Wspomniana procedura umożliwia
    utworzenie długich prefiksów, co zwiększa prawdopodieństwo utworzenia poprawnych słów w danym języku pomimo znacznych
    kosztów pamięci. Równocześnie uzupełnia słownik znaków występujących po danym prefiksie, przyporządkowując częstość
    występowania poszczególnych znaków po określonym prefiksie, aktualizowaną za pomocą sumowania.
     */
    private void uczSię(String o, List<Character> c){  //Argumenty: słowo, lista liter alfabetu
        this.długość = this.długość+o.length();  //Sumuje długość wszystkich słów
        this.liczbaSłów++;   //Zwiększa liczbę przeanalizowanych do danej iteracji słów
        if(!this.pierwsze.contains(o.charAt(0))){  //Dodaje do listy pierwszych liter niewystępujące w obiekie znaki
            this.pierwsze.add(o.charAt(0));
        }
        String prefiks = "";   //Tworzy na bieżąco prefiks - możliwe utworzenie zwykłego słowa
        for(int i=0; i<o.length()-1; i++){  //-1 by nie przekroczyć długości słowa; pętla analizująca poszczególne znaki
            prefiks = prefiks+o.charAt(i);  //Wydłuża prefiks o następny znak
            //Dodaje litery do listy liter alfabetu tworzonego na podstawie pliku tekstowego - bez powtórzeń
            if(!c.contains(o.charAt(i))){c.add(o.charAt(i));}
            //Uwzględnia ostatnią literę wyrazu w liście liter alfabetu - pętla iteruje do przedostatniego znaku
            if(!c.contains(o.charAt(i+1))){c.add(o.charAt(i+1));}

            //Dodaje klucz
            if (this.prefiksy.containsKey(prefiks)){  //Jeśli słownik prefiksów zawiera dany prefiks
                Map<Character, Double> znaki = this.prefiksy.get(prefiks);  //Lista znaków występujących po danym prefiksie
                char znak = o.charAt(i+1); //Znak po pefiksie
                if (znaki.containsKey(znak)){  //Jeśli znak występował wcześniej - dodaje wartość
                    znaki.replace(znak, znaki.get(znak)+1);  //Znak, (poprzednia wartość dla danego znaku+1)

                }
                else{    //Jeśli znak nie występował wcześniej -
                    znaki.put(znak, 1.0);   //Dodaje znak
                }
            }
            else{   //Jeśli słownik nie zawiera danego prefiksu
                this.prefiksy.put(prefiks, new HashMap<Character, Double>());  //Inicjalizuje słownik dla danego prefiksu
                Map<Character, Double> kolejny = this.prefiksy.get(prefiks);   //Słownik częstości znaków dla danego prefiksu
                kolejny.put(o.charAt(i+1), 1.0);  //Dodaje następny znak do nowo utworzonego słownika dla danego prefiksu
            }
        }
    }

    /*Metoda analizująca kolejno poszczególne słowa danego pliku tekstowego; wczytuje plik tekstowy; zgłasza wyjątek
    w przypadku braku pliku bądź podania niepoprawnej nazwy.
     */

    public void uczSięZPliku(String nazwa){
        try {
            List<Character> abecadło = new ArrayList();  //Lista liter alfabetu danego języka
             /*Jeżeli tworzenie języka opieramy na analizie pliku tekstowego,
             poszczególne litery występujące w dziele uznamy za reprezentatywne dla alfabetu danej mowy. */
            Scanner obj = new Scanner(new File(nazwa));  //Tworzy obiekt Scanner wczytywanego pliku
            /*
            Jako separator wyznacza znaki nieuznawane w systemie Unicode za literę. Podane rozwiązanie nie ogranicza liter
            alfabetu w liście wyłącznie do znaków właściwych językowi, w którym utworzono plik tekstowy, a uwzględnia
            wszystkie znaki występujące w dziele, włącznie z wyrazami obcego zapisu. Pomimo przedstawionych zastrzeżeń
            rozwiązanie umożliwia analizowanie tekstów utworzonych w różnych językach.
             */
            obj.useDelimiter("\\P{L}+");
            while(obj.hasNext()){  //Dopóki występują słowa w danym pliku
                String słowo = obj.next().toLowerCase();  //Zapisuje dane słowo małymi literami
                uczSię(słowo, abecadło);  //Modyfikuje zarówno słownik, jak i listę liter alfabetu; analizuje słowo
            }
            alfabet(abecadło);  //Składa napis z liter alfabetu, modyfikuje atrybut
            /*Tworzy nazwę języka na podstawie najbardziej wszechstronnego w słowotwórstwie prefiksu - na podstawie
            liczby par klucz-wartość; wybiera prefiks z największym rozmiarem słownika
             */
            stwórzNazwę();
        }
        catch(Exception e){  //Wyświetla komunikat i kończy program w przypadku braku pliku
            System.out.println("Brak pliku");
            System.exit(0);

        }}

    public int długośćSłowa(){ //Liczy średnią, zaokrągla i dodaje dwa, by wyznaczyć maksymalną długość generowanych słów
        return (int)Math.round((długość)/(liczbaSłów))+2;
    }

    private void alfabet(List<Character> l){  //Składa napis liter alfabetu na podstawie listy niepowtarzalnych liter
        sort(l);  //Porządkuje listę alfabetycznie
        for (char c: l){
            this.litery=this.litery+c;
        }
    }

    public String literyAlfabetu(){return this.litery;}  //Zwraca napis złożony z liter alfabetu

    private void stwórzNazwę(){  //Tworzy nazwę języka na podstawie najbardziej słowotwórczo wszechstronnego prefiksu
        String naz = "";  //Inicjalizacja napisu - nazwy
        int rozm = 0;   //Inicjalizacja porównawczej wartości
        Map<String, Map<Character, Double>> p = this.prefiksy;  //Słownik prefiksów

        /*
        Jeżeli rozmiar słownika przyporządkowanego danemu prefiksowy jest większy niż wartość porównawcza, natomiast
        długość klucza prefiksu przekracza 4 znaki (zapewnienie minimalnej zadowalającej długości nazwy języka),
        następuje aktualizacja wartości porównawczej na rozmiar słownika oraz ustawienie nazwy na klucz prefiks
         */
        for (Map.Entry<String, Map<Character, Double>> para: p.entrySet()){  //Dla każdej pary prefiks: słownik znaków
            if(para.getValue().size()>rozm&&para.getKey().length()>4){naz = para.getKey(); rozm = para.getValue().size();}
        }

        //Zamiana pierwszej litery nazwy na wielką literę
        this.nazwa = this.nazwa+naz.substring(0,1).toUpperCase()+naz.substring(1);

    }

    public String nazwa(){return this.nazwa;}  //Zwraca nazwę języka

    public Map<Character, Double> częstość(String prefiks){  //Zwraca słownik częstości dla danego prefiksu
        return this.prefiksy.get(prefiks);
    }

    public List<Character> listaPierwszych(){return this.pierwsze;}  //Zwraca listę pierwszych liter

    public Map<String, Map<Character, Double>> słownikJęzykaAntypolskiego(){return this.prefiksy;}  //Zwraca słownik prefiksów


}
