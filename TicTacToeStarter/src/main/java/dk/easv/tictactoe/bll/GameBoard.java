
package dk.easv.tictactoe.bll;

/**
 *
 * @author EASV
 */
public class GameBoard implements IGameBoard
{
    private static final int BOARD_SIZE = 3; //3x3 board
    private static final int EMPTY = -1; //Empty cell

    private int [][] board;
    private int currentPlayer;
    private boolean gameOver;
    private int winner;

    public GameBoard() { newGame();}

    /**
     * Returns 0 for player 0, 1 for player 1.
     *
     * @return int Id of the next player.
     */
    public int getNextPlayer() {return (currentPlayer == 0) ? 1 : 0;}


    /**
     * Attempts to let the current player play at the given coordinates. It the
     * attempt is succesfull the current player has ended his turn and it is the
     * next players turn.
     *
     * @param col column to place a marker in.
     * @param row row to place a marker in.
     * @return true if the move is accepted, otherwise false. If gameOver == true
     * this method will always return false.
     */
    public boolean play(int col, int row)
    {
        //stops game if there is no legal moves left
        if (isGameOver()){
            return false;
        }

        //Check if move is legal
        if (isValidMove(col, row)){
            board[col][row] = currentPlayer;

            if (checkWinCondition(col, row)){
                gameOver = true;
                winner = currentPlayer;
            }
            else if (checkDraw()) {
                gameOver = true;
                winner = -1;
            }
            else {
                currentPlayer = getNextPlayer();
            }
            return true;
        }
        return false;
    }

    /**
     * Tells us if the game has ended either by draw or by meeting the winning
     * condition.
     *
     * @return true if the game is over, else it will retun false.
     */
    public boolean isGameOver() {return gameOver;}

    /**
     * Gets the id of the winner, -1 if its a draw.
     *
     * @return int id of winner, or -1 if draw.
     */
    public int getWinner() {return winner;}

    /**
     * Resets the game to a new game state.
     */
    public void newGame()
    {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++){
            for (int j = 0; j < BOARD_SIZE; j++){
                board[i][j] = EMPTY;
            }
        }

        currentPlayer = 0;
        gameOver = false;
        winner = -1;
    }
    private boolean isValidMove(int col, int row){
        return col >= 0 && col < BOARD_SIZE && row >= 0 && row < BOARD_SIZE && board[col][row] == EMPTY;
    }

    private boolean checkWinCondition(int col, int row){
        return  checkRow(row) || checkColum(col) || checkDiagonals();
    }
    private boolean checkRow(int row) {
        for (int i = 1; i < BOARD_SIZE; i++) {
            if (board[i][row] != board[0][row] || board[0][row] == EMPTY) {
                return false;
            }
        }
        return true;
    }
    private boolean checkColum(int col){
        for (int i = 1; i < BOARD_SIZE; i++){
            if (board[col][i] != board[col][0] || board[col][0] == EMPTY){
                return false;
            }
        }
        return true;
    }
    //checks if diag align
    private boolean checkDiagonals(){
        boolean checkDiag1 = true, checkDiag2 = true;
        for (int i = 1; i < BOARD_SIZE; i++){
            if (board[i][i] != board[0][0] || board[0][0] == EMPTY){
                checkDiag1 = false;
            }
            if (board[i][BOARD_SIZE - i - 1] != board[0][BOARD_SIZE - 1] || board[0][BOARD_SIZE - 1] == EMPTY){
                checkDiag2 = false;
            }
        }

        return checkDiag1 || checkDiag2;
    }

    // checks if the game ended in a draw
    private boolean checkDraw(){
        for (int i = 1; i < BOARD_SIZE; i++){
            for (int j = 1; j < BOARD_SIZE; j++){
                if (board[i][j] == EMPTY){
                    return false;
                }
            }
        }
        return true;
    }
}
