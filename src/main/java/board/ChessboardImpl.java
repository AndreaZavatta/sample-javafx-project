package board;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import controls.ControlCheck;
import controls.ControlCheckImpl;
import exceptions.PositionNotFoundException;
import piece.utils.Position;
import pieces.Piece;

    /**
     * 
     * a normal chessboard.
     *
     */
class ChessboardImpl implements Chessboard {
    private final List<Piece> piecesList;
    private final int xBorder;
    private final int yBorder;

    ChessboardImpl(final List<Piece> piecesList, final int yBorder, final int xBorder) {
        this.piecesList = piecesList;
        this.xBorder = xBorder;
        this.yBorder = yBorder;
    }

    @Override
    public List<Piece> getAllPieces() {
        return Collections.unmodifiableList(this.piecesList);
    }

    @Override
    public void move(final Position actualPos, final Position finalPos) throws PositionNotFoundException {
        final ControlCheck movesController = new ControlCheckImpl();
        final Piece attacker = this.getPieceOnPosition(actualPos).get();
        if (movesController.removeMovesInCheck(this, attacker).contains(finalPos)) {
            this.moveWithoutChecks(attacker, finalPos);
        } else {
            throw new PositionNotFoundException();
        }

    }

    public int getxBorder() {
        return this.xBorder;
    }

    public int getyBorder() {
        return this.yBorder;
    }

    @Override
    public String toString() {
        return "Chessboard: " + this.piecesList.stream().map(Piece::toString)
                                            .collect(Collectors.joining(" | "));
    }

    public Optional<Piece> getPieceOnPosition(final Position selectedPos) {
        return piecesList.stream()
                .filter(t -> t.getPosition().equals(selectedPos))
                .findFirst();
    }

    private boolean canKill(final Position targetPos) {
        return this.getPieceOnPosition(targetPos).isPresent();
    }

    private void moveWithoutChecks(final Piece piece, final Position targetPos) {
        if (this.canKill(targetPos)) {
           this.piecesList.remove(this.getPieceOnPosition(targetPos).get()); 
        }
        piece.setPosition(targetPos);
        if (piece.isMoved()) {
            piece.setIsMoved();
        }
    }
}
