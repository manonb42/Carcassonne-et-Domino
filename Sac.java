// import java.util.Arrays;

public class Sac {
    private int nbPiecesRestantes;
    private Piece[] pieces;


    public Sac(int nbPieces){
        this.nbPiecesRestantes=nbPieces;
        this.pieces=new Piece[nbPieces];
        for(int i=0; i<nbPieces; i++){
            this.pieces[i]=genererPiece();
        }
    }

    public int getPiecesRestantes(){
        return this.nbPiecesRestantes;
    }

    public Piece genererPiece(){
        int tab[][]= new int[4][3];
        Piece p=new Piece(tab);
        for(int i=0; i<p.getNumeros().length; i++){
            for(int j=0; j<p.getNumeros()[i].length; j++){
                p.setNumeros(i, j, (int)(Math.random() * 4));
            }
        }
        return p;
    }

    public Piece piocher(){
        nbPiecesRestantes--;
        Piece p=this.pieces[nbPiecesRestantes];
        this.pieces[nbPiecesRestantes]=null;
        return p;
    }
    

    /* 
    public static void main(String[] args){
        Sac sac = new Sac(4);
        System.out.println(Arrays.toString(sac.piocher().getNumeros()[0]));
        System.out.println(Arrays.toString(sac.piocher().getNumeros()[1]));
        System.out.println(Arrays.toString(sac.piocher().getNumeros()[2]));
        System.out.println(Arrays.toString(sac.piocher().getNumeros()[3]));

        
        Piece pieces[]=new Piece[0];
        Sac sac = new Sac(3);
        System.out.println(Arrays.toString(sac.pieces[0].getNumeros()[3]));
        
        for(int i=0; i<4; i++){
            System.out.println(Arrays.toString(sac.genererPiece().getNumeros()[i]));
        }  
    }*/
}
