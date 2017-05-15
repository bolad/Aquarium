import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by bolad on 5/15/17.
 */
public class Aquarium extends Frame implements Runnable{

    Image aquariumImage; //background image of aquarium

    Image memoryImage; //created to avoid flickering of aquarium

    Graphics memoryGraphics;

    Image[] fishImages = new Image[2];

    MediaTracker tracker; //ensures images are loaded correctly

    Thread thread;

    public Aquarium(){
        setTitle("Stanley's Aquarium");
        tracker = new MediaTracker(this);

        fishImages[0] = Toolkit.getDefaultToolkit().getImage("/Users/bolad/Desktop/Programming/JAVA/Java After Hours/code/ch01/fish1.gif");
        tracker.addImage(fishImages[0], 0);
        fishImages[1] = Toolkit.getDefaultToolkit().getImage("/Users/bolad/Desktop/Programming/JAVA/Java After Hours/code/ch01/fish2.gif");
        tracker.addImage(fishImages[1], 0);

        aquariumImage = Toolkit.getDefaultToolkit().getImage("/Users/bolad/Desktop/Programming/JAVA/Java After Hours/code/ch01/bubbles.gif");
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
            //@Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        new Aquarium();
    }

    @Override
    public void run() {

        Rectangle edges = new Rectangle(0 + getInsets().left, 0 + getInsets().top
        , getSize().width - (getInsets().left + getInsets().right),
                getSize().height - (getInsets().top + getInsets().bottom));

    }
}
