public class nQueens {

    //check to make sure that
    public static void main(String[] args) {
        int[] aSolution = {1, 6, 8, 3, 7, 4, 2, 5};
        int[] queenPositions = {1, 6, 8, 2, 4, 7, 3, 0};
        int[] emptyBoard = {1, 3, 5, 8, 2, 4, 6, 0};
        printChessboard(nextLegalPosition(initializeBoard(emptyBoard, 8), 0));

    }

    // Question 1 Methods

    /**
     * Given a chessboard and an array containing the positions of the queens present on a board
     * determine if the current orientation of the board has no queens threatening one another
     *
     * @param board 2D array representing the current orientation of the chess board
     * @return a boolean if the position is legal or not
     */
    public static boolean isLegalPosition(int[][] board) {

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

        //check column = -row diagonal
        for (int i = row, j = column; i >= 0 && j >= 0; i--, j--) {
            if (i != row && board[i][j] == 1) {
                return true;
            }
        }

        //check column = row diagonal
        for (int i = row, j = column; i >= 0 && j < board.length; i--, j++) {
            if (i != row && board[i][j] == 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * function that prints out the array representation of a chessboard
     *
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

    //Question 2 Methods

    /**
     * @param board 2D array representation of a chessboard
     * @param n     row we are checking
     * @return      returns the next legal position in the form of an array
     */
    public static int[][] nextLegalPosition(int[][] board, int n) {
        queenPos lastQueen = getQueenPos(board, board.length - 1);

        int nextRow = lastQueen.getRow();

        if (nextRow < board.length - 1 && isLegalPosition(board)) {
            //I can foresee an issue here just check here if anything goes wrong
            return getNextLegalPosition(board, nextRow + 1);
        }

        return getNextLegalPosition(board, n);
    }

    /**
     * @param board 2D representation of the current orientation of the chess board
     * @param row the row of the chess board that we are looking at
     * @return the chessboard after being modified
     */
    public static int[][] getNextLegalPosition(int[][] board, int row) {
        int column = -1;
        queenPos lastPos = getQueenPos(board, board.length - 1);

        if (row <= lastPos.row) {
            column = lastPos.column;
        }

        if (column == -1) {
            for (int i = 0; i < board.length; i++) {
                if (!isQueenThreatened(board, row, i)) {
                    board[row][i] = 1;
                    return board;
                }
            }

            board[lastPos.row][lastPos.column] = 0;
            return getNextLegalPosition(board, lastPos.row - 1);
        }

        //if row isn't empty we want to go back to the previous row and remove the queen
        board[lastPos.row][lastPos.column] = 0;

        //now we iterate through this row
        for (int i = 0; i < board.length; i++) {
            if (!isQueenThreatened(board, row, i)) {
                if (i != lastPos.column) {
                    board[row][i] = 1;
                    return board;
                }
            }
        }
        return getNextLegalPosition(board, row - 1);
    }

    /**
     * class to hold the row and column position of a queen
     */
    public static class queenPos {
        int row;
        int column;

        public queenPos(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }
    }

    /**
     * gets the position of the last queen through recursion
     *
     * @param board current orientation of the chess board
     * @param n     row that we are looking at, when calling we want to use board.length - 1 to get the last row
     * @return a queenPos class with the row and column stored within them
     */
    public static queenPos getQueenPos(int[][] board, int n) {
        int column = -1;

        queenPos lastQueenPos = new queenPos(-1, -1);

        //check for queen in row
        for (int i = 0; i < board.length; i++) {
            if (board[n][i] == 1) {
                column = i;
            }
            lastQueenPos.row = n;
            lastQueenPos.column = column;
        }

        if (column == -1) {
            lastQueenPos = getQueenPos(board, n - 1);
        }

        return lastQueenPos;
    }
}
