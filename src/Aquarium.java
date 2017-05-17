import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

/**
 * Created by bolad on 5/15/17.
 */
public class Aquarium extends Frame implements Runnable {

    Image aquariumImage; //background image of aquarium

    Image memoryImage; //creates an offscreen object in memory to avoid flickering of aquarium

    Graphics memoryGraphics; //creates a Graphics object used to draw in the new memory image

    Image[] fishImages = new Image[2];

    MediaTracker tracker; //ensures images are loaded correctly

    int numberOfFish = 12;

    int sleepTime = 110; //how long the thread sleeps between moving individual fish

    Vector<Fish> fishes = new Vector<>(); //keeps track of the fish objects

    Fish fish;

    boolean runOk = true; // used to end the thread when the application is ended

    Thread thread;

    public Aquarium() {

        setTitle("Stanley's Aquarium");

        tracker = new MediaTracker(this); //the this keyword here passes reference to current instance of a MediaTracker

        fishImages[0] = Toolkit.getDefaultToolkit().getImage("fish1.gif");
        tracker.addImage(fishImages[0], 0);

        fishImages[1] = Toolkit.getDefaultToolkit().getImage("fish2.gif");
        tracker.addImage(fishImages[1], 0);

        aquariumImage = Toolkit.getDefaultToolkit().getImage("bubbles.gif");
        tracker.addImage(aquariumImage, 0);

        try {
            tracker.waitForID(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        setSize(aquariumImage.getWidth(this), aquariumImage.getHeight(this));
        setResizable(false);
        setVisible(true);

        memoryImage = createImage(getSize().width, getSize().height);
        memoryGraphics = memoryImage.getGraphics();

        thread = new Thread(this);
        thread.start();

        /*set the windows close event which occurs when the user clicks the close button at
        upper right corner of the window
         */
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                runOk = false;
                System.exit(0);
            }
        });
    }


    @Override
    public void run() {

        Rectangle edges = new Rectangle(0 + getInsets().left, 0 + getInsets().top
                , getSize().width - (getInsets().left + getInsets().right),
                getSize().height - (getInsets().top + getInsets().bottom));

        for (int i = 0; i < numberOfFish; i++) {
            fishes.add(new Fish(fishImages[0], fishImages[1], edges, this));

            try {
                Thread.sleep(20);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (runOk) {
            for (int i=0; i<numberOfFish; i++) {
                fish = fishes.elementAt(i);
                fish.swim();
            }

            try {
                Thread.sleep(sleepTime);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
            /*
            the repaint calls the update method, which is where
            flickering usually happens, because by default, the update
            method first redraws the entire window using the background
            window color.
             */
            repaint();
        }

    }
    /*
    You can draw the background image in the memory image with the drawImage method
    by passing it the image to draw, the X and Y location at which to draw the new image,
    and an object that acts as an image observer in case you want to be notified of the
    events that happen as the image is loaded (Aquarium doesn't make use of these events,
    so the code simply passes the current object as the image observer)
     */
    public void update(Graphics graphics) {

        memoryGraphics.drawImage(aquariumImage, 0, 0, this);

        //draw each fish in the memory image
        for (int i=0; i<numberOfFish; i++){
            fishes.elementAt(i).drawFishImage(memoryGraphics);
        }

        //flash the offscreen image(memoryImage) onto the screen with minimum flicker
        graphics.drawImage(memoryImage, 0, 0, this);

    }


}
