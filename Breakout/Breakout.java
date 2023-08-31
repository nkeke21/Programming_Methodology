
/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	/** x coordinate */
	private static final int START_X = ((WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) % NBRICKS_PER_ROW) / 2;

	private RandomGenerator rgen = RandomGenerator.getInstance();
	AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
	/** start x coordinate of ball */
	private static final int BALL_X = WIDTH / 2 - BALL_RADIUS;
	/** start y coordinate of ball */
	private static final int BALL_Y = HEIGHT / 2 - BALL_RADIUS;
	private GRect paddle;
	private GOval ball;
	private double vx, vy; // vx and vy speeds of ball*/
	private double rmv_brck;// amount of removed bricks
	private int DELAY = 9;// pause time
	private int count;// counting how many times the ball hit paddle

	/* Method: run() */
	/** Runs the Breakout program. */
	public void run() {
		/* You fill this in, along with any subsidiary methods */
		addMouseListeners();
		drawing();
		gaming();
		ending();
	}
	/** function drawing consists painting bricks, paddle and some extra things */
	private void drawing() {
		drawLines(NBRICK_ROWS);
		paddle();
		label();// extension that shows how much score you have got
		lab_lvl();// extension that shows which level are you on
	}
	/** function gaming is one of the most important parts where the game is in progress*/
	private void gaming() {
		for (int i = 0; i < NTURNS; i++) {
			if (rmv_brck < 100) {
				ball();
			}
			moveBall();
			pause(500);
		}
	}

	/**this function tells us if we win or lose after game and also it will give us smile */
	private void ending() {
		removeAll();
		setBackground(Color.BLACK);
		if (rmv_brck == 100) {
			winning();
		} else {
			losing();
		}
		smile(); // adding smile, makes our game funnier(extension)
	}

	/** drawing one thousand bricks */
	private void drawLines(int NBRICK_ROWS) {
		for (int i = 0; i < NBRICK_ROWS; i++) {
			/** y coordinate for each brick per row */
			int y = BRICK_Y_OFFSET + (BRICK_HEIGHT + BRICK_SEP) * i;
			/** there is a method how bricks are colored */
			if (i % NBRICK_ROWS <= 1) {
				drawLine(Color.RED, START_X, y);
			} else if (i % NBRICK_ROWS <= 3) {
				drawLine(Color.ORANGE, START_X, y);
			} else if (i % NBRICK_ROWS <= 5) {
				drawLine(Color.YELLOW, START_X, y);
			} else if (i % NBRICK_ROWS <= 7) {
				drawLine(Color.GREEN, START_X, y);
			} else if (i % NBRICK_ROWS <= 9) {
				drawLine(Color.CYAN, START_X, y);
			}
		}
	}

	/** drawing one line of bricks(consist 10 bricks) */
	private void drawLine(Color color, int START_X, int y) {
		for (int i = 0; i < NBRICKS_PER_ROW; i++) {
			/** x coordinate for each brick in a row */
			int brickx = START_X + i * (BRICK_WIDTH + BRICK_SEP);
			GRect brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
			brick.setFilled(true);
			brick.setColor(color);
			add(brick, brickx, y);
		}
	}

	/** this function creates the paddle */
	public void paddle() {
		paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
		/** start x coordinate of paddle */
		int paddx = (WIDTH - PADDLE_WIDTH) / 2;
		/** start y coordinate of paddle */
		int paddy = getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT;
		paddle.setFilled(true);
		add(paddle, paddx, paddy);
	}

	/** moving paddle with mouse */
	public void mouseMoved(MouseEvent e) {
		if (e.getX() < getWidth() - PADDLE_WIDTH / 2 && e.getX() > PADDLE_WIDTH / 2) {
			double lastX = paddle.getX() + PADDLE_WIDTH / 2;
			paddle.move(e.getX() - lastX, 0);
		}
	}

	/** creating ball */
	private void ball() {
		ball = new GOval(2 * BALL_RADIUS, 2 * BALL_RADIUS);
		ball.setFilled(true);
		add(ball, BALL_X, BALL_Y);
		vx = rgen.nextDouble(1.0, 3.0); // this helps our game not to be boring
		if (rgen.nextBoolean(0.5)) {
			vx = -vx;
		}
		vy = 3.0; // speed of vy
	}

	/** This function describe the movement of the ball */
	private void moveBall() {
		while (true) {
			pause(DELAY);
			ball.move(vx, vy);
			bouncingFromWalls();
			if (ball.getY() + 2 * BALL_RADIUS > getHeight()) {
				remove(ball);
				break;
			}
			hiting();// meeting bricks or paddle

			/** if all bricks are removed we need to stop the game */
			if (rmv_brck == 100) {
				break;
			}
		}
	}

	/**
	 * if ball goes out of canvas, the next functions help the ball to be returned
	 * in canvas
	 */
	private void bouncingFromWalls() {
		/** the next two step make ball not to cross wall for any situation */
		double vxx = Math.min(vx, 2 * BALL_RADIUS);
		double vyy = Math.min(vy, 2 * BALL_RADIUS);
		if (ball.getX() + 2 * BALL_RADIUS > WIDTH) {
			vx = -vxx;
		}
		if (ball.getX() < 0) {
			vx = -vxx;
		}
		if (ball.getY() < 0) {
			vy = -vyy;
		}
	}

	/** this function helps us to know if a ball crash something */
	private GObject getCollidingObject() {
		/**
		 * on the next four lines i wrote coordinates of each edges of a square, in witch
		 * ball is painted.
		 */
		double balX1 = ball.getX();
		double balX2 = ball.getX() + 2 * BALL_RADIUS;
		double balY1 = ball.getY();
		double balY2 = ball.getY() + 2 * BALL_RADIUS;

		/** returning an object that met a ball */
		if (getElementAt(balX1, balY1) != null) {
			return getElementAt(balX1, balY1);
		} else if (getElementAt(balX1, balY2) != null) {
			return getElementAt(balX1, balY2);
		} else if (getElementAt(balX2, balY1) != null) {
			return getElementAt(balX2, balY1);
		} else if (getElementAt(balX1, balY2) != null) {
			return getElementAt(balX1, balY2);
		}
		return null;
	}

	private GObject collider; // object that met a ball

	/**
	 * this function shows us what will happen if ball will crash brick or paddle
	 */
	private void hiting() {
		collider = getCollidingObject();
		boolean brick = collider != null && collider != label && collider != lab_lvl;

		/** if paddle crash the ball, we want the ball change direction */

		boolean paddle1 = ball.getX() + 1.5 * BALL_RADIUS < paddle.getX();
		boolean paddle2 = ball.getX() + 0.5 * BALL_RADIUS > paddle.getX() + PADDLE_WIDTH;

		if (paddle == collider) {
			if (paddle1) {
				ball.setLocation(paddle.getX() - 2 * BALL_RADIUS, ball.getY());
				vx = -vx;
			} else if (paddle2) {
				ball.setLocation(paddle.getX() + PADDLE_WIDTH, ball.getY());
				vx = -vx;
			} else {
				/*
				 * if we want ball not to be stuck in paddle we should return it to ovaly
				 * coordinate
				 */
				int ovaly = getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT - 2 * BALL_RADIUS;
				ball.setLocation(ball.getX(), ovaly);
				vy = -vy; // changing direction
			}
			bounceClip.play();
			count++;
		} else if (brick) {
			/**
			 * on the next lines i wrote program that describe what will happen if the ball
			 * crash bricks
			 */

			if (ball.getY() + 2 * BALL_RADIUS <= collider.getY() + vy) {
				remove(collider);
				vy = -vy;
			} else if (ball.getY() >= collider.getY() + BRICK_HEIGHT + vy) {
				remove(collider);
				vy = -vy;
			} else {
				remove(collider);
				vx = -vx;
			}
			bounceClip.play();
			rmv_brck++; // plus one more removed bricks
		}
		extension();
	}

	// this extension makes game more interesting
	private void extension() {
		accleration(); // makes the ball faster
		background(); // this add night mode in the game
		pointing(); // this adds points
		level();
		lab_lvl.setLabel("level: " + lvl);// extension
		label.setLabel("score: " + point);// extension
	}

	private void accleration() {
		if (DELAY > 5) {// this helps our ball not to be too fast
			if (count == 15) {
				DELAY = DELAY - 1; // this makes game faster
				count = 0;
			}
		}
	}

	/**
	 * colores which come in the game after 50 removed brick, this makes the game's
	 * visual better
	 */
	private void background() {
		if (rmv_brck > 90) {
			paddle.setColor(Color.RED);
			ball.setColor(Color.RED);
			setBackground(Color.BLACK);
		} else if (rmv_brck > 75) {
			paddle.setColor(Color.GREEN);
			ball.setColor(Color.GREEN);
			setBackground(Color.DARK_GRAY);
		} else if (rmv_brck > 50) {
			paddle.setColor(Color.CYAN);
			ball.setColor(Color.CYAN);
			setBackground(Color.LIGHT_GRAY);
		}
	}

	/** this function shows us in the end, how many point we get */
	private GLabel label;

	private void label() {
		label = new GLabel("score: " + point);
		double labelx = WIDTH / 2 - label.getWidth(); // x coordinate of label
		double labely = getHeight() - PADDLE_Y_OFFSET + 2 * label.getAscent();// y coordinate of label
		label.setFont(" Times new Roman -20");
		label.setColor(Color.RED);
		add(label, labelx, labely);
	}

	private int point = 0;// adds points

	/** in this function there is shown how we get points */
	private void pointing() {
		double brick_point = 2 * (BRICK_HEIGHT + BRICK_SEP);
		if (collider != null && collider != lab_lvl) {
			if (collider.getY() <= BRICK_Y_OFFSET + brick_point) {
				point += 10;
			} else if (collider.getY() < BRICK_Y_OFFSET + 2 * brick_point) {
				point += 8;
			} else if (collider.getY() < BRICK_Y_OFFSET + 3 * brick_point) {
				point += 6;
			} else if (collider.getY() < BRICK_Y_OFFSET + 4 * brick_point) {
				point += 4;
			} else if (collider.getY() < BRICK_Y_OFFSET + 5 * brick_point) {
				point += 2;
			}
		}
	}

	private int lvl = 1;

	// it will be more entertaining if there will be levels in beakout
	private void level() {
		if (rmv_brck > 90) {
			lvl = 5;
		} else if (rmv_brck > 70) {
			lvl = 4;
		} else if (rmv_brck > 50) {
			lvl = 3;
		} else if (rmv_brck > 25) {
			lvl = 2;
		}
	}

	private GLabel lab_lvl;

	private void lab_lvl() {
		lab_lvl = new GLabel("");
		lab_lvl.setFont("-20");
		lab_lvl.setColor(Color.CYAN);
		add(lab_lvl, 0, 30);
	}

	/** we need ending, otherwise game will be boring */

	private void square(Color color) {
		int length = 200;
		int height = 100;
		GRect rect = new GRect(length, height);
		rect.setFilled(true);
		rect.setColor(color);
		add(rect, (getWidth() - length) / 2, (getHeight() - height) / 2);
	}

	/** this function tell us that we won */

	private void winning() {
		square(Color.ORANGE);
		GLabel win = new GLabel("YOU WIN !!!");
		win.setFont("-30");
		win.setColor(Color.WHITE);
		add(win, (getWidth() - win.getWidth()) / 2, getHeight() / 2);
	}

	/** this function tells us that we lost */
	private void losing() {
		square(Color.BLUE);
		GLabel lose = new GLabel("YOU LOSE");
		lose.setFont("-30");
		lose.setColor(Color.WHITE);
		add(lose, (getWidth() - lose.getWidth()) / 2, getHeight() / 2);
	}

	/** this method helps us to paint circles quickly */

	private GOval circle(Color color, double rad) {
		GOval oval = new GOval(rad, rad);
		oval.setFilled(true);
		oval.setFillColor(color);
		return oval;
	}

	/**
	 * In the next lines there is constant variables to create some figures in
	 * ending
	 */
	private static final double SM_RAD = 100;// radius of smile
	private static final double SMILE_X = (WIDTH - SM_RAD) / 2;// x coordinate of smile
	private static final double SMILE_Y = (HEIGHT + SM_RAD) / 2;// y coordinate of smile

	/** painting smile */
	private void smile() {
		double mouth_rad = 30;// mouth radius
		double eye_rad = SM_RAD / 4;// radius of eye
		/** these lines are created for smile eyes */
		double linex1 = WIDTH / 2 - mouth_rad;
		double linex2 = WIDTH / 2 - 0.2 * mouth_rad;
		double line_length = linex2 - linex1;// length of the line
		double liney = SMILE_Y + mouth_rad * 1.2; // y coordinate for lines

		if (rmv_brck == 100) {
			/** smile's face */
			GOval smile = circle(Color.YELLOW, SM_RAD);
			add(smile, SMILE_X, SMILE_Y);

			/** painting mouth */
			GArc ar1 = new GArc(2 * mouth_rad, 2 * mouth_rad, 180, 180);
			ar1.setFilled(true);
			ar1.setFillColor(Color.WHITE);
			add(ar1, getWidth() / 2 - mouth_rad, SMILE_Y + mouth_rad);

			/** painting first eye */
			GLine eye1 = new GLine(linex1, liney, linex2, liney);
			add(eye1);

			/** painting second eye */
			GOval eye2 = circle(Color.WHITE, eye_rad);
			add(eye2, linex1 + eye_rad * 1.4, liney - eye_rad / 2);
		} else {
			/** painting sad smile face */
			GOval sad = circle(Color.YELLOW, SM_RAD);
			add(sad, SMILE_X, SMILE_Y);

			/** painting first eye */
			GLine eye1 = new GLine(linex1, liney, linex2, liney);
			add(eye1);

			/** painting second eye */
			double off_centr = WIDTH / 2 - linex2; // offset from center
			double startx = WIDTH / 2 + off_centr; // start x coordinate
			GLine eye2 = new GLine(startx, liney, startx + line_length, liney);
			add(eye2);

			/** painting mouth */
			double mouth_st_x = WIDTH / 2 - mouth_rad;// start x coordinate of mouth
			double mouth_lst_x = WIDTH / 2 + mouth_rad;// last x coordinate of mouth
			double off_bottom = SM_RAD / 3;// offset from the bottom of smile's face
			double mouth_height = SMILE_Y + SM_RAD - off_bottom;// y coordinate
			GLine mouth = new GLine(mouth_st_x, mouth_height, mouth_lst_x, mouth_height);
			add(mouth);
		}
	}
}
