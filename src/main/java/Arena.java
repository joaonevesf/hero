import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    private int width;
    private int height;
    private Hero hero;
    private List<Wall> walls;
    private List<Coin> coins;
    private List<Monster> monsters;

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.hero = new Hero(10, 10);
        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters = createMonsters();
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();
        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }
        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }
        return walls;
    }

    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        int n = 5;
        for (int i = 0; i < n; i++) {
            Coin coin = new Coin(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
                if (!coin.getPosition().equals(hero.getPosition())) {
                    coins.add(coin);
                }
                else {
                    n ++;
                }
        }
        return coins;
    }

    public void retrieveCoins() {
        for (Coin coin : coins) {
            if (coin.getPosition().equals(hero.getPosition())) {
                coin.collect();
            }
        }
    }

    public List<Monster> createMonsters() {
        List<Monster> monsters = new ArrayList<>();
        Random random = new Random();
        int n = 5;
        for (int i = 0; i < n; i++) {
            Monster monster = new Monster(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
            if (!monster.getPosition().equals(hero.getPosition())) {
                monsters.add(monster);
            }
            else {
                n++;
            }
        }
        System.out.println(monsters.size());
        return monsters;
    }

    public boolean verifyMonsterCollisions(){
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(hero.getPosition())) {
                return true;
            }
        }
        return false;
    }


    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        for (Wall wall : walls) {
            wall.draw(graphics);
        }
        for (Coin coin : coins) {
            coin.draw(graphics);
        }
        for (Monster monster : monsters) {
            monster.draw(graphics);
        }
        hero.draw(graphics);
    }
    public void processKey(KeyStroke key) {
        switch (key.getKeyType()) {
            case ArrowUp :
                moveHero(hero.moveUp());
                moveMonsters();
                break;
            case ArrowDown :
                moveHero(hero.moveDown());
                moveMonsters();
                break;
            case ArrowRight :
                moveHero(hero.moveRight());
                moveMonsters();
                break;
            case ArrowLeft :
                moveHero(hero.moveLeft());
                moveMonsters();
                break;
        }
        retrieveCoins();
    }

    public void moveHero(Position position) {
        if (canMove(position)) {
            hero.setPosition(position);
        }
    }

    public void moveMonsters() {
        Random random = new Random();
        Random random2 = new Random();
        int isSmart = random.nextInt(3);
        int direction = random.nextInt(3);
        for (Monster monster : monsters) {
            switch (isSmart) {
                case 0:
                    if(canMove(monster.follow(hero.getPosition()))) {
                        monster.setPosition((monster.follow(hero.getPosition())));
                    }
                    break;
                default:
                    switch (direction) {
                    case 0:
                        if (canMove(monster.moveUp())) {
                            monster.setPosition(monster.moveUp());
                        } else {
                            monster.setPosition(monster.moveDown());
                        }
                        break;
                    case 1:
                        if (canMove(monster.moveDown())) {
                            monster.setPosition(monster.moveDown());
                        } else {
                            monster.setPosition(monster.moveUp());
                        }
                        break;
                    case 2:
                        if (canMove(monster.moveLeft())) {
                            monster.setPosition(monster.moveLeft());
                        } else {
                            monster.setPosition(monster.moveRight());
                        }
                        break;
                    case 3:
                        if (canMove(monster.moveRight())) {
                            monster.setPosition(monster.moveRight());
                        } else {
                            monster.setPosition(monster.moveLeft());
                        }
                        break;
                }
                    break;
            }
        }
    }
    private boolean canMove(Position position) {
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)) {
                return false;
            }
        }
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(position))
                return false;
        }
        return true;
    }

    public boolean isWin() {
        int counter = 0;
        for (Coin coin : coins) {
            if (coin.isCollected()) {
                counter++;
            }
        }
        if (counter == coins.size()) {
            return true;
        }
        return false;
    }
}