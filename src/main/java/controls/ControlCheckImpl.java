package controls;

import java.awt.Color;
import java.util.List;

import javax.swing.text.Position;

import board.Chessboard;
import pieces.Piece;

/**
 * 
 *
 */
public class ControlCheckImpl implements ControlCheck {

    @Override
    public List<Position> moveInCheck(final Chessboard chessboard, final Piece piece, final List<Position> avaliableMoves) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isInCheck(final Chessboard chessboard, final Color color) {
        // TODO Auto-generated method stub
        return false;
    }


}
