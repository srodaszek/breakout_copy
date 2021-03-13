package Bar;

import org.newdawn.slick.*;
import java.awt.Font.*;


public class Bar {
    private Image narrow_bar;
    private Image wide_bar;
    private Image normal_bar;
    private double pos_x;

    private int current_bar;
    public Bar(){
        try {
            narrow_bar = new Image("/resources/bar.png").getSubImage(80, 0, 26, 8);
            normal_bar = new Image("/resources/bar.png").getSubImage(0, 0, 32, 8);
            wide_bar = new Image("/resources/bar.png").getSubImage(32, 0, 48, 8);
            current_bar=0;
            pos_x=288;
        }
        catch(SlickException e){e.printStackTrace();}

    }
    public double get_x(){return pos_x;}
    public int get_current_bar(){return current_bar;}
    public void set_x(double new_x){
        if (new_x<16.0)pos_x=16.0;
        else if (current_bar==0 && new_x>572)pos_x=572.0;
        else if (current_bar==1 && new_x>560)pos_x=560.0;
        else if (current_bar==2 && new_x>528)pos_x=528.0;
        else pos_x=new_x;
    }
    public void set_current_bar(int new_bar){
        if(new_bar>2 || new_bar<0)return;
        else {
            int temp=get_width();
            current_bar=new_bar;
            set_x(get_x()+((double) temp-get_width())/2);
        }

    }
    public Image get_bar_image(){
       if(current_bar==0)return narrow_bar;
       else if(current_bar==1)return normal_bar;
       else return wide_bar;
    }
    public int get_width(){
        switch(current_bar){
            case 0:
                return 26;
            case 1:
                return 32;
            default:
                return 48;

        }
    }


}
