package lk.ijse.dep.service;

public class AiPlayer extends Player{

    public AiPlayer(Board board) {
        super(board);
    }
    @Override
    public void movePiece(int col) {
        Piece[][] tempBoard=board.getPieces();
        int bestScore=Integer.MIN_VALUE;
        int row=0;
        for (int c=0;c<6;c++) {
            for (int r = 0; r < 5; r++) {
                if (tempBoard[c][r]==Piece.EMPTY){
                    tempBoard[c][r]=Piece.GREEN;
                    int score=minimax(tempBoard,0,false);
                    tempBoard[c][r]=Piece.EMPTY;
                    if (bestScore<score){
                        bestScore=score;
                        col=c;
                        row=r;
                    }
                }
            }
        }

        board.updateMove(col,row,Piece.GREEN);
        board.getBoardUI().update(col,false);
        if(board.findWinner()==null){
            if (board.existLegalMoves()){
                board.getBoardUI().notifyWinner(new Winner(Piece.EMPTY));
            }
        }else{
            board.getBoardUI().notifyWinner(board.findWinner());
        }

    }

    public int minimax(Piece[][] tempBoard,int depth,boolean maximizingPlayer){
        String winner=getWinner(tempBoard);
        if (depth==4 || winner != null){
            if(winner==null){
                return 0;
            }
            if (winner.equals("player")){
                return -5;
            }
            if (winner.equals("Ai")){
                return 5;
            }
            if (winner.equals("tie")){
                return 0;
            }
        }
        if (maximizingPlayer){
            int bestScore=Integer.MIN_VALUE;
            for (int r=0;r<5;r++){
                for (int c=0;c<6;c++){
                    if (tempBoard[c][r]==Piece.EMPTY){
                        tempBoard[c][r]=Piece.GREEN;
                        int score=minimax(tempBoard,depth+1,false);
                        tempBoard[c][r]=Piece.EMPTY;
                        bestScore=Math.max(bestScore,score);
                    }
                }
            }
            return bestScore;
        }else{
            int bestScore=Integer.MAX_VALUE;
            for (int r=0;r<5;r++){
                for (int c=0;c<6;c++){
                    if (tempBoard[c][r]==Piece.EMPTY){
                        tempBoard[c][r]=Piece.BLUE;
                        int score=minimax(tempBoard,depth+1,true);
                        tempBoard[c][r]=Piece.EMPTY;
                        bestScore=Math.min(bestScore,score);
                    }
                }
            }
            return bestScore;
        }
    }
    public String getWinner(Piece[][] tempBoard) {
        String winner = null;
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 3; c++) {
                if (tempBoard[c][r] == Piece.BLUE && tempBoard[c + 1][r] == Piece.BLUE && tempBoard[c + 2][r] == Piece.BLUE && tempBoard[c + 3][r] == Piece.BLUE) {
                    winner = "player";
                } else if (tempBoard[c][r] == Piece.GREEN && tempBoard[c + 1][r] == Piece.GREEN && tempBoard[c + 2][r] == Piece.GREEN && tempBoard[c + 3][r] == Piece.GREEN) {
                    winner = "Ai";
                }
            }
        }
        for (int c = 0; c < 6; c++) {
            for (int r = 0; r < 2; r++) {
                if (tempBoard[c][r] == Piece.BLUE && tempBoard[c][r + 1] == Piece.BLUE && tempBoard[c][r + 2] == Piece.BLUE && tempBoard[c][r + 3] == Piece.BLUE) {
                    winner = "player";
                } else if (tempBoard[c][r] == Piece.GREEN && tempBoard[c][r + 1] == Piece.GREEN && tempBoard[c][r + 2] == Piece.GREEN && tempBoard[c][r + 3] == Piece.GREEN) {
                    winner = "Ai";
                }
            }
        }
        int empty = 0;
        for (int c = 0; c < 6; c++) {
            for (int r = 0; r < 5; r++) {
                if (tempBoard[c][r] == Piece.EMPTY) {
                    empty++;
                }
            }
        }
        if (winner == null && empty == 0) {
            return "tie";
        } else {
            return winner;
        }
    }
}

