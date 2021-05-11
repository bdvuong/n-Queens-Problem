public class nQueens {

    public static void main(String[] args) {
        int[] aSolution = {1, 6, 8, 3, 7, 4, 2, 5};
        int[] queenPositions = {1, 6, 8, 3, 5, 0, 0, 0};
        isLegalPosition(aSolution, 8);
        isLegalPosition(queenPositions, 8);
    }

    /**
     * Given a chessboard and an array containing the positions of the queens present on a board
     * determine if the current orientation of the board has no queens threatening one another
     * @param queenPositions 1D array containing the positions of queens in a finished/unfinished board
     * @param n size of the chessboard
     * @return a boolean if the position is legal or not
     */
    public static boolean isLegalPosition(int[] queenPositions, int n) {
        //initialize the board
        int[][] board = initializeBoard(queenPositions, n);

        //iterate through the chess board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 1) {
                    if (isQueenThreatened(board, i, j)) {
                        System.out.println("Illegal Position");
                        return false;
                    }
                }
            }
        }
        System.out.println("Legal Position");
        return true;
    }

    /**
     * helper function that creates an n*n matrix that will represent the chess board that will be analyzed
     *
     * @param queenPositions Array listing the positions of the queens, where the index indicates the row that the queen is on
     * @param n              size of the board
     * @return n*n array which represents a chess board
     */
    public static int[][] initializeBoard(int[] queenPositions, int n) {
        //create n*n array to act as the chess board
        int[][] chessboard = new int[n][n];

        //initialize board with 0s in the entirety of the array
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                chessboard[i][j] = 0;
            }
        }

        //add queen positions onto the board
        for (int i = 0; i < queenPositions.length; i++) {
            //used to zero index the position of the queens
            int column = queenPositions[i] - 1;

            //if i returns a -1, that means that there is no queen in that row
            if (column != -1) {
                chessboard[i][column] = 1;
            }
        }
        printChessboard(chessboard);
        return chessboard;
    }

    /**
     * helper function that checks the chess board to see if there are any conflicting queens
     *
     * @param board  n*n chessboard that we are iterating through to make sure there are no queens threatening each other
     * @param row    queen's row position that we are checking
     * @param column queen's column position that we are checking
     * @return a boolean if the queen given is threatened by another queen
     */
    public static boolean isQueenThreatened(int[][] board, int row, int column) {
        //only need to check rows above the queen because logically the rows above the new row have gone
        //through this method already

        // check the same row of the queen
        for (int i = 0; i < board.length; i++) {
            if (i != column && board[row][i] == 1) {
                return true;
            }
        }

        // check the same column
        for (int i = 0; i < row; i++) {
            if (board[i][column] == 1) {
                return true;
            }
        }

        //check y = -x diagonal
        for (int i = row, j = column; i >= 0 && j >= 0; i--, j--) {
            if (i != row && board[i][j] == 1) {
                return true;
            }
        }

        //check y = x diagonal
        for (int i = row, j = column; i >= 0 && j < board.length; i--, j++) {
            if (i != row && board[i][j] == 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * function that prints out the array representation of a chessboard
     * @param board 2D array representation of a chessboard we want to display
     */
    public static void printChessboard(int[][] board) {
        System.out.print("\n");
        for (int[] num : board) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(num[j] + " ");
            }
            System.out.println();
        }
        System.out.print("\n");
    }
}
