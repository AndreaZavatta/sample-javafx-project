package io.test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static model.piece.utils.Side.BLACK;
import static model.piece.utils.Side.WHITE;
import static org.junit.jupiter.api.Assertions.fail;
import board.Chessboard;
import board.ChessboardFactory;
import board.ChessboardFactoryImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.*;
import model.pieces.*;
import org.junit.jupiter.api.Test;
import game.Game;
import game.GameImpl;
import tuple.Pair;
import model.piece.utils.Name;
import model.piece.utils.Position;
import user.User;
import user.UserImpl;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

class IOTest {
    private final String fs = System.getProperty("file.separator");
    private final String cd =  System.getProperty("user.dir");
    private final PieceFactory fact = new PieceFactoryImpl();
    private final JsonSerializer js = new JsonSerializerImpl();
    private final ChessboardFactory chessboardFact = new ChessboardFactoryImpl();
    private final ObjectMapper map = JsonUtils.getMapper();
    private final JsonFileWriter fw = new JsonFileWriterImpl("prova.txt");
    @Test
    void testDeserializationPiece() {
        try {
            final Piece rook = fact.createPiece(Name.ROOK, Position.createNumericPosition(3, 4), BLACK);
            final Piece rook2 = map.readValue(map.writeValueAsString(rook), Piece.class);
            assertEquals(rook, rook2);
        } catch (JsonProcessingException ex) {
            fail();
        }
    }

    @Test
    void testSerializationPiece() {
        try {
            final Piece rook = fact.createPiece(Name.ROOK, Position.createNumericPosition(3, 4), BLACK);
            String str = map.writeValueAsString(rook);
            final Piece rook2 = map.readValue(str, Piece.class);
            assertEquals(rook, rook2);
        } catch (JsonProcessingException ex) {
            fail();
        }
    }
    @Test
    void testDate() {
        //TODO
    }

    @Test
    void testDeserializationChessboard() {
        try {
            final Chessboard chessboard = chessboardFact.createNormalCB();
            String str = map.writeValueAsString(chessboard);
            final Chessboard chessboard2 = map.readValue(str, Chessboard.class);
            assertEquals(chessboard, chessboard2);
        } catch (JsonProcessingException ex) {
            fail();
        }
    }
    @Test
    void testSerializationChessboard() {
        try {
            final Chessboard chessboard = chessboardFact.createNormalCB();
            String str = map.writeValueAsString(chessboard);
            final Chessboard chessboard2 = map.readValue(str, Chessboard.class);
            assertEquals(chessboard, chessboard2);
        } catch (IOException ex) {
            fail();
        }

    }

    @Test
    void testDeserializationGame() {
        try {
            final Game game = new GameImpl(new Pair<>(new UserImpl("andrea"), BLACK), new Pair<>(new UserImpl("marco"), WHITE));
            String str = map.writeValueAsString(game);
            final Game game2 = map.readValue(str, Game.class);
            assertEquals(game.getPiecesList(), game2.getPiecesList());
        } catch (JsonProcessingException ex) {
            fail();
        }
    }

    @Test
    void testSerializationGame() {
        try {
            final User user = new UserImpl("andrea");
            final User user2 = new UserImpl("giacomo");
            final Game game = new GameImpl(new Pair<>(user, WHITE), new Pair<>(user2, BLACK));
            final String json = map.writeValueAsString(game);
            final Game game2 = map.readValue(json, GameImpl.class);
            final String json2 = map.writeValueAsString(game2);
            assertEquals(json, json2);
        } catch (IOException ex) {
            fail();
        }
    }

    @Test
    void testDeserializer() {
        try {
            final JsonDeserializer jsonDeserializer = new JsonDeserializerImpl();
            final List<Game> game = List.of(getGame("andrea", "giacomo"));
            final String json = js.serialize(game);
            final List<Game> game2 = jsonDeserializer.deserialize(json);
            assertEquals(game.get(0).getPiecesList(), game2.get(0).getPiecesList());
        } catch (IOException ex) {
            fail();
        }
    }

    private Game getGame(final String firstName, final String secondName) {
        final User user1 = new UserImpl(firstName);
        final User user2 = new UserImpl(secondName);
        return new GameImpl(new Pair<>(user1, WHITE), new Pair<>(user2, BLACK));
    }
    private List<Game> getGames() {
        return List.of(getGame("andrea", "giacomo"), getGame("stefano", "giorgio"));
    }

    @Test
    void testReadObjFile() {
        try {
            final Game game = getGame("andrea", "giacomo");
            fw.writeFile(game);
            final JsonFileReader fr = new JsonFileReaderImpl("prova.txt");
            final Game game2 = (Game) fr.readFile();
            assertEquals(game.getPiecesList(), game2.getPiecesList());
        } catch (IOException ignored) {
            fail();
        }
    }

    @Test
    void testReadListFile() {
        try {
            final List<Game> listGame = getGames();
            fw.writeFile(listGame);
            JsonFileReader fr = new JsonFileReaderImpl("prova.txt");
            final List<Game> listGame2 = fr.readFile();
            final List<List<Piece>> listPiece = listGame.stream().map(Game::getPiecesList).collect(Collectors.toList());
            final List<List<Piece>> listPiece2 = listGame2.stream().map(Game::getPiecesList).collect(Collectors.toList());
            assertEquals(listPiece, listPiece2);
            if (!new File(cd + fs + "prova.txt").delete()) {
                fail();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
