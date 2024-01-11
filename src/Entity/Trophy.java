package Entity;

import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Trophy extends MapObject {

    private boolean hit;
    private boolean remove;
    private BufferedImage[] sprites;
    private BufferedImage[] hitSprites;

    public Trophy(TileMap tm) {

        super(tm);

        facingRight = right;

        if(right) dx = moveSpeed;
        else dx = -moveSpeed;

        width = 30;
        height = 30;
        cwidth = 14;
        cheight = 14;

        // load sprites
        try {

            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/Sprites/Player/trophyplaceholder.gif"
                    )
            );

            sprites = new BufferedImage[4];
            for(int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(
                        i * width,
                        0,
                        width,
                        height
                );
            }

            hitSprites = new BufferedImage[3];
            for(int i = 0; i < hitSprites.length; i++) {
                hitSprites[i] = spritesheet.getSubimage(
                        i * width,
                        height,
                        width,
                        height
                );
            }

            animation = new Animation();
            animation.setFrames(sprites);
            animation.setDelay(70);

        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }


    public void setHit() {
        if(hit) return;
        hit = true;
        animation.setFrames(hitSprites);
        animation.setDelay(70);
        dx = 0;
    }

    public boolean shouldRemove() { return remove; }

    public void update() {

        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        if(dx == 0 && !hit) {
            setHit();
        }

        animation.update();
        if(hit && animation.hasPlayedOnce()) {
            remove = true;
        }

    }

    public void draw(Graphics2D g) {

        setMapPosition();

        super.draw(g);

    }

}