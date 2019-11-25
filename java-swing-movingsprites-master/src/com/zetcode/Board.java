package com.zetcode;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.*;

import static com.zetcode.Asteroid.asteroids;

public class Board extends JPanel implements ActionListener {

    private Image image;
    protected int width, height;
    private boolean isGameOver;
    private final int ICRAFT_X = 40;
    private final int ICRAFT_Y = 60;
    private final int DELAY = 10;
    private Timer timer;
    private SpaceShip spaceShip;



    public Board() {
        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);
        isGameOver = false;

        spaceShip = new SpaceShip(ICRAFT_X, ICRAFT_Y);
        Asteroid.setAsteroidCoordinate();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!isGameOver) {
            doDrawing(g);
        }
        else gameOverPopUp();

        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        if (spaceShip.isVisible())g2d.drawImage(spaceShip.getImage(), spaceShip.getX(),
                spaceShip.getY(), this);

        List<Missile> missiles = spaceShip.getMissiles();

        for (Missile missile : missiles) {
            if (missile.isVisible()) g2d.drawImage(missile.getImage(), missile.getX(),
                    missile.getY(), this);
        }

        for (Asteroid asteroid : asteroids) {
            if (asteroid.isVisible()) g2d.drawImage(asteroid.getImage(), asteroid.getX(),
                    asteroid.getY(), this);
        }
    }

    private void gameOverPopUp(){

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        isGameOver();
        updateMissiles();
        updateSpaceShip();
        updateAsteroid();
        isCollision();
        repaint();
    }

    private void isCollision() {
        List<Missile> missiles = spaceShip.getMissiles();

        for (Asteroid asteroid : asteroids) {
            if ((spaceShip.getX() + 50 == asteroid.getX() ) && (spaceShip.getY() + 50 > asteroid.getY() && spaceShip.getY() - 50 < asteroid.getY())) {
                spaceShip.setVisible(false);
                asteroid.setVisible(false);
                isGameOver = true;
            }
        }

        for (Missile missile : missiles) {
            for (Asteroid asteroid : asteroids) {
                if ((missile.getY() < asteroid.getY() + 54 && missile.getY() > asteroid.getY() - 50) &&( missile.getX()-20  < asteroid.getX() + 48 && missile.getX() - 39 > asteroid.getX() - 48) ) {
                    missile.setVisible(false);
                    asteroid.setVisible(false);
                    break;
                }
            }
        }
    }

    private void updateAsteroid() {
        if (asteroids.isEmpty()) {
            isGameOver = true;
            return;
        }

        for (int i = 0; i < asteroids.size(); i++){
            Asteroid asteroid = asteroids.get(i);
            if (asteroid.isVisible()) asteroid.moveAsteroid();
            else asteroids.remove(i);
        }
    }

    private void isGameOver() {
        if (isGameOver) {
            ImageIcon ii = new ImageIcon("src/resources/game-over1.jpg");
            image = ii.getImage();
            width = image.getWidth(null);
            height = image.getHeight(null);
            timer.stop();
        }

    }

    private void updateMissiles() {

        List<Missile> missiles = spaceShip.getMissiles();

        for (int i = 0; i < missiles.size(); i++) {

            Missile missile = missiles.get(i);

            if (missile.isVisible()) {

                missile.move();
            } else {

                missiles.remove(i);
            }
        }
    }

    private void updateSpaceShip() {

        spaceShip.move();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            spaceShip.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            spaceShip.keyPressed(e);
        }
    }
}