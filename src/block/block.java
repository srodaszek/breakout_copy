package block;
import org.newdawn.slick.*;

import java.util.ArrayList;

public class block {
    private int pos_x;
    private int pos_y;
    private static Image all_blocks;
    public static Image[] blocks_images;
    private int current_block_image;
    private int power;//0 for nothing,1 for speed_up,2 for speed_down,3 for narrow_bar,4 for wide_bar

public block(int index,int pos_x,int pos_y,int power){
    try {
        Image all_blocks = new Image("/resources/blocks.png");
        blocks_images=new Image[]{all_blocks.getSubImage(0,0,8,16),
                                    all_blocks.getSubImage(8,0,16,8),
                                    all_blocks.getSubImage(8,8,16,8),
                                    all_blocks.getSubImage(24,0,16,8),
                                    all_blocks.getSubImage(24,8,16,8),
                                    all_blocks.getSubImage(40,0,16,8),
                                    all_blocks.getSubImage(40,8,16,8),
                                    all_blocks.getSubImage(56,0,16,8),
                                    all_blocks.getSubImage(56,8,16,8)};
    }catch(SlickException e){
        e.printStackTrace();
    }
    this.pos_x=pos_x;
    this.pos_y=pos_y;
    current_block_image=index;
    this.power=power;
}
    public int get_x(){return pos_x;}
    public int get_y(){return pos_y;}
    public int get_index(){return current_block_image;}
    public int get_power(){return power;}
}
