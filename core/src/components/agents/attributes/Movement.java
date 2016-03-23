package components.agents.attributes;

import java.lang.Math.*;
/**
 * Created by kopec on 2016-03-23.
 */
import static java.lang.Math.hypot;
public class Movement {
    public float x;
    public float y;


    public void setMovement(Position actorPosition, Position destinationPosition){
        double vx = (double)actorPosition.x - (double)destinationPosition.x;
        double vy = (double)actorPosition.y - (double)destinationPosition.y;
        float d = (float)hypot(vx,vy);
        x = (float)vx/d;
        y = (float)vy/d;
    }
}
