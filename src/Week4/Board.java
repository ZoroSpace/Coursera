package Week4;

/**
 * Created by Zoro on 17-7-10.
 */
public class Board {
    private int boardDimension;
    private int[][] blocks;
    public Board(int[][] blocks) {
        this.boardDimension = blocks.length;
        this.blocks = new int[boardDimension][boardDimension];
        System.arraycopy(blocks,0,this.blocks,0, boardDimension);
    }

    public int dimension() {
        return boardDimension;
    }

    public int hamming() {
        int hammingCounter = 0;
        for (int i = 0; i < boardDimension; i++) {
            for (int j = 0; j < boardDimension; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != i * boardDimension + j + 1) {
                    hammingCounter++;
                }
            }
        }
        return hammingCounter;
    }

    public int manhattan() {
        int manhattanCounter = 0;
        for (int i = 0; i < boardDimension; i++) {
            for (int j = 0; j < boardDimension; j++) {
                if (blocks[i][j] != 0) {
                    if (blocks[i][j] % boardDimension == 0) {
                        manhattanCounter += Math.abs(blocks[i][j]/boardDimension - i - 1) + Math.abs(boardDimension - 1 - j);
                    } else {
                        manhattanCounter += Math.abs(blocks[i][j]/boardDimension - i) + Math.abs(blocks[i][j]%boardDimension - 1 - j);
                    }
                }
            }
        }
        return manhattanCounter;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public Board twin() {
        int[][] newBlock = new int[boardDimension][boardDimension];
        if (newBlock[0][0] != 0 && newBlock[0][1] != 0) {
            int i = newBlock[0][0];
            newBlock[0][0] = newBlock[0][1];
            newBlock[0][1] = i;
        } else {
            int i = newBlock[1][0];
            newBlock[1][0] = newBlock[1][1];
            newBlock[1][1] = i;
        }

        return new Board(newBlock);
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < boardDimension; i++) {
            for (int j = 0; j < boardDimension; j++) {
                s = s + String.format("%4d",blocks[i][j]);
            }
            s = s + "\n";
        }

        return s;
    }

    public static void main(String[] args) {

//        int[][] blocks = new int[2][2];
//        blocks[0][0] = 2;
//        blocks[0][1] = 1;
//        blocks[1][0] = 3;
//        blocks[1][1] = 0;

//        int[][] blocks = new int[2][2];
//        blocks[0][0] = 1;
//        blocks[0][1] = 2;
//        blocks[1][0] = 3;
//        blocks[1][1] = 0;

//        int[][] blocks = new int[3][3];
//        blocks[0][0] = 8;
//        blocks[0][1] = 1;
//        blocks[0][2] = 3;
//        blocks[1][0] = 4;
//        blocks[1][1] = 0;
//        blocks[1][2] = 2;
//        blocks[2][0] = 7;
//        blocks[2][1] = 6;
//        blocks[2][2] = 5;

        int[][] blocks = new int[3][3];
        blocks[0][0] = 1;
        blocks[0][1] = 2;
        blocks[0][2] = 3;
        blocks[1][0] = 4;
        blocks[1][1] = 5;
        blocks[1][2] = 6;
        blocks[2][0] = 7;
        blocks[2][1] = 8;
        blocks[2][2] = 0;
        Board board = new Board(blocks);
        System.out.println(board);
    }
}
