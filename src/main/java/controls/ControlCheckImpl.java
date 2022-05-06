package controls;

import static piece.utils.Name.KING;
import piece.utils.Color;
import java.util.ArrayList;
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
    public List<Position> removeMovesInCheck(final Chessboard chessboard, final Piece piece) {
        final List<Position> avaliableMoves = new ArrayList<>(piece.getAllPossiblePositions(chessboard));
        avaliableMoves.removeIf(x -> this.isMoveInCheck(chessboard, piece, x));
        return avaliableMoves;
    }

    private boolean isMoveInCheck(final Chessboard chessboard, final Piece piece, final Position pos) {
        return isInCheck(simulateMove(chessboard, piece, pos), piece.getColor());
    }
    private Chessboard simulateMove(final Chessboard chessboard, final Piece piece, final Position pos) {
        final Chessboard chessboardCopy = copyChessboard(chessboard);
        setPosition(piece, pos, chessboardCopy);
        return chessboardCopy;
    }
    private Chessboard copyChessboard(final Chessboard chessboard) {
        return chessboardFact.createTestCB(chessboard.getAllPieces());
    }
    private void setPosition(final Piece piece, final Position pos, final Chessboard chessboardCopy) {
        chessboardCopy.getAllPieces().stream()
        .filter(x -> x.getPosition().equals(piece.getPosition()))
        .findFirst().get()
        .setPosition(pos);
    }
    @Override
    public boolean isInCheck(final Chessboard chessboard, final Color color) {
        return chessboard.getAllPieces().stream()
                .filter(x -> !x.getColor().equals(color))
                .filter(x -> canEatKing(chessboard, x))
                .findFirst().isPresent();
    }

    private boolean canEatKing(final Chessboard chessboard, final Piece piece) {
        return piece.getAllPossiblePositions(chessboard)
                    .contains(getEnemyKingPosition(chessboard, piece));
    }
    private Position getEnemyKingPosition(final Chessboard chessboard, final Piece piece) {
        return chessboard.getAllPieces().stream()
                .filter(x -> !x.getColor().equals(piece.getColor()))
                .filter(x -> x.getName().equals(KING))
                .findFirst()
                .get().getPosition();
    }



}
