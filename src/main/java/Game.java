import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

import static com.googlecode.lanterna.input.KeyType.*;

public class Game {
    private Screen screen;
    private Hero hero;
    public Game() {
        try {
            this.hero = new Hero(10,10);
            TerminalSize terminalSize = new TerminalSize(40, 20);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();
            this.screen = new TerminalScreen(terminal);
            this.screen.setCursorPosition(null); // we don't need a cursor
            this.screen.startScreen(); // screens must be started
            this.screen.doResizeIfNecessary(); // resize screen if necessary
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void draw() throws IOException {
        this.screen.clear();
        hero.draw(screen);
        this.screen.refresh();
    }

    private void processKey(KeyStroke key) {
        switch (key.getKeyType()) {
            case ArrowUp -> hero.moveUp();
            case ArrowDown -> hero.moveDown();
            case ArrowRight -> hero.moveRight();
            case ArrowLeft -> hero.moveLeft();
        }
    }

    public void run() throws IOException {
        while(true) {
            draw();
            KeyStroke key = screen.readInput();
            if (key.getKeyType() == Character && key.getCharacter() == 'q') {
                screen.close();
            }
            else if (key.getKeyType() == KeyType.EOF) {
                break;
            }
            else
                processKey(key);

        }
    }
}