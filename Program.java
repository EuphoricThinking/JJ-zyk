package Program;

import Generacja.Generator;
import Generacja.JJęzyk;

public class Program {
    public static void test(JJęzyk j, String nazwa){
        j.uczSięZPliku(nazwa);
        System.out.println("Proszę Państwa, dzisiaj komunikujemy się w narzeczu: "+j.nazwa());
        Generator g = new Generator(j);
        String g1 = g.dajSłowo();
        System.out.print(g1.substring(0,1).toUpperCase()+g1.substring(1)+" ");  //Piwersze słowo - wielką literą
        for (int i=15; i>0; i--){
            System.out.print(g.dajSłowo()+" ");
        }
        System.out.print(g.dajSłowo());  //Usuwa przerwę między słowem a znakiem interpunkcyjnym
        System.out.print(", ");
        for (int i=11; i>0; i--){
        System.out.print(g.dajSłowo()+" ");}
        System.out.print(g.dajSłowo());
        System.out.print("? ");   //Pytanie retoryczne
        String g2 = g.dajSłowo();
        System.out.print(g2.substring(0,1).toUpperCase()+g2.substring(1)+" ");  //Nowe zdanie - wielką literą
        for (int i=0; i<6; i++){
            System.out.print(g.dajSłowo()+ " ");
        }
        System.out.print(g.dajSłowo());
        System.out.println("!");

        System.out.println("Na podstawie dzieła ,,"+nazwa+"\" przedstawiamy Państwu działanie programu: "+g.nazwa());
        System.out.println();


    }

    public static void main(String[] args){
        Program p = new Program();
        p.test(new JJęzyk(), "Orzeszkowa2");  //Język polski, drugi tom ,,Nad Niemnem", wolnelektury.pl
        p.test(new JJęzyk(), "Amos");         //Język hebrajski, artykuł o Amosie Ozie, wikipedia.org
        p.test(new JJęzyk(), "Arabala");      //Język arabski, artykuł o Malali Yousafzai, wikipedia.org
        p.test(new JJęzyk(), "Keczua");       //Język keczuański, artykuł o nie wiadomo czym, wikipedia.org
        p.test(new JJęzyk(), "Oracle");       //Język angielski, interfejsy List i Map, docs.oracle.com
        p.test(new JJęzyk(), "Uśmiech!");     //Zamierzony brak pliku przedstawiający obsługiwanie wyjątków

        //Zachęcam do dokładniejszej lektury powyższych przykładowych dzieł literackich

    }

}
