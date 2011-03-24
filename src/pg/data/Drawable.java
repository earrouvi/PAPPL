package pg.data;
import java.awt.Graphics;

/**
 * A drawable interface for projective geometry objects
 * @author Cedric Telegone, ECN 2010
 *
 */

public interface Drawable {

	void paint(Graphics g, double mag);

}
