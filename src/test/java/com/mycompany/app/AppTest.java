package com.mycompany.app;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppTest {
    private Game game;
    private Player player;

    @BeforeEach
    public void setUp() {
        game = new Game();
        player = new Player();
        player.symbol = 'X';
    }

    @Test
    public void gameInitialization_ShouldSetDefaultValues() {
        assertEquals(State.PLAYING, game.state);
        assertNotNull(game.board);
        assertEquals(9, game.board.length);
        for (char c : game.board) {
            assertEquals(' ', c);
        }
    }

    @Test
    public void playerInitialization_ShouldHaveDefaultProperties() {
        Player p = new Player();
        assertEquals('\u0000', p.symbol);
        assertEquals(0, p.move);
        assertFalse(p.selected);
        assertFalse(p.win);
    }

    @Test
    public void checkGameState_WhenNoWinner_ShouldReturnPlaying() {
        char[] board = {'X',' ',' ',' ',' ',' ',' ',' ',' '};
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    public void checkGameState_WhenBoardFull_ShouldReturnDraw() {
        char[] board = {'X','O','X','X','O','O','O','X','X'};
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(board));
        game.symbol = 'O';
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    public void checkGameState_WhenXWinsHorizontal_ShouldReturnXWin() {
        char[] board = {'X','X','X',' ',' ',' ',' ',' ',' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void checkGameState_WhenOWinsVertical_ShouldReturnOWin() {
        char[] board = {'O',' ',' ','O',' ',' ','O',' ',' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    public void checkGameState_WhenXWinsDiagonal_ShouldReturnXWin() {
        char[] board = {'X',' ',' ',' ','X',' ',' ',' ','X'};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void checkGameState_WhenOWinsAntiDiagonal_ShouldReturnOWin() {
        char[] board = {' ',' ','O',' ','O',' ','O',' ',' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    public void generateMoves_OnEmptyBoard_ShouldReturnAllPositions() {
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(9, moves.size());
        for (int i = 0; i < 9; i++) {
            assertTrue(moves.contains(i));
        }
    }

    @Test
    public void generateMoves_OnPartiallyFilledBoard_ShouldReturnValidMoves() {
        char[] board = {'X','O',' ',' ','X','O','X',' ',' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(4, moves.size());
        assertTrue(moves.contains(2));
        assertTrue(moves.contains(3));
        assertTrue(moves.contains(7));
        assertTrue(moves.contains(8));
    }

    @Test
    public void evaluatePosition_WhenDraw_ShouldReturnZero() {
        char[] board = {'X','O','X','X','O','O','O','X','X'};
        player.symbol = 'X';
        assertEquals(0, game.evaluatePosition(board, player));
    }

    @Test
    public void evaluatePosition_WhenXWins_ShouldReturnInfinityForXPlayer() {
        char[] board = {'X','X','X',' ',' ',' ',' ',' ',' '};
        game.symbol = 'X';
        player.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(board, player));
    }

    @Test
    public void evaluatePosition_WhenXWins_ShouldReturnNegativeInfinityForOPlayer() {
        char[] board = {'X','X','X',' ',' ',' ',' ',' ',' '};
        game.symbol = 'X';
        player.symbol = 'O';
        assertEquals(-Game.INF, game.evaluatePosition(board, player));
    }

    @Test
    public void evaluatePosition_WhenOWins_ShouldReturnInfinityForOPlayer() {
        char[] board = {'O','O','O',' ',' ',' ',' ',' ',' '};
        game.symbol = 'O';
        player.symbol = 'O';
        assertEquals(Game.INF, game.evaluatePosition(board, player));
    }

    @Test
    public void evaluatePosition_WhenOWins_ShouldReturnNegativeInfinityForXPlayer() {
        char[] board = {'O','O','O',' ',' ',' ',' ',' ',' '};
        game.symbol = 'O';
        player.symbol = 'X';
        assertEquals(-Game.INF, game.evaluatePosition(board, player));
    }

    @Test
    public void minMove_ShouldReturnValidValueWithinRange() {
        char[] board = {'X','O',' ',' ',' ',' ',' ',' ',' '};
        player.symbol = 'X';
        int value = game.MinMove(board, player);
        assertTrue(value >= -Game.INF && value <= Game.INF);
    }

    @Test
    public void maxMove_ShouldReturnValidValueWithinRange() {
        char[] board = {'X','O',' ',' ',' ',' ',' ',' ',' '};
        player.symbol = 'X';
        int value = game.MaxMove(board, player);
        assertTrue(value >= -Game.INF && value <= Game.INF);
    }

    @Test
    public void miniMax_OnInitialBoard_ShouldReturnValidMove() {
        player.symbol = 'X';
        int move = game.MiniMax(game.board, player);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    public void printBoard_ShouldExecuteWithoutErrors() {
        char[] board = {'X','O',' ',' ',' ',' ',' ',' ',' '};
        Utility.print(board);
    }

    @Test
    public void printIntArray_ShouldExecuteWithoutErrors() {
        int[] array = {6,2,4,1,8,6,7,5,9};
        Utility.print(array);
    }

    @Test
    public void printArrayList_ShouldExecuteWithoutErrors() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(7);
        list.add(6);
        Utility.print(list);
    }

    @Test
    public void ticTacToeCell_Initialization_ShouldSetCorrectProperties() {
        TicTacToeCell cell = new TicTacToeCell(8, 2, 1);
        assertEquals(8, cell.getNum());
        assertEquals(2, cell.getCol());
        assertEquals(1, cell.getRow());
        assertEquals(' ', cell.getMarker());
    }

    @Test
    public void ticTacToeCell_SetMarker_ShouldUpdateState() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("O");
        assertEquals('O', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    public void ticTacToePanel_Initialization_ShouldCreate9Cells() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3,3));
        assertEquals(9, panel.getComponentCount());
        for (Component comp : panel.getComponents()) {
            assertInstanceOf(TicTacToeCell.class, comp);
        }
    }

    @Test
    public void ticTacToeCell_Click_ShouldSetXorO() {
        System.setProperty("java.awt.headless", "true");
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3,3));
        TicTacToeCell cell = (TicTacToeCell) panel.getComponent(0);
        cell.doClick();
        char marker = cell.getMarker();
        assertTrue(marker == 'X' || marker == 'O');
    }
}