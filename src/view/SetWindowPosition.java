package view;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;


public class SetWindowPosition {
    
    /**
     * Centers the window.
     */
    public static void setPosition(Container window) {

        final Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        final int x = (int) ((dimension.getWidth() - window.getWidth()) / 2);
        final int y = (int) ((dimension.getHeight() - window.getHeight()) / 2);
        window.setLocation(x, y);

        ((Window) window).pack();

    }

}
