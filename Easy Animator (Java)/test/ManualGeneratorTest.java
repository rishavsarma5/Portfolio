import org.junit.Test;

import animationcreator.IAnimationCreator;
import animationcreator.ManualGenerator;

import static org.junit.Assert.assertEquals;

/**
 * JUnit test cases for the ManualGenerator class.
 */
public class ManualGeneratorTest {

  private IAnimationCreator newCreator = new ManualGenerator();

  @Test(expected = UnsupportedOperationException.class)
  public void testMove() {
    newCreator.move(5,4,1);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testAddMove() {
    newCreator.addMove(2,1);
  }

  @Test
  public void testToString() {
    assertEquals("canvas 400 300\n" +
            "oval name disk1 center-x 190.0 center-y 168.0 x-radius " +
            "20.0 y-radius 18 color 0.49875963 " +
            "0.72675294 0.96805584 from 1 to 1166\n" +
            "oval name disk2 center-x 178.75 center-y 186.0 x-radius " +
            "42.5 y-radius 18 color 0.40170985 0.6379354 0.19164959 " +
            "from 1 to 1166\n" +
            "oval name disk3 center-x 167.5 center-y 204.0 x-radius " +
            "65.0 y-radius 18 color 0.61867917 0.80975264 0.46404484 " +
            "from 1 to 1166\n" +
            "oval name disk4 center-x 156.25 center-y 222.0 x-radius " +
            "87.5 y-radius 18 color 0.98098457 0.4923268 " +
            "0.8237369 from 1 to 1166\n" +
            "oval name disk5 center-x 145.0 center-y 240.0 x-radius " +
            "110.0 y-radius 18 color 0.2233703 0.84182423 " +
            "0.7612209 from 1 to 1166\n" +
            "move name disk1 moveto 190.0 168.0 191.0 169.0 from 1 to 2\n" +
            "move name disk2 moveto 178.75 186.0 179.75 187.0 from 1 to 2\n" +
            "move name disk3 moveto 167.5 204.0 168.5 205.0 from 1 to 2\n" +
            "move name disk4 moveto 156.25 222.0 157.25 223.0 from 1 to 2\n" +
            "move name disk5 moveto 145.0 240.0 146.0 241.0 from 1 to 2\n" +
            "move name disk1 moveto 191.0 169.0 192.0 170.0 from 2 to 3\n" +
            "move name disk2 moveto 179.75 187.0 180.75 188.0 from 2 to 3\n" +
            "move name disk3 moveto 168.5 205.0 169.5 206.0 from 2 to 3\n" +
            "move name disk4 moveto 157.25 223.0 158.25 224.0 from 2 to 3\n" +
            "move name disk5 moveto 146.0 241.0 147.0 242.0 from 2 to 3\n" +
            "move name disk1 moveto 192.0 170.0 193.0 171.0 from 3 to 4\n" +
            "move name disk2 moveto 180.75 188.0 181.75 189.0 from 3 to 4\n" +
            "move name disk3 moveto 169.5 206.0 170.5 207.0 from 3 to 4\n" +
            "move name disk4 moveto 158.25 224.0 159.25 225.0 from 3 to 4\n" +
            "move name disk5 moveto 147.0 242.0 148.0 243.0 from 3 to 4\n" +
            "move name disk1 moveto 193.0 171.0 194.0 172.0 from 4 to 5\n" +
            "move name disk2 moveto 181.75 189.0 182.75 190.0 from 4 to 5\n" +
            "move name disk3 moveto 170.5 207.0 171.5 208.0 from 4 to 5\n" +
            "move name disk4 moveto 159.25 225.0 160.25 226.0 from 4 to 5\n" +
            "move name disk5 moveto 148.0 243.0 149.0 244.0 from 4 to 5\n" +
            "move name disk1 moveto 194.0 172.0 195.0 173.0 from 5 to 6\n" +
            "move name disk2 moveto 182.75 190.0 183.75 191.0 from 5 to 6\n" +
            "move name disk3 moveto 171.5 208.0 172.5 209.0 from 5 to 6\n" +
            "move name disk4 moveto 160.25 226.0 161.25 227.0 from 5 to 6\n" +
            "move name disk5 moveto 149.0 244.0 150.0 245.0 from 5 to 6\n" +
            "move name disk1 moveto 195.0 173.0 196.0 174.0 from 6 to 7\n" +
            "move name disk2 moveto 183.75 191.0 184.75 192.0 from 6 to 7\n" +
            "move name disk3 moveto 172.5 209.0 173.5 210.0 from 6 to 7\n" +
            "move name disk4 moveto 161.25 227.0 162.25 228.0 from 6 to 7\n" +
            "move name disk5 moveto 150.0 245.0 151.0 246.0 from 6 to 7\n" +
            "move name disk1 moveto 196.0 174.0 197.0 175.0 from 7 to 8\n" +
            "move name disk2 moveto 184.75 192.0 185.75 193.0 from 7 to 8\n" +
            "move name disk3 moveto 173.5 210.0 174.5 211.0 from 7 to 8\n" +
            "move name disk4 moveto 162.25 228.0 163.25 229.0 from 7 to 8\n" +
            "move name disk5 moveto 151.0 246.0 152.0 247.0 from 7 to 8\n" +
            "move name disk1 moveto 197.0 175.0 198.0 176.0 from 8 to 9\n" +
            "move name disk2 moveto 185.75 193.0 186.75 194.0 from 8 to 9\n" +
            "move name disk3 moveto 174.5 211.0 175.5 212.0 from 8 to 9\n" +
            "move name disk4 moveto 163.25 229.0 164.25 230.0 from 8 to 9\n" +
            "move name disk5 moveto 152.0 247.0 153.0 248.0 from 8 to 9\n" +
            "move name disk1 moveto 198.0 176.0 199.0 177.0 from 9 to 10\n" +
            "move name disk2 moveto 186.75 194.0 187.75 195.0 from 9 to 10\n" +
            "move name disk3 moveto 175.5 212.0 176.5 213.0 from 9 to 10\n" +
            "move name disk4 moveto 164.25 230.0 165.25 231.0 from 9 to 10\n" +
            "move name disk5 moveto 153.0 248.0 154.0 249.0 from 9 to 10\n" +
            "move name disk1 moveto 199.0 177.0 200.0 178.0 from 10 to 11\n" +
            "move name disk2 moveto 187.75 195.0 188.75 196.0 from 10 to 11\n" +
            "move name disk3 moveto 176.5 213.0 177.5 214.0 from 10 to 11\n" +
            "move name disk4 moveto 165.25 231.0 166.25 232.0 from 10 to 11\n" +
            "move name disk5 moveto 154.0 249.0 155.0 250.0 from 10 to 11\n" +
            "move name disk1 moveto 200.0 178.0 201.0 179.0 from 11 to 12\n" +
            "move name disk2 moveto 188.75 196.0 189.75 197.0 from 11 to 12\n" +
            "move name disk3 moveto 177.5 214.0 178.5 215.0 from 11 to 12\n" +
            "move name disk4 moveto 166.25 232.0 167.25 233.0 from 11 to 12\n" +
            "move name disk5 moveto 155.0 250.0 156.0 251.0 from 11 to 12\n" +
            "move name disk1 moveto 201.0 179.0 202.0 180.0 from 12 to 13\n" +
            "move name disk2 moveto 189.75 197.0 190.75 198.0 from 12 to 13\n" +
            "move name disk3 moveto 178.5 215.0 179.5 216.0 from 12 to 13\n" +
            "move name disk4 moveto 167.25 233.0 168.25 234.0 from 12 to 13\n" +
            "move name disk5 moveto 156.0 251.0 157.0 252.0 from 12 to 13\n" +
            "move name disk1 moveto 202.0 180.0 203.0 181.0 from 13 to 14\n" +
            "move name disk2 moveto 190.75 198.0 191.75 199.0 from 13 to 14\n" +
            "move name disk3 moveto 179.5 216.0 180.5 217.0 from 13 to 14\n" +
            "move name disk4 moveto 168.25 234.0 169.25 235.0 from 13 to 14\n" +
            "move name disk5 moveto 157.0 252.0 158.0 253.0 from 13 to 14\n" +
            "move name disk1 moveto 203.0 181.0 204.0 182.0 from 14 to 15\n" +
            "move name disk2 moveto 191.75 199.0 192.75 200.0 from 14 to 15\n" +
            "move name disk3 moveto 180.5 217.0 181.5 218.0 from 14 to 15\n" +
            "move name disk4 moveto 169.25 235.0 170.25 236.0 from 14 to 15\n" +
            "move name disk5 moveto 158.0 253.0 159.0 254.0 from 14 to 15\n" +
            "move name disk1 moveto 204.0 182.0 205.0 183.0 from 15 to 16\n" +
            "move name disk2 moveto 192.75 200.0 193.75 201.0 from 15 to 16\n" +
            "move name disk3 moveto 181.5 218.0 182.5 219.0 from 15 to 16\n" +
            "move name disk4 moveto 170.25 236.0 171.25 237.0 from 15 to 16\n" +
            "move name disk5 moveto 159.0 254.0 160.0 255.0 from 15 to 16\n" +
            "move name disk1 moveto 205.0 183.0 206.0 184.0 from 16 to 17\n" +
            "move name disk2 moveto 193.75 201.0 194.75 202.0 from 16 to 17\n" +
            "move name disk3 moveto 182.5 219.0 183.5 220.0 from 16 to 17\n" +
            "move name disk4 moveto 171.25 237.0 172.25 238.0 from 16 to 17\n" +
            "move name disk5 moveto 160.0 255.0 161.0 256.0 from 16 to 17\n" +
            "move name disk1 moveto 206.0 184.0 207.0 185.0 from 17 to 18\n" +
            "move name disk2 moveto 194.75 202.0 195.75 203.0 from 17 to 18\n" +
            "move name disk3 moveto 183.5 220.0 184.5 221.0 from 17 to 18\n" +
            "move name disk4 moveto 172.25 238.0 173.25 239.0 from 17 to 18\n" +
            "move name disk5 moveto 161.0 256.0 162.0 257.0 from 17 to 18\n" +
            "move name disk1 moveto 207.0 185.0 208.0 186.0 from 18 to 19\n" +
            "move name disk2 moveto 195.75 203.0 196.75 204.0 from 18 to 19\n" +
            "move name disk3 moveto 184.5 221.0 185.5 222.0 from 18 to 19\n" +
            "move name disk4 moveto 173.25 239.0 174.25 240.0 from 18 to 19\n" +
            "move name disk5 moveto 162.0 257.0 163.0 258.0 from 18 to 19\n" +
            "move name disk1 moveto 208.0 186.0 209.0 187.0 from 19 to 20\n" +
            "move name disk2 moveto 196.75 204.0 197.75 205.0 from 19 to 20\n" +
            "move name disk3 moveto 185.5 222.0 186.5 223.0 from 19 to 20\n" +
            "move name disk4 moveto 174.25 240.0 175.25 241.0 from 19 to 20\n" +
            "move name disk5 moveto 163.0 258.0 164.0 259.0 from 19 to 20\n" +
            "move name disk1 moveto 209.0 187.0 210.0 188.0 from 20 to 21\n" +
            "move name disk2 moveto 197.75 205.0 198.75 206.0 from 20 to 21\n" +
            "move name disk3 moveto 186.5 223.0 187.5 224.0 from 20 to 21\n" +
            "move name disk4 moveto 175.25 241.0 176.25 242.0 from 20 to 21\n" +
            "move name disk5 moveto 164.0 259.0 165.0 260.0 from 20 to 21\n" +
            "move name disk1 moveto 210.0 188.0 211.0 189.0 from 21 to 22\n" +
            "move name disk2 moveto 198.75 206.0 199.75 207.0 from 21 to 22\n" +
            "move name disk3 moveto 187.5 224.0 188.5 225.0 from 21 to 22\n" +
            "move name disk4 moveto 176.25 242.0 177.25 243.0 from 21 to 22\n" +
            "move name disk5 moveto 165.0 260.0 166.0 261.0 from 21 to 22\n" +
            "move name disk1 moveto 211.0 189.0 212.0 190.0 from 22 to 23\n" +
            "move name disk2 moveto 199.75 207.0 200.75 208.0 from 22 to 23\n" +
            "move name disk3 moveto 188.5 225.0 189.5 226.0 from 22 to 23\n" +
            "move name disk4 moveto 177.25 243.0 178.25 244.0 from 22 to 23\n" +
            "move name disk5 moveto 166.0 261.0 167.0 262.0 from 22 to 23\n" +
            "move name disk1 moveto 212.0 190.0 213.0 191.0 from 23 to 24\n" +
            "move name disk2 moveto 200.75 208.0 201.75 209.0 from 23 to 24\n" +
            "move name disk3 moveto 189.5 226.0 190.5 227.0 from 23 to 24\n" +
            "move name disk4 moveto 178.25 244.0 179.25 245.0 from 23 to 24\n" +
            "move name disk5 moveto 167.0 262.0 168.0 263.0 from 23 to 24\n" +
            "move name disk1 moveto 213.0 191.0 214.0 192.0 from 24 to 25\n" +
            "move name disk2 moveto 201.75 209.0 202.75 210.0 from 24 to 25\n" +
            "move name disk3 moveto 190.5 227.0 191.5 228.0 from 24 to 25\n" +
            "move name disk4 moveto 179.25 245.0 180.25 246.0 from 24 to 25\n" +
            "move name disk5 moveto 168.0 263.0 169.0 264.0 from 24 to 25\n" +
            "move name disk1 moveto 214.0 192.0 215.0 193.0 from 25 to 26\n" +
            "move name disk2 moveto 202.75 210.0 203.75 211.0 from 25 to 26\n" +
            "move name disk3 moveto 191.5 228.0 192.5 229.0 from 25 to 26\n" +
            "move name disk4 moveto 180.25 246.0 181.25 247.0 from 25 to 26\n" +
            "move name disk5 moveto 169.0 264.0 170.0 265.0 from 25 to 26\n" +
            "move name disk1 moveto 215.0 193.0 216.0 194.0 from 26 to 27\n" +
            "move name disk2 moveto 203.75 211.0 204.75 212.0 from 26 to 27\n" +
            "move name disk3 moveto 192.5 229.0 193.5 230.0 from 26 to 27\n" +
            "move name disk4 moveto 181.25 247.0 182.25 248.0 from 26 to 27\n" +
            "move name disk5 moveto 170.0 265.0 171.0 266.0 from 26 to 27\n" +
            "move name disk1 moveto 216.0 194.0 217.0 195.0 from 27 to 28\n" +
            "move name disk2 moveto 204.75 212.0 205.75 213.0 from 27 to 28\n" +
            "move name disk3 moveto 193.5 230.0 194.5 231.0 from 27 to 28\n" +
            "move name disk4 moveto 182.25 248.0 183.25 249.0 from 27 to 28\n" +
            "move name disk5 moveto 171.0 266.0 172.0 267.0 from 27 to 28\n" +
            "move name disk1 moveto 217.0 195.0 218.0 196.0 from 28 to 29\n" +
            "move name disk2 moveto 205.75 213.0 206.75 214.0 from 28 to 29\n" +
            "move name disk3 moveto 194.5 231.0 195.5 232.0 from 28 to 29\n" +
            "move name disk4 moveto 183.25 249.0 184.25 250.0 from 28 to 29\n" +
            "move name disk5 moveto 172.0 267.0 173.0 268.0 from 28 to 29\n" +
            "move name disk1 moveto 218.0 196.0 219.0 197.0 from 29 to 30\n" +
            "move name disk2 moveto 206.75 214.0 207.75 215.0 from 29 to 30\n" +
            "move name disk3 moveto 195.5 232.0 196.5 233.0 from 29 to 30\n" +
            "move name disk4 moveto 184.25 250.0 185.25 251.0 from 29 to 30\n" +
            "move name disk5 moveto 173.0 268.0 174.0 269.0 from 29 to 30\n" +
            "move name disk1 moveto 219.0 197.0 220.0 198.0 from 30 to 31\n" +
            "move name disk2 moveto 207.75 215.0 208.75 216.0 from 30 to 31\n" +
            "move name disk3 moveto 196.5 233.0 197.5 234.0 from 30 to 31\n" +
            "move name disk4 moveto 185.25 251.0 186.25 252.0 from 30 to 31\n" +
            "move name disk5 moveto 174.0 269.0 175.0 270.0 from 30 to 31\n" +
            "move name disk1 moveto 220.0 198.0 221.0 199.0 from 31 to 32\n" +
            "move name disk2 moveto 208.75 216.0 209.75 217.0 from 31 to 32\n" +
            "move name disk3 moveto 197.5 234.0 198.5 235.0 from 31 to 32\n" +
            "move name disk4 moveto 186.25 252.0 187.25 253.0 from 31 to 32\n" +
            "move name disk5 moveto 175.0 270.0 176.0 271.0 from 31 to 32\n" +
            "move name disk1 moveto 221.0 199.0 222.0 200.0 from 32 to 33\n" +
            "move name disk2 moveto 209.75 217.0 210.75 218.0 from 32 to 33\n" +
            "move name disk3 moveto 198.5 235.0 199.5 236.0 from 32 to 33\n" +
            "move name disk4 moveto 187.25 253.0 188.25 254.0 from 32 to 33\n" +
            "move name disk5 moveto 176.0 271.0 177.0 272.0 from 32 to 33\n" +
            "move name disk1 moveto 222.0 200.0 223.0 201.0 from 33 to 34\n" +
            "move name disk2 moveto 210.75 218.0 211.75 219.0 from 33 to 34\n" +
            "move name disk3 moveto 199.5 236.0 200.5 237.0 from 33 to 34\n" +
            "move name disk4 moveto 188.25 254.0 189.25 255.0 from 33 to 34\n" +
            "move name disk5 moveto 177.0 272.0 178.0 273.0 from 33 to 34\n" +
            "move name disk1 moveto 223.0 201.0 224.0 202.0 from 34 to 35\n" +
            "move name disk2 moveto 211.75 219.0 212.75 220.0 from 34 to 35\n" +
            "move name disk3 moveto 200.5 237.0 201.5 238.0 from 34 to 35\n" +
            "move name disk4 moveto 189.25 255.0 190.25 256.0 from 34 to 35\n" +
            "move name disk5 moveto 178.0 273.0 179.0 274.0 from 34 to 35\n" +
            "move name disk1 moveto 224.0 202.0 225.0 203.0 from 35 to 36\n" +
            "move name disk2 moveto 212.75 220.0 213.75 221.0 from 35 to 36\n" +
            "move name disk3 moveto 201.5 238.0 202.5 239.0 from 35 to 36\n" +
            "move name disk4 moveto 190.25 256.0 191.25 257.0 from 35 to 36\n" +
            "move name disk5 moveto 179.0 274.0 180.0 275.0 from 35 to 36\n" +
            "move name disk1 moveto 225.0 203.0 226.0 204.0 from 36 to 37\n" +
            "move name disk2 moveto 213.75 221.0 214.75 222.0 from 36 to 37\n" +
            "move name disk3 moveto 202.5 239.0 203.5 240.0 from 36 to 37\n" +
            "move name disk4 moveto 191.25 257.0 192.25 258.0 from 36 to 37\n" +
            "move name disk5 moveto 180.0 275.0 181.0 276.0 from 36 to 37\n" +
            "move name disk1 moveto 226.0 204.0 227.0 205.0 from 37 to 38\n" +
            "move name disk2 moveto 214.75 222.0 215.75 223.0 from 37 to 38\n" +
            "move name disk3 moveto 203.5 240.0 204.5 241.0 from 37 to 38\n" +
            "move name disk4 moveto 192.25 258.0 193.25 259.0 from 37 to 38\n" +
            "move name disk5 moveto 181.0 276.0 182.0 277.0 from 37 to 38\n" +
            "move name disk1 moveto 227.0 205.0 228.0 206.0 from 38 to 39\n" +
            "move name disk2 moveto 215.75 223.0 216.75 224.0 from 38 to 39\n" +
            "move name disk3 moveto 204.5 241.0 205.5 242.0 from 38 to 39\n" +
            "move name disk4 moveto 193.25 259.0 194.25 260.0 from 38 to 39\n" +
            "move name disk5 moveto 182.0 277.0 183.0 278.0 from 38 to 39\n" +
            "move name disk1 moveto 228.0 206.0 229.0 207.0 from 39 to 40\n" +
            "move name disk2 moveto 216.75 224.0 217.75 225.0 from 39 to 40\n" +
            "move name disk3 moveto 205.5 242.0 206.5 243.0 from 39 to 40\n" +
            "move name disk4 moveto 194.25 260.0 195.25 261.0 from 39 to 40\n" +
            "move name disk5 moveto 183.0 278.0 184.0 279.0 from 39 to 40\n" +
            "move name disk1 moveto 229.0 207.0 230.0 208.0 from 40 to 41\n" +
            "move name disk2 moveto 217.75 225.0 218.75 226.0 from 40 to 41\n" +
            "move name disk3 moveto 206.5 243.0 207.5 244.0 from 40 to 41\n" +
            "move name disk4 moveto 195.25 261.0 196.25 262.0 from 40 to 41\n" +
            "move name disk5 moveto 184.0 279.0 185.0 280.0 from 40 to 41\n" +
            "move name disk1 moveto 230.0 208.0 231.0 209.0 from 41 to 42\n" +
            "move name disk2 moveto 218.75 226.0 219.75 227.0 from 41 to 42\n" +
            "move name disk3 moveto 207.5 244.0 208.5 245.0 from 41 to 42\n" +
            "move name disk4 moveto 196.25 262.0 197.25 263.0 from 41 to 42\n" +
            "move name disk5 moveto 185.0 280.0 186.0 281.0 from 41 to 42\n" +
            "move name disk1 moveto 231.0 209.0 232.0 210.0 from 42 to 43\n" +
            "move name disk2 moveto 219.75 227.0 220.75 228.0 from 42 to 43\n" +
            "move name disk3 moveto 208.5 245.0 209.5 246.0 from 42 to 43\n" +
            "move name disk4 moveto 197.25 263.0 198.25 264.0 from 42 to 43\n" +
            "move name disk5 moveto 186.0 281.0 187.0 282.0 from 42 to 43\n" +
            "move name disk1 moveto 232.0 210.0 233.0 211.0 from 43 to 44\n" +
            "move name disk2 moveto 220.75 228.0 221.75 229.0 from 43 to 44\n" +
            "move name disk3 moveto 209.5 246.0 210.5 247.0 from 43 to 44\n" +
            "move name disk4 moveto 198.25 264.0 199.25 265.0 from 43 to 44\n" +
            "move name disk5 moveto 187.0 282.0 188.0 283.0 from 43 to 44\n" +
            "move name disk1 moveto 233.0 211.0 234.0 212.0 from 44 to 45\n" +
            "move name disk2 moveto 221.75 229.0 222.75 230.0 from 44 to 45\n" +
            "move name disk3 moveto 210.5 247.0 211.5 248.0 from 44 to 45\n" +
            "move name disk4 moveto 199.25 265.0 200.25 266.0 from 44 to 45\n" +
            "move name disk5 moveto 188.0 283.0 189.0 284.0 from 44 to 45\n" +
            "move name disk1 moveto 234.0 212.0 235.0 213.0 from 45 to 46\n" +
            "move name disk2 moveto 222.75 230.0 223.75 231.0 from 45 to 46\n" +
            "move name disk3 moveto 211.5 248.0 212.5 249.0 from 45 to 46\n" +
            "move name disk4 moveto 200.25 266.0 201.25 267.0 from 45 to 46\n" +
            "move name disk5 moveto 189.0 284.0 190.0 285.0 from 45 to 46\n" +
            "move name disk1 moveto 235.0 213.0 236.0 214.0 from 46 to 47\n" +
            "move name disk2 moveto 223.75 231.0 224.75 232.0 from 46 to 47\n" +
            "move name disk3 moveto 212.5 249.0 213.5 250.0 from 46 to 47\n" +
            "move name disk4 moveto 201.25 267.0 202.25 268.0 from 46 to 47\n" +
            "move name disk5 moveto 190.0 285.0 191.0 286.0 from 46 to 47\n" +
            "move name disk1 moveto 236.0 214.0 237.0 215.0 from 47 to 48\n" +
            "move name disk2 moveto 224.75 232.0 225.75 233.0 from 47 to 48\n" +
            "move name disk3 moveto 213.5 250.0 214.5 251.0 from 47 to 48\n" +
            "move name disk4 moveto 202.25 268.0 203.25 269.0 from 47 to 48\n" +
            "move name disk5 moveto 191.0 286.0 192.0 287.0 from 47 to 48\n" +
            "move name disk1 moveto 237.0 215.0 238.0 216.0 from 48 to 49\n" +
            "move name disk2 moveto 225.75 233.0 226.75 234.0 from 48 to 49\n" +
            "move name disk3 moveto 214.5 251.0 215.5 252.0 from 48 to 49\n" +
            "move name disk4 moveto 203.25 269.0 204.25 270.0 from 48 to 49\n" +
            "move name disk5 moveto 192.0 287.0 193.0 288.0 from 48 to 49\n" +
            "move name disk1 moveto 238.0 216.0 239.0 217.0 from 49 to 50\n" +
            "move name disk2 moveto 226.75 234.0 227.75 235.0 from 49 to 50\n" +
            "move name disk3 moveto 215.5 252.0 216.5 253.0 from 49 to 50\n" +
            "move name disk4 moveto 204.25 270.0 205.25 271.0 from 49 to 50\n" +
            "move name disk5 moveto 193.0 288.0 194.0 289.0 from 49 to 50\n" +
            "move name disk1 moveto 239.0 217.0 240.0 218.0 from 50 to 51\n" +
            "move name disk2 moveto 227.75 235.0 228.75 236.0 from 50 to 51\n" +
            "move name disk3 moveto 216.5 253.0 217.5 254.0 from 50 to 51\n" +
            "move name disk4 moveto 205.25 271.0 206.25 272.0 from 50 to 51\n" +
            "move name disk5 moveto 194.0 289.0 195.0 290.0 from 50 to 51"
            ,newCreator.toString());
  }

}