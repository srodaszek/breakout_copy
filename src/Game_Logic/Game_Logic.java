package Game_Logic;


import org.newdawn.slick.*;


import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import block.block;
import Bar.Bar;
import Game_Graphic.Game_Graphic;
import power_up.power_up;
import speed_up.speed_up;
import speed_down.speed_down;
import narrow_bar.narrow_bar;
import wide_bar.wide_bar;

import static java.lang.Math.abs;


public class Game_Logic {

    private float[] ball_speed;
    private float[] ball_position;
    private double bar_speed;
    private block[][] map;
    private Bar bar;
    private Game_Graphic g_;
    private int height;
    private boolean special_bar_size;
    private float special_bar_time;
    GameContainer gc;
    private int score;
    private boolean end;
    private ArrayList<power_up> power_ups;
    private ArrayList<Float> effects_time;
    private ArrayList<Float> effects_power;

    public Game_Logic(GameContainer gc,Game_Graphic g_){
        special_bar_size=false;
        effects_power=new ArrayList<>();
        effects_time=new ArrayList<>();
        power_ups=new ArrayList<>();
        this.g_=g_;
        this.gc=gc;
        score=0;
        end=false;
        bar=new Bar();
        bar_speed=300;
        ball_position=new float[2];
        ball_position[0]=304;
        ball_position[1]=448;
        ball_speed=new float[2];
        ball_speed[0]=100;
        ball_speed[1]=-100;

        height=ThreadLocalRandom.current().nextInt(4,7);
    map=new block[height][19];
        for (int i=0;i<height ;i++) {
            for (int j = 0; j < 19; j++) {
                map[i][j] = new block(ThreadLocalRandom.current().nextInt(1, 9), 16+32 * j, 16+16 * i,ThreadLocalRandom.current().nextInt(0,5));
            }
        }
    }
    public int game_loop(Input input,int delta){//return 1 if game still run, else return 0

        if(input.isKeyDown(Input.KEY_LEFT)){
            bar.set_x(bar.get_x()-bar_speed*((float)(delta)/1000.0f));
        }
        else if (input.isKeyDown(Input.KEY_RIGHT)) {//move right
            bar.set_x(bar.get_x()+bar_speed*((float)(delta)/1000.0f));
        }
        else if(input.isKeyPressed(Input.KEY_ESCAPE))return score;
        ball_position[0]+=ball_speed[0]*((float)(delta)/1000.0f);
        ball_position[1]+=ball_speed[1]*((float)(delta)/1000.0f);
        for(int i=0;;i++){
            if(power_ups.size()<=i)break;
            power_ups.get(i).set_y((float)(power_ups.get(i).get_y()+150.0f*((float)(delta)/1000.0f)));
            if(power_ups.get(i).get_y()>480)power_ups.remove(i--);

        }
        check_collisions();
        check_collisions_with_powerups();
        change_effects((float)(delta)/1000.0f);

        if (end==true)return score;

        return -1;
    }
    public void game_loop_graphic(){
        g_.draw_main_menu_background();
        g_.draw_walls();
        g_.draw_map(map,height);
        g_.draw_bar(bar);
        g_.draw_ball(ball_position[0],ball_position[1]);
        g_.draw_power_ups(power_ups);
    }

    private void  change_effects(float delta){
        for(int i=0;;i++){
            if(i>=effects_power.size())break;
            effects_time.set(i,effects_time.get(i)-delta);
            if(effects_time.get(i)<=0){
                    ball_speed[0]/=effects_power.get(i);
                    ball_speed[1]/=effects_power.get(i);
                    effects_time.remove(i);
                    effects_power.remove(i--);
            }
        }
        if(special_bar_size){
            special_bar_time-=delta;
            if(special_bar_time<=0){
                special_bar_size=false;
                bar.set_current_bar(1);
            }
        }
    }
    private int collide(float block_x,float block_y,int block_width,int block_height,float ball_position[]){
        if(block_x+block_width>ball_position[0] && block_x<ball_position[0]+16
        && block_y+block_height>ball_position[1] && block_y<ball_position[1]+16){
            if (ball_position[1]<block_y)return 2;
            else if(ball_position[1]>block_y+12)return 4;
            else if(ball_position[0]-8<block_x)return 1;
            else return 3;
        }
        return 0;


    }
    private boolean check_if_game_end(){
        if(ball_position[1]>480)return true;
        for(int i=0;i<height;i++){
            for(int j=0;j<19;j++){
                if(map[i][j]!=null)return false;
            }
        }
        return true;
    }
    private boolean collide_with_powerup(power_up pw){
        if(pw.get_x()+32>bar.get_x() && pw.get_x()<bar.get_x()+bar.get_width() &&pw.get_y()+16>466 && pw.get_y()<480) return true;
        return false;
    }
    private void check_collisions_with_powerups(){
        for(int i=0;;i++){
           if(i>=power_ups.size())break;
           if(collide_with_powerup(power_ups.get(i))){
               power_up temp=power_ups.get(i);
               if(temp instanceof speed_up){
                   effects_time.add(temp.get_duration());
                   effects_power.add(((speed_up) temp).get_power());
                   ball_speed[0]*=((speed_up) temp).get_power();
                   ball_speed[1]*=((speed_up) temp).get_power();
                   power_ups.remove(i--);
               }
               else if(temp instanceof speed_down){
                   effects_time.add(temp.get_duration());
                   effects_power.add(((speed_down) temp).get_power());
                   ball_speed[0]*=((speed_down) temp).get_power();
                   ball_speed[1]*=((speed_down) temp).get_power();
                   power_ups.remove(i--);
               }
               else if(temp instanceof narrow_bar){
                    special_bar_size=true;
                    special_bar_time=temp.get_duration();
                    int temp_bar=bar.get_current_bar();
                    if(temp_bar!=0)temp_bar--;
                    bar.set_current_bar(temp_bar);
                    power_ups.remove(i--);
               }
               else if(temp instanceof wide_bar){
                   special_bar_size=true;
                   special_bar_time=temp.get_duration();
                   int temp_bar=bar.get_current_bar();
                   if(temp_bar!=2)temp_bar++;
                   bar.set_current_bar(temp_bar);
                   power_ups.remove(i--);
               }
           }
        }
    }


    private void check_collisions(){
        if(ball_position[0]<16)ball_speed[0]=abs(ball_speed[0]);
        else if(ball_position[0]>608)ball_speed[0]=-1*abs(ball_speed[0]);
        else if (ball_position[1]<16)ball_speed[1]=abs(ball_speed[1]);
        for(int i=0;i<height;i++){
            for(int j=0;j<19;j++){
                if(map[i][j]!=null){
                    int result=collide(map[i][j].get_x(),map[i][j].get_y(),34,18,ball_position);
                    if(result==1)ball_speed[0]=(-1)*abs(ball_speed[0]);
                    else if(result==2)ball_speed[1]=(-1)*abs(ball_speed[1]);
                    else if(result==3)ball_speed[0]=abs(ball_speed[0]);
                    else if(result==4)ball_speed[1]=abs(ball_speed[1]);
                    if(result!=0){
                        if(map[i][j].get_power()>0){
                            if(map[i][j].get_power()==1)power_ups.add(new speed_up(map[i][j].get_x(),map[i][j].get_y()));
                            if(map[i][j].get_power()==2)power_ups.add(new speed_down(map[i][j].get_x(),map[i][j].get_y()));
                            if(map[i][j].get_power()==3)power_ups.add(new narrow_bar(map[i][j].get_x(),map[i][j].get_y()));
                            if(map[i][j].get_power()==4)power_ups.add(new wide_bar(map[i][j].get_x(),map[i][j].get_y()));
                        }
                        map[i][j]=null;
                        score+=10;

                    }

                    }
                }
            }
        int result=collide((float)bar.get_x(),466,bar.get_width()+2,16,ball_position);
        if(result==1){ball_speed[0]=(-1)*abs(ball_speed[0]);}
        else if(result==2)ball_speed[1]=(-1)*abs(ball_speed[1]);
        else if(result==3)ball_speed[0]=abs(ball_speed[0]);
        else if(result==4)ball_speed[1]=abs(ball_speed[1]);
        end=check_if_game_end();
        }
    }



