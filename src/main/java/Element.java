import com.googlecode.lanterna.graphics.TextGraphics;

abstract class Element {

    protected Position position;

    public Element(int x, int y) {
        position = new Position(x, y);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    abstract void draw(TextGraphics graphics);
}
