import java.awt.GridLayout;
import javax.swing.JPanel;

public class TestDomino extends JPanel {
    TuileDomino t;
    Carrechiffre b00;
    Carrechiffre b01;
    Carrechiffre b02;
    Carrechiffre b10;
    Carrechiffre b11;
    Carrechiffre b12;
    Carrechiffre b20;
    Carrechiffre b21;
    Carrechiffre b22;
    Carrechiffre b30;
    Carrechiffre b31;
    Carrechiffre b32;


    TestDomino(TuileDomino tu){
        t = tu;
        setSize(25,25);
        b00 = new Carrechiffre(t.getNumeros()[0][0]);
        b01 = new Carrechiffre(t.getNumeros()[0][1]);
        b02 = new Carrechiffre(t.getNumeros()[0][2]);
        b10 = new Carrechiffre(t.getNumeros()[1][0]);
        b11 = new Carrechiffre(t.getNumeros()[1][1]);
        b12 = new Carrechiffre(t.getNumeros()[1][2]);
        b20 = new Carrechiffre(t.getNumeros()[2][0]);
        b21 = new Carrechiffre(t.getNumeros()[2][1]);
        b22 = new Carrechiffre(t.getNumeros()[2][2]);
        b30 = new Carrechiffre(t.getNumeros()[3][0]);
        b31 = new Carrechiffre(t.getNumeros()[3][1]);
        b32 = new Carrechiffre(t.getNumeros()[3][2]);
        setLayout(new GridLayout(5,5));
        for(int i = 0; i<25; i++){
            if(i== 1){
            add(b00);
            }else if(i == 2){
                add(b01);
            }
            else if(i == 3){
                add(b02);
            }
            else if(i == 5){
                add(b32);
            }else if(i == 9){
                add(b10);
            }else if(i == 10){
                add(b31);
            }else if(i == 14){
                add(b11);
            }else if(i == 15){
                add(b30);
            }else if(i == 19){
                add(b12);
            }
            else if(i == 21){
                add(b22);
            }else if(i == 22){
                add(b21);
            }else if(i == 23){
                add(b20);
            }else{
                add(new Carrechiffre());
            }
        }
    }

    void actualiser(){
        b00.setChiffre(t.getNumeros()[0][0]);
        b01.setChiffre(t.getNumeros()[0][1]);
        b02.setChiffre(t.getNumeros()[0][2]);
        b10.setChiffre(t.getNumeros()[1][0]);
        b11.setChiffre(t.getNumeros()[1][1]);
        b12.setChiffre(t.getNumeros()[1][2]);
        b20.setChiffre(t.getNumeros()[2][0]);
        b21.setChiffre(t.getNumeros()[2][1]);
        b22.setChiffre(t.getNumeros()[2][2]);
        b30.setChiffre(t.getNumeros()[3][0]);
        b31.setChiffre(t.getNumeros()[3][1]);
        b32.setChiffre(t.getNumeros()[3][2]);

    }
    
}
