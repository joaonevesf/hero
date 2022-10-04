import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Coin extends Element {

    private Boolean collected;

    public Coin(int x, int y) {
        super(x, y);
        collected = false;
    }

    public void draw(TextGraphics graphics) {
        if (!collected) {
            graphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
            graphics.putString(new TerminalPosition(position.getX(), position.getY()), "C");
        }
    }

    public void collect()
    {
        collected = true;
    }


}
