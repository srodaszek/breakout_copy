package main;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.*;
import java.awt.Font.*;

import java.util.Scanner;

import java.util.ArrayList;

import Game_Graphic.Game_Graphic;
import block.block;
import Game_Logic.Game_Logic;

public class main extends BasicGame
{
    private Game_Logic gl;
    private Game_Graphic g_;
    private int menu_arrow_index;
    private int game_state;
    private ArrayList<Integer> scores;
    private int new_score;
    //0-main_menu
    //1-leaderboard
    //2-game
    //3-game_end
    public main(String gamename)
    {
        super(gamename);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {

        block xd=new block(0,0,0,0);
        g_=new Game_Graphic(gc,gc.getGraphics());

        menu_arrow_index=0;
        game_state=0;
    }
    public void read_scores(){
        scores=new ArrayList<>();
        try {
            File scores_file = new File("leaderboard.txt");
            Scanner myReader = new Scanner(scores_file);
            while(myReader.hasNextLine()){
                Integer data=Integer.parseInt(myReader.nextLine());
                scores.add(data);
            }
            myReader.close();
        }catch(FileNotFoundException e) {
        e.printStackTrace();
        }

    }
    public void sort_scores(){
        for(int i=0;i<scores.size();i++){
            for(int j=0;j<scores.size()-1;j++){

                if(scores.get(j)< scores.get(j+1)){
                    int temp=scores.get(j);
                    scores.set(j,scores.get(j+1));
                    scores.set(j+1,temp);
                }
            }
        }
    }
    public void add_score(){
        try {
            FileWriter fw = new FileWriter("leaderboard.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Integer.toString(new_score));
            bw.newLine();
            bw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        int temp;
        Input input=gc.getInput();
    switch(game_state) {
        case 0:
            if (input.isKeyPressed(Input.KEY_DOWN)) {
                menu_arrow_index = (menu_arrow_index + 1) % 3;
            } else if (input.isKeyPressed(Input.KEY_UP)) {
                menu_arrow_index = (menu_arrow_index - 1 + 3) % 3;
            }
            else if(input.isKeyPressed(Input.KEY_ENTER)) {
                if(menu_arrow_index==1){
                    game_state=1;
                    read_scores();
                    sort_scores();
            }
                if(menu_arrow_index==0){
                    gl=new Game_Logic(gc,g_);
                    game_state=2;
                }
                if (menu_arrow_index==2){
                    gc.exit();
                    //save or sth
                    System.exit(0);
                }
            }
        break;
        case 1:
            if(input.isKeyPressed(Input.KEY_ESCAPE)){
                game_state=0;
            }
        break;
        case 2:
            if((temp=gl.game_loop(input,delta))>-1){
                new_score=temp;
                add_score();
                game_state=3;
                gl=null;
            }
            break;
        case 3:
            if(input.isKeyPressed(Input.KEY_ENTER)){

                game_state=0;
            }
        break;

    }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        switch(game_state) {
            case 0:
                g_.draw_main_menu_background();
                g_.draw_heads();
                g_.draw_menu_text();
                g_.draw_menu_arrow(menu_arrow_index);
                g_.draw_walls();
            break;
            case 1:
            g_.draw_main_menu_background();
            g_.draw_leaderboard(scores);
            break;
            case 2:
                gl.game_loop_graphic();
            break;
            case 3:
                g_.draw_main_menu_background();
                g_.draw_end_game_text(new_score);
            break;
        }
    }

    public static void main(String[] args)
    {
        try
        {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new main("Arkanoid BABYYYYYY!"));
            appgc.setDisplayMode(640, 480, false);
            appgc.start();
        }
        catch (SlickException ex)
        {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
