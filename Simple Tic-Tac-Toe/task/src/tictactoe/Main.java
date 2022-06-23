package tictactoe;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        String input_;
//        Scanner scanner = new Scanner(System.in);
//        input_ = scanner.next();
        String input_ = "_________";
        int horiz_length = 3;
        int vertical_length = 3;
        int total_size = horiz_length*vertical_length;

        char[][] twoDimArray = new char[horiz_length][vertical_length];
        char[][] board = make_board_from_2d_arr(input_, twoDimArray);
        String answer = "Game not finished";
        char player = 'X';
        print_top_cover();
        print_board_from_2d_arr(board);
        print_bottom_cover();
        while (answer == "Game not finished") {
            play_move(board, player);
            answer = checkBoard(board);
            player = changeplayer(player);
            print_top_cover();
            print_board_from_2d_arr(board);
            print_bottom_cover();
        }
        System.out.println(answer);

    }

    private static char changeplayer(char player) {
        switch (player){
            case 'X':
                player = 'O';
                break;
            case 'O':
                player = 'X';
                break;
        }
        return player;
    }

    static char[][] play_move(char[][] board, char player){
        boolean move_not_implemented = true;
        while (move_not_implemented){
        int[] user_input = get_user_input();
        boolean move_validation;
        if (two_number_were_given(user_input)) {
            move_validation = validate_input(board, user_input);
        }
        else {System.out.println("You should enter numbers!"); continue;}

        if (move_validation) {
            int[] user_move = preproc_move(user_input);
            board = implement_move(board, user_move, player);
            move_not_implemented = false;

        };}
        return board;
    }

    private static boolean two_number_were_given(int[] user_input) {
        if (user_input[2] == 2){ return true;}
        return false;
    }

    private static char[][] implement_move(char[][] board, int[] user_move, char player) {
        board[user_move[0]][user_move[1]] = player;
        return board;
    }

    private static int[] preproc_move(int[] user_input) {
        int i = user_input[0];
        int j = user_input[1];
        i = i - 1;
        j = j - 1;
        int[] user_move = new int[2];
        user_move[0] = i;
        user_move[1] = j;
        return user_move;
    }

    private static boolean validate_input(char[][] board, int[] user_input) {

        int i = user_input[0];
        int j = user_input[1];
        if (((i<=3) && (i>=1)) && ((j<=3) && (j>=1))) {
            if (board[i-1][j-1] == '_'){ // -1 since the board is from 0 to 2 but user input is 1 to 3
                return true;
            }
            else {System.out.println("This cell is occupied! Choose another one!");}

        }
        else {System.out.println("Coordinates should be from 1 to 3!");}
        return false;
    }

    private static int[] get_user_input() {
        int[] input_ = new int[3];
        input_[0] = -1;
        input_[1] = -1;
        input_[2] =  0;
        Scanner scanner = new Scanner(System.in);
        if(scanner.hasNextInt()) {
            input_[0] = scanner.nextInt();
            input_[2] += 1;

        }
        if(scanner.hasNextInt()) {
            input_[1] = scanner.nextInt();
            input_[2] += 1;
        }
        return input_;
    }

    static void print_board_from_2d_arr(char[][] board){
        // length of the rows in the array
        int vertical_length = board.length;
        // length of the cols in the array
        int horiz_length = board[0].length;
        for (int i = 0; i < vertical_length; i++) {
            System.out.print("| ");  // begging of line
            for (int j = 0; j < horiz_length; j++) {
                System.out.print(board[i][j]);
                System.out.print(" ");
            }
            System.out.print("|\n"); // close line
        };
    }
    static char[][] make_board_from_2d_arr(String input_, char[][] twoDimArray){
        // length of the rows in the array
        int vertical_length = twoDimArray.length;
        // length of the cols in the array
        int horiz_length = twoDimArray[0].length;
        int k = 0;
        for (int i = 0; i < vertical_length; i++) {
            for (int j = 0; j < horiz_length; j++) { // write line content
                twoDimArray[i][j] = input_.charAt(k); // init board
                k++;
            }
        } return twoDimArray;
    }

    static void print_line(){
        System.out.println("---------");
    }
    static void print_top_cover() {
        print_line();
    }

    static void print_bottom_cover() {
        print_line();
    }
    static String checkBoard(char[][] board) {
        String answer = "Impossible";
        boolean xWinner;
        boolean OWinner;

        xWinner = isXWins(board);
        OWinner = isOWins(board);
        if (xWinner) {
            answer = "X wins";
        }
        ;
        if (OWinner) {
            answer = "O wins";
        }
        ;
        if (!xWinner && !OWinner) {
            if (isFinished(board) == "Game not finished")
            {answer = "Game not finished";}
            else {answer = "Draw";};
            ;
        }
        if (xWinner && OWinner) {
            answer = "Impossible";
        };
        int Xoccurance = countOccurrencesOf(board, 'X');
        int Ooccurance = countOccurrencesOf(board, 'O');
        double diff = Math.abs(Xoccurance - Ooccurance);
        if (diff > 1) {
            answer = "Impossible";
        }
        return answer;
    };

    static int countOccurrencesOf(char[][] board, char c)
    {int count = 0;
        for (int j = 0; j<board[0].length; j++){
            for (int i = 0; i <board.length; i++){
            if (board[i][j] == c) {
                count++;
            }
        }
        }return count;}

//    private static boolean GameNotFinished(char[] array) {
//        for (char c : array) {
//            if (c == ' ' || c == '_') {
//                return true;
//            }
//        }
//        return false;
//    }
    static String isFinished(char[][] board) {
        String answer = null;
        for (int j = 0; j<board[0].length; j++)
        {
            for (int i = 0; i <board.length; i++)
            {
                if (board[i][j] == '_')
                {
                    answer = "Game not finished";
                }
            }
        }
        return answer;
    };

    static Boolean checkRows(char[][] board, char symbol)
    {

        boolean wins_local = false;
        for (int i=0; i < board.length; i++) {
            char a = board[i][0];
            if (a != symbol) {
                break;
            }
            char b = board[i][1];
            char c = board[i][2];;
            wins_local = ((a == b) && (b == c));
            if (wins_local) {
                break;
            }
            ;
            }return wins_local;};

    static Boolean checkCols(char[][] board, char symbol)
    {
        boolean wins_local = false;
        for (int j=0; j < board[0].length; j++) {
            char a = board[0][j];
            if (a != symbol) {
                continue;
            }
            char b = board[1][j];
            char c = board[2][j];
            wins_local = ((a == b) && (b == c));
            if (wins_local) {
                break;
            }
            ;
        }return wins_local;};

    static Boolean checkDiags(char[][] board, char symbol)
    {
        int i = 0;
        char center = board[1][1];
        boolean wins_local = false;

        if (center != symbol) {return wins_local;}
        char a = board[0][0];
        char c = board[2][2];
        wins_local = ((a == center) && (center == c));
        if (wins_local) {
            return wins_local;
            };
        // check toher diagonal
        a = board[0][2];
        c = board[2][0];
        wins_local = ((a == center) && (center == c));
        return wins_local;
        };

    static Boolean isXWins(char[][] board) {
        // check rows:
        boolean wins = false;
        // check rows
        boolean wins_local = checkRows(board, 'X');
        boolean wins_local2 = checkCols(board, 'X');
        // check col
        // check diags
        boolean wins_local3 = checkDiags(board, 'X');
        wins = ((wins_local || wins_local2) || wins_local3);
        return wins;
    };

    static Boolean isOWins(char[][] board) {
        // check rows:
        boolean wins = false;
        // check rows
        boolean wins_local = checkRows(board, 'O');
        boolean wins_local2 = checkCols(board, 'O');
        // check col
        // check diags
        boolean wins_local3 = checkDiags(board, 'O');
        wins = ((wins_local || wins_local2) || wins_local3);
        return wins;
    }; }


