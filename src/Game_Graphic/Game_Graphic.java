package Game_Graphic;
import org.newdawn.slick.*;
import java.awt.Font.*;

import java.util.ArrayList;

import block.block;
import Bar.Bar;
import power_up.power_up;
import speed_up.speed_up;



public class Game_Graphic {
private GameContainer app;
private Graphics g;
private Image background;
private TrueTypeFont big_font;
private TrueTypeFont average_font;
private TrueTypeFont small_font;
private Animation head;
private Image arrow_image;
private Image ball;

 public Game_Graphic(GameContainer app_, Graphics g_){
     app=app_;
     g=g_;

     try {
         ball=new Image("/resources/ball.png");
         arrow_image=new Image("/resources/arrow.png");
         background = new Image("/resources/background.png").getSubImage(16, 16, 32, 32);
         big_font = new TrueTypeFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 60), false);
         average_font = new TrueTypeFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 30), false);
         small_font = new TrueTypeFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20), false);
         Image[] head_temp = {new Image("/resources/head.png").getSubImage(0, 144, 64, 92), new Image("/resources/head.png").getSubImage(64, 144, 64, 92), new Image("/resources/head.png").getSubImage(128, 144, 64, 92), new Image("/resources/head.png").getSubImage(192, 144, 64, 92)};
         int[] duration_head = {300, 300, 300, 300};
         head = new Animation(head_temp, duration_head, true);
     }
     catch (SlickException e){
         e.printStackTrace();
     }

 }
 public void draw_main_menu_background(){
     for(int i=0;i<20;i++) {
         for(int j=0;j<15;j++) {
             g.drawImage(background, 32*i, 32*j);
         }
     }

 }
 public void draw_heads(){
     for(int i=0;i<8;i++)
         head.draw(70+64*i,80);

 }
 public void draw_menu_text(){
     big_font.drawString(70,20,"ARKANOID BABY!",Color.white);
     average_font.drawString( 240, 240,"Start Game!",Color.white);
     average_font.drawString(235,300,"Leaderboard");
     average_font.drawString(250,360,"Exit");
 }
 public void draw_menu_arrow(int index){
     arrow_image.draw(200,240+index*60,2f);
 }
 public void draw_leaderboard(ArrayList<Integer> scores){
     for(int i=0;i<10;i++){
         small_font.drawString(250,100+30*i,Integer.toString(i+1)+" "+Integer.toString(scores.get(i)));
     }


 }
 public void draw_walls(){
     for(int i=0;i<15;i++) {
         block.blocks_images[0].draw(0, 16+i*32,2f);
     }
     for(int i=0;i<15;i++) {
         block.blocks_images[0].draw(624, 16+i*32,2f);
     }
     for(int i=0;i<20;i++){
         block.blocks_images[1].draw(32*i,0,2f);
     }
 }
public void draw_map(block[][] map,int height){
     for(int i=0;i<height;i++){
         for(int j=0;j<19;j++){
             if(map[i][j]!=null)
             block.blocks_images[map[i][j].get_index()].draw(map[i][j].get_x(),map[i][j].get_y(),2f);
         }

     }
}
public void draw_bar(Bar bar){
    bar.get_bar_image().draw((float)(bar.get_x()),466,2f);
}
public void draw_ball(float x,float y){
     ball.draw(x,y);
}

public void draw_end_game_text(int score){
     big_font.drawString(100,240,"GAME ENDED!",Color.white);
     average_font.drawString(240,300,"Your score: "+Integer.toString(score));
}
public void draw_power_ups(ArrayList<power_up> power_ups){
     for(int i=0;i<power_ups.size();i++){
         power_ups.get(i).get_image().draw(power_ups.get(i).get_x(),power_ups.get(i).get_y());
     }
}

}
