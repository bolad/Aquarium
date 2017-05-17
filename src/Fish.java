import java.awt.*;
import java.util.Random;

/**
 * Created by bolad on 5/16/17.
 */
public class Fish {

    Component tank;
    Image image1, image2;
    Point location;
    Point velocity;
    Rectangle edges;
    Random random;

    public Fish(Image image1, Image image2, Rectangle edges, Component tank){

        random = new Random(System.currentTimeMillis());
        this.tank = tank;
        this.image1 = image1;
        this.image2 = image2;
        this.edges = edges;
        this.location = new Point(100 + (Math.abs(random.nextInt()) % 300),
        100 + (Math.abs(100 + random.nextInt()) % 100));
        this.velocity = new Point(random.nextInt() % 8, random.nextInt() % 8);
    }

    public void swim(){
        //ensures that no velocity component exceeds an absolute magnitude of 8;
        if (random.nextInt() % 7 <= 1) {
            velocity.x += random.nextInt() % 4;
            velocity.x = Math.min(velocity.x, 8);
            velocity.x = Math.max(velocity.x, -8);

            velocity.y += random.nextInt() % 4;
            velocity.y = Math.min(velocity.y, 8);
            velocity.y = Math.max(velocity.y, -8);
        }

        //Add x and y components of the fish's velocity to move the fish to a new position
        location.x += velocity.x;
        location.y += velocity.y;

        /*
        determine if updating the fish's position has put it beyond the edge of the aquarium,
        in which case the fish should bounce off the edge of the tank by reversing the sign of
         its x or y velocity
         */
        if (location.x < edges.x) {
            location.x = edges.x;
            velocity.x = -velocity.x;
        }

        if ((location.x + image1.getWidth(tank)) > (edges.x + edges.width)) {
            location.x = edges.x + edges.width - image1.getWidth(tank);
            velocity.x = -velocity.x;
        }

        if (location.y < edges.y) {
            location.y = edges.y;
            velocity.y = -velocity.y;
        }

        if ((location.y + image1.getHeight(tank)) > (edges.y + edges.height)){
             location.y = edges.y + edges.height - image1.getHeight(tank);
            velocity.y = -velocity.y;
        }


    }

    /*
    The drawFishImage method is used to draw the fish when needed in the memory-based
    image before that image is flashed onto the screen. This method is passed the graphics object
    it should use to draw the fish. Here, if the image of the fish is going to the left use a
    negative x velocity and vice versa
     */

    public void drawFishImage(Graphics graphics) {

        if(velocity.x < 0) {
            graphics.drawImage(image1, location.x, location.y, tank);
        }
        else{
            graphics.drawImage(image2, location.x, location.y, tank);
        }
    }


}
