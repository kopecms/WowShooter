package components;

/**
 * Created by kopec on 2016-03-22.
 */
public class Enemy {
    String name;
    int id;

    private Position position = new Position();

    public void setId(int id) { this.id = id; }
    public void setPosition(int x, int y) { position.x = x; position.y = y;}
    public int getx(){ return position.x; }
    public int gety(){ return position.y; }
}
