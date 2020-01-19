import java.util.*;
class Game{
        int board[][]=new int[4][4];
        int score=0;
        boolean isover=false;
        int arr[]={2,4,8,16,32,64,128};
        public Game(){
            Random r=new Random();
            int i=Math.abs(r.nextInt())%4;
            int j=Math.abs(r.nextInt())%4;
            board[i][j]=2;
            i=Math.abs(r.nextInt())%4;
            j=Math.abs(r.nextInt())%4;
            board[i][j]=2;
        }
        public boolean isOver(){
            return isover;
        }
        public void clearscreen(){
            try{
                new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            }
            catch(Exception e)
            {

            }
        }
        public void display(){
            clearscreen();
            System.out.println("SCORE : "+score);
            for(int i=0;i<4;i++){
                for(int j=0;j<4;j++){
                    System.out.print(board[i][j]+"\t |");
                }
                System.out.println();
            }
        }
        public boolean checkBoard(){
            for(int i=0;i<4;i++){
                for(int j=0;j<4;j++)
                    if(board[i][j]==0)
                        return true;
            }
            return false;
        }
        public void generateNewIndex(){
            boolean found=true;
            if(checkBoard()){
                while(found){
                    Random r=new Random();
                    int i=Math.abs(r.nextInt())%4;
                    int j=Math.abs(r.nextInt())%4;
                    if(board[i][j]==0){
                        int m=arr[r.nextInt(arr.length-1)];
                        if(m>calculateMax())
                            m=calculateMax();
                        board[i][j]=m;
                        found=false;
                    }
                }
            }
        }
        public void moveLeft(){
            for(int i=0;i<4;i++){
                for(int j=0;j<4;j++){
                    if(board[i][j]==0){
                        for(int k=j+1;k<4;k++){
                            if(board[i][k]!=0){
                                board[i][j]=board[i][k];
                                board[i][k]=0;
                                break;
                            }
                        }
                    }
                }
            }
        }
        public void moveRight(){
            for(int i=0;i<4;i++){
                for(int j=3;j>=0;j--){
                    if(board[i][j]==0){
                        for(int k=j-1;k>=0;k--){
                            if(board[i][k]!=0){
                                board[i][j]=board[i][k];
                                board[i][k]=0;
                                break;
                            }
                        }
                    }
                }
            }
        }
        public void moveUp(){
            for(int i=0;i<4;i++){
                for(int j=0;j<4;j++){
                    if(board[j][i]==0){
                        for(int k=j+1;k<4;k++){
                            if(board[k][i]!=0){
                                board[j][i]=board[k][i];
                                board[k][i]=0;
                                break;
                            }
                        }
                    }
                }
            }
        }
        public void moveDown(){
            for(int i=0;i<4;i++){
                for(int j=3;j>=0;j--){
                    if(board[j][i]==0){
                        for(int k=j-1;k>=0;k--){
                            if(board[k][i]!=0){
                                board[j][i]=board[k][i];
                                board[k][i]=0;
                                break;
                            }
                        }
                    }
                }
            }
        }
        public void sumLeft(){
            for(int i=0;i<4;i++){
                for(int j=0;j<3;j++){
                    if(board[i][j]!=0 && board[i][j]==board[i][j+1]){
                        board[i][j]*=2;
                        board[i][j+1]=0;
                        score+=board[i][j];
                        moveLeft();
                    }
                }
            }
        }
        public void sumRight(){
            for(int i=0;i<4;i++){
                for(int j=3;j>0;j--){
                    System.out.println(i+" "+j);
                    if(board[i][j]!=0 && board[i][j]==board[i][j-1]){
                        board[i][j]*=2;
                        board[i][j-1]=0;
                        score+=board[i][j];
                        moveRight();
                    }
                }
            }
        }
        public void sumUp(){
            for(int i=0;i<4;i++){
                for(int j=0;j<3;j++){
                    if(board[j][i]!=0 && board[j][i]==board[j+1][i]){
                        board[j][i]*=2;
                        board[j+1][i]=0;
                        score+=board[j][i];
                        moveUp();
                    }
                }
            }
        }
        public void sumDown(){
            for(int i=0;i<4;i++){
                for(int j=3;j>0;j--){
                    if(board[j][i]!=0 && board[j][i]==board[j-1][i]){
                        board[j][i]*=2;
                        board[j-1][i]=0;
                        score+=board[j][i];
                        moveDown();
                    }
                }
            }
        }
        public int calculateMax(){
            int max=0;
            for(int i=0;i<4;i++){
                for(int j=0;j<4;j++){
                    if(board[i][j]>max)
                        max=board[i][j];
                }
            }
            return max;
        }
        public void win(){
            System.out.println("HURRAY!!!!! You won the game");
        }
        public void lose(){
            System.out.println("Game Over!! Better Luck Next Time");
        }
        public void playGame(char ch){
            if(calculateMax()==2048){
                isover=true;
                win();
                System.exit(0);
            }
            if(!checkBoard()){
               isover=true;
                lose();
                System.exit(0);
            }
            switch(ch){
                case 'a':
                case 'A':
                moveLeft();
                sumLeft();
                break;
                case 'd':
                case 'D':
                moveRight();
                sumRight();
                break;
                case 's':
                case 'S':
                moveDown();
                sumDown();
                break;
                case 'w':
                case 'W':
                moveUp();
                sumUp();
                break;
                default:
                System.out.println("Invalid Move");
            }
            generateNewIndex();
            
            
        }
}

class Main{
    public static void main(String args[]){
        Game g=new Game();
        Scanner sc=new Scanner(System.in);
        while(!g.isOver()){
            g.display();
            char ch=sc.next().charAt(0);
            g.playGame(ch);
        }
    }
}