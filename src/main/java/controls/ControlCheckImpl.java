package controls;

import piece.utils.Color;
import piece.utils.Name;
import java.util.List;
import piece.utils.Position;
import board.Chessboard;
import board.ChessboardFactory;
import board.ChessboardFactoryImpl;
import pieces.Piece;

/**
 * 
 *
 */

public class ControlCheckImpl implements ControlCheck {

    private ChessboardFactory chessboardFact = new ChessboardFactoryImpl();
    @Override
    public List<Position> removeMoveInCheck(final Chessboard chessboard, final Piece piece) {
        List<Position> avaliableMoves = piece.getAllPossiblePositions(chessboard);
        for (Position pos: avaliableMoves) {
            Chessboard chessboardCopy = chessboardFact.createTestCB(chessboard.getAllPieces());
            //chessboardCopy.move(piece.getPosition(), pos); -> this move should not check removeMoveInCheck
            if (isInCheck(chessboardCopy, piece.getColor())) {
                avaliableMoves.remove(pos);
            }
        }
        return avaliableMoves;
    }

    @Override
    public boolean isInCheck(final Chessboard chessboard, final Color color) {
        return chessboard.getAllPieces().stream()
                .filter(x -> !x.getColor().equals(color))
                .filter(x -> canEatKing(chessboard, x))
                .findFirst().isPresent();
    }
    private boolean canEatKing(final Chessboard chessboard, final Piece piece) {
        return piece.getAllPossiblePositions(chessboard).contains(getEnemyKingPosition(chessboard, piece));
    }
    private Position getEnemyKingPosition(final Chessboard chessboard, final Piece piece) {
        return chessboard.getAllPieces().stream()
                .filter(x -> !x.getColor().equals(piece.getColor()))
                .filter(x -> x.getName().equals(Name.KING))
                .findFirst()
                .get().getPosition();
    }



}
