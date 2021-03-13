package speed_up;

import org.newdawn.slick.*;
import power_up.power_up;
public class speed_up implements power_up {
    private float duration;
    private float power;

    private Animation sprite;
    private float pos_x;
    private float pos_y;
    public float get_x(){return pos_x;}
    public float get_y(){return pos_y;}
    public Animation get_image(){return sprite;}
    public void set_y(float new_y){pos_y=new_y;}
    public speed_up(float x,float y){
        pos_x=x;
        pos_y=y;
        Image[] temp=new Image[8];
        try {
            for (int i = 0; i < 8; i++) {
                temp[i] = new Image("/resources/powerup.png").getSubImage(32 * i, 0, 32, 14);
            }
        }catch(SlickException e){
            e.printStackTrace();
        }
        int[] duration_temp={100,100,100,100,100,100,100,100};
        sprite=new Animation(temp,duration_temp,true);
        duration=5.0f;
        power=1.25f;
    }

    @Override
    public float get_duration(){return duration;}
    public float get_power(){return power;}
}
