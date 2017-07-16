package Week4;

import edu.princeton.cs.algs4.Queue;

/**
 * Created by Zoro on 17-7-10.
 */
public class Board {
    private int boardDimension;
    private int[][] blocks;
    private int hammingValue = -1;
    private int manhattanValue = -1;
    public int step = 0;
    public Board(int[][] blocks) {
        this.boardDimension = blocks.length;
        this.blocks = new int[boardDimension][boardDimension];
        for (int i = 0; i < boardDimension; i++) {
            for (int j = 0; j < boardDimension; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
    }

    public int dimension() {
        return boardDimension;
    }

    public int hamming() {
        if (hammingValue == -1) {
            int hammingCounter = 0;
            for (int i = 0; i < boardDimension; i++) {
                for (int j = 0; j < boardDimension; j++) {
                    if (blocks[i][j] != 0 && blocks[i][j] != i * boardDimension + j + 1) {
                        hammingCounter++;
                    }
                }
            }
            hammingValue = hammingCounter;
        }
        return hammingValue;

    }

    public int manhattan() {
        if (manhattanValue == -1) {
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
            manhattanValue = manhattanCounter;
        }
        return manhattanValue;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public Board twin() {
        int[][] newBlock = new int[boardDimension][boardDimension];
        for (int i = 0; i < boardDimension; i++) {
            for (int j = 0; j < boardDimension; j++) {
                newBlock[i][j] = blocks[i][j];
            }
        }
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

    public boolean equals(Object y) {
        if (!(y instanceof Board)) return false;
        Board that = (Board)y;
        if (this.boardDimension != that.boardDimension) return false;
        for (int i = 0; i < boardDimension; i++) {
            for (int j = 0; j < boardDimension; j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) return false;
            }
        }
        return true;
    }

    private int[][] exchange(int[][] matrix, int iFirst,int jFirst,int iSecond,int jSecond) {
        int[][] newMatrix = new int[boardDimension][boardDimension];
        for (int i = 0; i < boardDimension; i++) {
            for (int j = 0; j < boardDimension; j++) {
                newMatrix[i][j] = matrix[i][j];
            }
        }
        int t;
        t = newMatrix[iFirst][jFirst];
        newMatrix[iFirst][jFirst] = newMatrix[iSecond][jSecond];
        newMatrix[iSecond][jSecond] = t;
        return newMatrix;
    }

    public Iterable<Board> neighbors() {
        Queue<Board> neighborBoards = new Queue<>();
        int iIndex = -1;
        int jIndex = -1;
        for (int i = 0; i < boardDimension; i++) {
            for (int j = 0; j < boardDimension; j++) {
                if (blocks[i][j] == 0) {
                    iIndex = i;
                    jIndex = j;
                }
            }
        }
        if (iIndex == 0 && jIndex == 0) {
            neighborBoards.enqueue(new Board(exchange(this.blocks,iIndex,jIndex,iIndex,jIndex + 1)));
            neighborBoards.enqueue(new Board(exchange(this.blocks,iIndex,jIndex,iIndex + 1,jIndex)));

        } else if (iIndex == 0 && jIndex == boardDimension - 1) {
            neighborBoards.enqueue(new Board(exchange(this.blocks,iIndex,jIndex,iIndex + 1,jIndex)));
            neighborBoards.enqueue(new Board(exchange(this.blocks,iIndex,jIndex,iIndex,jIndex - 1)));

        } else if (iIndex == boardDimension - 1 && jIndex == 0) {
            neighborBoards.enqueue(new Board(exchange(this.blocks,iIndex,jIndex,iIndex,jIndex + 1)));
            neighborBoards.enqueue(new Board(exchange(this.blocks,iIndex,jIndex,iIndex - 1,jIndex)));
        } else if (iIndex == boardDimension - 1 && jIndex == boardDimension - 1) {
            neighborBoards.enqueue(new Board(exchange(this.blocks,iIndex,jIndex,iIndex,jIndex - 1)));
            neighborBoards.enqueue(new Board(exchange(this.blocks,iIndex,jIndex,iIndex - 1,jIndex)));
        } else if (iIndex == 0) {
            neighborBoards.enqueue(new Board(exchange(this.blocks,iIndex,jIndex,iIndex,jIndex - 1)));
            neighborBoards.enqueue(new Board(exchange(this.blocks,iIndex,jIndex,iIndex,jIndex + 1)));
            neighborBoards.enqueue(new Board(exchange(this.blocks,iIndex,jIndex,iIndex + 1,jIndex)));
        } else if (iIndex == boardDimension - 1) {
            neighborBoards.enqueue(new Board(exchange(this.blocks,iIndex,jIndex,iIndex,jIndex - 1)));
            neighborBoards.enqueue(new Board(exchange(this.blocks,iIndex,jIndex,iIndex,jIndex + 1)));
            neighborBoards.enqueue(new Board(exchange(this.blocks,iIndex,jIndex,iIndex - 1,jIndex)));
        } else if (jIndex == 0) {
            neighborBoards.enqueue(new Board(exchange(this.blocks,iIndex,jIndex,iIndex - 1,jIndex)));
            neighborBoards.enqueue(new Board(exchange(this.blocks,iIndex,jIndex,iIndex + 1,jIndex)));
            neighborBoards.enqueue(new Board(exchange(this.blocks,iIndex,jIndex,iIndex,jIndex + 1)));
        } else if (jIndex == boardDimension - 1) {
            neighborBoards.enqueue(new Board(exchange(this.blocks,iIndex,jIndex,iIndex - 1,jIndex)));
            neighborBoards.enqueue(new Board(exchange(this.blocks,iIndex,jIndex,iIndex + 1,jIndex)));
            neighborBoards.enqueue(new Board(exchange(this.blocks,iIndex,jIndex,iIndex,jIndex - 1)));
        } else {
            neighborBoards.enqueue(new Board(exchange(this.blocks,iIndex,jIndex,iIndex - 1,jIndex)));
            neighborBoards.enqueue(new Board(exchange(this.blocks,iIndex,jIndex,iIndex + 1,jIndex)));
            neighborBoards.enqueue(new Board(exchange(this.blocks,iIndex,jIndex,iIndex,jIndex - 1)));
            neighborBoards.enqueue(new Board(exchange(this.blocks,iIndex,jIndex,iIndex,jIndex + 1)));
        }
        return neighborBoards;
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

        int[][] blocks1 = new int[2][2];
        blocks1[0][0] = 2;
        blocks1[0][1] = 1;
        blocks1[1][0] = 3;
        blocks1[1][1] = 0;
        Board board1 = new Board(blocks1);

        int[][] blocks2 = new int[2][2];
        blocks2[0][0] = 1;
        blocks2[0][1] = 2;
        blocks2[1][0] = 3;
        blocks2[1][1] = 0;
        Board board2 = new Board(blocks2);

        int[][] blocks3 = new int[3][3];
        blocks3[0][0] = 8;
        blocks3[0][1] = 1;
        blocks3[0][2] = 3;
        blocks3[1][0] = 4;
        blocks3[1][1] = 0;
        blocks3[1][2] = 2;
        blocks3[2][0] = 7;
        blocks3[2][1] = 6;
        blocks3[2][2] = 5;
        Board board3 = new Board(blocks3);

        int[][] blocks4 = new int[3][3];
        blocks4[0][0] = 1;
        blocks4[0][1] = 2;
        blocks4[0][2] = 3;
        blocks4[1][0] = 4;
        blocks4[1][1] = 5;
        blocks4[1][2] = 6;
        blocks4[2][0] = 7;
        blocks4[2][1] = 8;
        blocks4[2][2] = 0;
        Board board4 = new Board(blocks4);

        System.out.println(board3);
        System.out.println(board3.twin());
        for (Board b : board3.neighbors()) {
            System.out.println(b);
        }
    }
}
