import org.junit.Test;

import animationcreator.TowerOfHanoiGenerator;

import static org.junit.Assert.assertEquals;

/**
 * JUnit test cases for the TowerOfHanoiGenerator class.
 */
public class TowerOfHanoiGeneratorTest {
  private TowerOfHanoiGenerator t1 = new TowerOfHanoiGenerator();

  @Test
  public void testMove() {
    t1.move(5, 1, 3);
    assertEquals("canvas 410 208\n" +
            "rectangle name disk1 min-x 190.0 min-y 168.0 width 20.0 height " +
            "18.0 color 0.49803922 0.7254902 0.96862745 from 1 to 1166\n" +
            "rectangle name disk2 min-x 178.75 min-y 186.0 width 42.5 height " +
            "18.0 color 0.4 0.6392157 0.19215687 from 1 to 1166\n" +
            "rectangle name disk3 min-x 167.5 min-y 204.0 width 65.0 height " +
            "18.0 color 0.61960787 0.80784315 0.4627451 from 1 to 1166\n" +
            "rectangle name disk4 min-x 156.25 min-y 222.0 width 87.5 height " +
            "18.0 color 0.98039216 0.49411765 0.8235294 from 1 to 1166\n" +
            "rectangle name disk5 min-x 145.0 min-y 240.0 width 110.0 height " +
            "18.0 color 0.22352941 0.84313726 0.7607843 from 1 to 1166\n" +
            "move name disk1 moveto 190.0 168.0 190.0 100.0 from 15 to 25\n" +
            "move name disk1 moveto 190.0 100.0 490.0 100.0 from 25 to 35\n" +
            "move name disk1 moveto 490.0 100.0 490.0 240.0 from 35 to 45\n" +
            "move name disk2 moveto 178.75 186.0 178.75 100.0 from 45 to 55\n" +
            "move name disk2 moveto 178.75 100.0 328.75 100.0 from 55 to 65\n" +
            "move name disk2 moveto 328.75 100.0 328.75 240.0 from 65 to 75\n" +
            "move name disk1 moveto 490.0 240.0 490.0 100.0 from 75 to 85\n" +
            "move name disk1 moveto 490.0 100.0 340.0 100.0 from 85 to 95\n" +
            "move name disk1 moveto 340.0 100.0 340.0 222.0 from 95 to 105\n" +
            "move name disk3 moveto 167.5 204.0 167.5 100.0 from 105 to 115\n" +
            "move name disk3 moveto 167.5 100.0 467.5 100.0 from 115 to 125\n" +
            "move name disk3 moveto 467.5 100.0 467.5 240.0 from 125 to 135\n" +
            "move name disk1 moveto 340.0 222.0 340.0 100.0 from 135 to 145\n" +
            "move name disk1 moveto 340.0 100.0 190.0 100.0 from 145 to 155\n" +
            "move name disk1 moveto 190.0 100.0 190.0 204.0 from 155 to 165\n" +
            "move name disk2 moveto 328.75 240.0 328.75 100.0 from 165 to 175\n" +
            "move name disk2 moveto 328.75 100.0 478.75 100.0 from 175 to 185\n" +
            "move name disk2 moveto 478.75 100.0 478.75 222.0 from 185 to 195\n" +
            "move name disk1 moveto 190.0 204.0 190.0 100.0 from 195 to 205\n" +
            "move name disk1 moveto 190.0 100.0 490.0 100.0 from 205 to 215\n" +
            "move name disk1 moveto 490.0 100.0 490.0 204.0 from 215 to 225\n" +
            "move name disk4 moveto 156.25 222.0 156.25 100.0 from 225 to 235\n" +
            "move name disk4 moveto 156.25 100.0 306.25 100.0 from 235 to 245\n" +
            "move name disk4 moveto 306.25 100.0 306.25 240.0 from 245 to 255\n" +
            "move name disk1 moveto 490.0 204.0 490.0 100.0 from 255 to 265\n" +
            "move name disk1 moveto 490.0 100.0 340.0 100.0 from 265 to 275\n" +
            "move name disk1 moveto 340.0 100.0 340.0 222.0 from 275 to 285\n" +
            "move name disk2 moveto 478.75 222.0 478.75 100.0 from 285 to 295\n" +
            "move name disk2 moveto 478.75 100.0 178.75 100.0 from 295 to 305\n" +
            "move name disk2 moveto 178.75 100.0 178.75 222.0 from 305 to 315\n" +
            "move name disk1 moveto 340.0 222.0 340.0 100.0 from 315 to 325\n" +
            "move name disk1 moveto 340.0 100.0 190.0 100.0 from 325 to 335\n" +
            "move name disk1 moveto 190.0 100.0 190.0 204.0 from 335 to 345\n" +
            "move name disk3 moveto 467.5 240.0 467.5 100.0 from 345 to 355\n" +
            "move name disk3 moveto 467.5 100.0 317.5 100.0 from 355 to 365\n" +
            "move name disk3 moveto 317.5 100.0 317.5 222.0 from 365 to 375\n" +
            "move name disk1 moveto 190.0 204.0 190.0 100.0 from 375 to 385\n" +
            "move name disk1 moveto 190.0 100.0 490.0 100.0 from 385 to 395\n" +
            "move name disk1 moveto 490.0 100.0 490.0 240.0 from 395 to 405\n" +
            "move name disk2 moveto 178.75 222.0 178.75 100.0 from 405 to 415\n" +
            "move name disk2 moveto 178.75 100.0 328.75 100.0 from 415 to 425\n" +
            "move name disk2 moveto 328.75 100.0 328.75 204.0 from 425 to 435\n" +
            "move name disk1 moveto 490.0 240.0 490.0 100.0 from 435 to 445\n" +
            "move name disk1 moveto 490.0 100.0 340.0 100.0 from 445 to 455\n" +
            "move name disk1 moveto 340.0 100.0 340.0 186.0 from 455 to 465\n" +
            "move name disk5 moveto 145.0 240.0 145.0 100.0 from 465 to 475\n" +
            "move name disk5 moveto 145.0 100.0 445.0 100.0 from 475 to 485\n" +
            "move name disk5 moveto 445.0 100.0 445.0 240.0 from 485 to 495\n" +
            "move name disk1 moveto 340.0 186.0 340.0 100.0 from 495 to 505\n" +
            "move name disk1 moveto 340.0 100.0 190.0 100.0 from 505 to 515\n" +
            "move name disk1 moveto 190.0 100.0 190.0 240.0 from 515 to 525\n" +
            "move name disk2 moveto 328.75 204.0 328.75 100.0 from 525 to 535\n" +
            "move name disk2 moveto 328.75 100.0 478.75 100.0 from 535 to 545\n" +
            "move name disk2 moveto 478.75 100.0 478.75 222.0 from 545 to 555\n" +
            "move name disk1 moveto 190.0 240.0 190.0 100.0 from 555 to 565\n" +
            "move name disk1 moveto 190.0 100.0 490.0 100.0 from 565 to 575\n" +
            "move name disk1 moveto 490.0 100.0 490.0 204.0 from 575 to 585\n" +
            "move name disk3 moveto 317.5 222.0 317.5 100.0 from 585 to 595\n" +
            "move name disk3 moveto 317.5 100.0 167.5 100.0 from 595 to 605\n" +
            "move name disk3 moveto 167.5 100.0 167.5 240.0 from 605 to 615\n" +
            "move name disk1 moveto 490.0 204.0 490.0 100.0 from 615 to 625\n" +
            "move name disk1 moveto 490.0 100.0 340.0 100.0 from 625 to 635\n" +
            "move name disk1 moveto 340.0 100.0 340.0 222.0 from 635 to 645\n" +
            "move name disk2 moveto 478.75 222.0 478.75 100.0 from 645 to 655\n" +
            "move name disk2 moveto 478.75 100.0 178.75 100.0 from 655 to 665\n" +
            "move name disk2 moveto 178.75 100.0 178.75 222.0 from 665 to 675\n" +
            "move name disk1 moveto 340.0 222.0 340.0 100.0 from 675 to 685\n" +
            "move name disk1 moveto 340.0 100.0 190.0 100.0 from 685 to 695\n" +
            "move name disk1 moveto 190.0 100.0 190.0 204.0 from 695 to 705\n" +
            "move name disk4 moveto 306.25 240.0 306.25 100.0 from 705 to 715\n" +
            "move name disk4 moveto 306.25 100.0 456.25 100.0 from 715 to 725\n" +
            "move name disk4 moveto 456.25 100.0 456.25 222.0 from 725 to 735\n" +
            "move name disk1 moveto 190.0 204.0 190.0 100.0 from 735 to 745\n" +
            "move name disk1 moveto 190.0 100.0 490.0 100.0 from 745 to 755\n" +
            "move name disk1 moveto 490.0 100.0 490.0 204.0 from 755 to 765\n" +
            "move name disk2 moveto 178.75 222.0 178.75 100.0 from 765 to 775\n" +
            "move name disk2 moveto 178.75 100.0 328.75 100.0 from 775 to 785\n" +
            "move name disk2 moveto 328.75 100.0 328.75 240.0 from 785 to 795\n" +
            "move name disk1 moveto 490.0 204.0 490.0 100.0 from 795 to 805\n" +
            "move name disk1 moveto 490.0 100.0 340.0 100.0 from 805 to 815\n" +
            "move name disk1 moveto 340.0 100.0 340.0 222.0 from 815 to 825\n" +
            "move name disk3 moveto 167.5 240.0 167.5 100.0 from 825 to 835\n" +
            "move name disk3 moveto 167.5 100.0 467.5 100.0 from 835 to 845\n" +
            "move name disk3 moveto 467.5 100.0 467.5 204.0 from 845 to 855\n" +
            "move name disk1 moveto 340.0 222.0 340.0 100.0 from 855 to 865\n" +
            "move name disk1 moveto 340.0 100.0 190.0 100.0 from 865 to 875\n" +
            "move name disk1 moveto 190.0 100.0 190.0 240.0 from 875 to 885\n" +
            "move name disk2 moveto 328.75 240.0 328.75 100.0 from 885 to 895\n" +
            "move name disk2 moveto 328.75 100.0 478.75 100.0 from 895 to 905\n" +
            "move name disk2 moveto 478.75 100.0 478.75 186.0 from 905 to 915\n" +
            "move name disk1 moveto 190.0 240.0 190.0 100.0 from 915 to 925\n" +
            "move name disk1 moveto 190.0 100.0 490.0 100.0 from 925 to 935\n" +
            "move name disk1 moveto 490.0 100.0 490.0 168.0 from 935 to 945", t1.toString());
  }

  @Test
  public void addMoveTest() {
    t1.addMove(1, 2);
    assertEquals("canvas 410 208\n" +
            "rectangle name disk1 min-x 190.0 min-y 168.0 " +
            "width 20.0 height 18.0 color 0.49803922 " +
            "0.7254902 0.96862745 from 1 to 1166\n" +
            "rectangle name disk2 min-x 178.75 min-y 186.0 " +
            "width 42.5 height 18.0 color 0.4 0.6392157 " +
            "0.19215687 from 1 to 1166\n" +
            "rectangle name disk3 min-x 167.5 min-y 204.0 " +
            "width 65.0 height 18.0 color 0.61960787 " +
            "0.80784315 0.4627451 from 1 to 1166\n" +
            "rectangle name disk4 min-x 156.25 min-y 222.0 " +
            "width 87.5 height 18.0 color 0.98039216 0.49411765 " +
            "0.8235294 from 1 to 1166\n" +
            "rectangle name disk5 min-x 145.0 min-y 240.0 " +
            "width 110.0 height 18.0 color 0.22352941 " +
            "0.84313726 0.7607843 from 1 to 1166\n" +
            "move name disk1 moveto 190.0 168.0 190.0 " +
            "100.0 from 15 to 25\n" +
            "move name disk1 moveto 190.0 100.0 " +
            "340.0 100.0 from 25 to 35\n" +
            "move name disk1 moveto 340.0 100.0 " +
            "340.0 240.0 from 35 to 45", t1.toString());
  }

}