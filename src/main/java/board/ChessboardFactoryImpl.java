package board;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import piece.utils.Color;
import piece.utils.Name;
import piece.utils.Position;
import pieces.Piece;
import pieces.PieceFactory;
import pieces.PieceFactoryImpl;

/**
     * 
     * This class create different types of chessboard
     * for different types of game modes.
     *
     */
public class ChessboardFactoryImpl implements ChessboardFactory {

    @Override
    public Chessboard createNormalCB() {
        final List<Piece> chessOnBoard = this.createPawns(6, Color.WHITE);
        chessOnBoard.addAll(this.createBackLine(7, Color.WHITE));
        chessOnBoard.addAll(this.createPawns(1, Color.BLACK));
        chessOnBoard.addAll(this.createBackLine(0, Color.BLACK));
        return new ChessboardImpl(chessOnBoard, 7, 7);
    }

    @Override
    public Chessboard createTestCB(final List<Piece> piecesOnBoard) {
        return new ChessboardImpl(this.createCopyOf(piecesOnBoard), 7, 7);
    }

    private List<Piece> createPawns(final int row, final Color color) {
        final PieceFactory pieceCreator = new PieceFactoryImpl();
        return Stream.iterate(0, n -> n + 1)
                .limit(8)
                .<Piece>map(n -> pieceCreator.createPiece(Name.PAWN, new Position(row, n), color))
                .collect(Collectors.toList());
    }

    private List<Piece> createBackLine(final int row, final Color color) {
        final PieceFactory pieceCreator = new PieceFactoryImpl();
        final List<Piece> backLine = new LinkedList<>();
        int n = 0;
        backLine.add(pieceCreator.createPiece(Name.ROOK, new Position(row, n++), color));
        backLine.add(pieceCreator.createPiece(Name.KNIGHT, new Position(row, n++), color));
        backLine.add(pieceCreator.createPiece(Name.BISHOP, new Position(row, n++), color));
        backLine.add(pieceCreator.createPiece(Name.QUEEN, new Position(row, n++), color));
        backLine.add(pieceCreator.createPiece(Name.KING, new Position(row, n++), color));
        backLine.add(pieceCreator.createPiece(Name.BISHOP, new Position(row, n++), color));
        backLine.add(pieceCreator.createPiece(Name.KNIGHT, new Position(row, n++), color));
        backLine.add(pieceCreator.createPiece(Name.ROOK, new Position(row, n), color));
        return backLine;
    }

    private List<Piece> createCopyOf(final List<Piece> originalList) {
        final List<Piece> copy = new ArrayList<>();
        final PieceFactory piece = new PieceFactoryImpl();
        originalList.stream()
            .forEach(x -> copy.add(piece.createPiece(x.getName(), x.getPosition(), x.getColor())));
        return copy;
    }
}
