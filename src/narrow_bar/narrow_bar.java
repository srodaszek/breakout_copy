package narrow_bar;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import power_up.power_up;

public class narrow_bar implements power_up{
    private float duration;
    private Animation sprite;
    private float pos_x;
    private float pos_y;
    public float get_x(){return pos_x;}
    public float get_y(){return pos_y;}
    public Animation get_image(){return sprite;}
    public void set_y(float new_y){pos_y=new_y;}
    public narrow_bar(float x,float y){
        pos_x=x;
        pos_y=y;
        Image[] temp=new Image[8];
        try {
            for (int i = 0; i < 8; i++) {
                temp[i] = new Image("/resources/powerup.png").getSubImage(32 * i, 42, 32, 14);
            }
        }catch(SlickException e){
            e.printStackTrace();
        }
        int[] duration_temp={100,100,100,100,100,100,100,100};
        sprite=new Animation(temp,duration_temp,true);
        duration=5.0f;

    }
    @Override
    public float get_duration(){return duration;}
}
