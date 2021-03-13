package power_up;

import org.newdawn.slick.*;

 public interface power_up {

    public float get_x();
    public float get_y();
    public Animation get_image();
    abstract public float get_duration();
    public void set_y(float new_y);

}
/*  protected Animation sprite;
    protected float pos_x;
    protected float pos_y;
public float get_x(){return pos_x;}
    public float get_y(){return pos_y;}
    public Animation get_image(){return sprite;}
    abstract public float get_duration();
    public void set_y(float new_y){pos_y=new_y;}
 */