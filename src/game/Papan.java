/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author PenunggangKuda
 */
public class Papan extends JPanel implements ActionListener {

    Image tail, umpan;
    int ju = 3, umpan_x, umpan_y, img = 1;
    static boolean kanan = true;
    static boolean kiri = false;
    static boolean atas = false;
    static boolean bawah = false;
    boolean mulai = true;
    LinkedList<Image> ll = new LinkedList<>();
    int uk = 10;
    int x[] = new int[300];
    int y[] = new int[300];
    Timer timer;

    public Papan() {
        addKeyListener(new Keyboard());
        setBackground(Color.white);
        setFocusable(true);
        setPreferredSize(new Dimension(300, 400));
        gmbr();
        game();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (mulai) {
            makanumpan();
            pembatas();
            arah();
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        skor(g);
        g.drawImage(umpan, umpan_x, umpan_y, this);
        buatular(g);
    }

    private void skor(Graphics g) {
        String msg = "Skor : " + String.valueOf(ll.size());
        Font small = new Font("Helvetica", Font.BOLD, 14);
        g.setColor(Color.black);
        g.setFont(small);
        g.drawString(msg, 1, 10);
    }

    private void gmbr() {
        ImageIcon ihead = new ImageIcon("src/resources/head.png");
        ll.addFirst(ihead.getImage());
        ImageIcon itail = new ImageIcon("src/resources/dot.png");
        tail = itail.getImage();
        ll.addLast(tail);
        ImageIcon iumpan = new ImageIcon("src/resources/apple" + img + ".png");
        umpan = iumpan.getImage();
        buatumpan();
    }

    private void game() {
        timer = new Timer(140, this);
        timer.start();
    }

    public void acakumpan() {
        int r = (int) (Math.random() * (this.getWidth() / uk));
        umpan_x = ((r * uk));
        r = (int) (Math.random() * (this.getHeight() / uk));
        umpan_y = ((r * uk));
    }

    private void buatular(Graphics g) {
        if (mulai) {
            for (int a = 0; a < ll.size(); a++) {
                if (a == 0) {
                    g.drawImage(ll.getFirst(), x[a], y[a], this);
                } else {
                    g.drawImage(ll.getLast(), x[a], y[a], this);
                }
            }
        } else {
            gameover(g);
        }
    }

    private void gameover(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);
        g.setColor(Color.black);
        g.setFont(small);
        g.drawString(msg, (this.getWidth() - metr.stringWidth(msg)) / 2, this.getHeight() / 2);
    }

    public void buatumpan() {
        Timer timeer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(!ll.isEmpty()){
                    Random rand = new Random();
                    img = rand.nextInt(2) + 1;
                    ImageIcon iumpan = new ImageIcon("src/resources/apple" + img + ".png");
                    umpan = iumpan.getImage();
                    acakumpan();
                }
            }
        });
        timeer.start();
    }
    
    public void nyawa(){
        Timer timeer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(!ll.isEmpty()){
                    ll.removeLast();
                }
            }
        });
        timeer.start();
    }

    private void makanumpan() {
        if (x[0] == umpan_x && y[0] == umpan_y) {
            if (img == 1) {
                ll.addLast(tail);
            } else if (img == 2) {
                ll.removeLast();
            }
            nyawa();
            acakumpan();
        }
        if(ll.isEmpty()){
            mulai=false;
        }
    }

    private void arah() {
        for (int a = ll.size(); a > 0; a--) {
            x[a] = x[(a - 1)];
            y[a] = y[(a - 1)];
        }
        if (kiri) {
            x[0] -= uk;
        } else if (kanan) {
            x[0] += uk;
        } else if (atas) {
            y[0] -= uk;
        } else if (bawah) {
            y[0] += uk;
        }
    }

    private void pembatas() {
        if (x[0] >= this.getWidth() || x[0] < 0) {
            mulai = false;
        } else if (y[0] >= this.getHeight() || y[0] < 0) {
            mulai = false;
        }
    }

    private static class Keyboard implements KeyListener {

        @Override
        public void keyTyped(KeyEvent ke) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void keyPressed(KeyEvent ke) {
            int key = ke.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!kanan)) {
                kiri = true;
                atas = false;
                bawah = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!kiri)) {
                kanan = true;
                atas = false;
                bawah = false;
            }

            if ((key == KeyEvent.VK_UP) && (!bawah)) {
                atas = true;
                kanan = false;
                kiri = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!atas)) {
                bawah = true;
                kanan = false;
                kiri = false;
            }
        }

        @Override
        public void keyReleased(KeyEvent ke) {
            //To change body of generated methods, choose Tools | Templates.
        }
    }

}
