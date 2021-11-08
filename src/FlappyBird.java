import processing.core.PApplet; // downloaded processing from processing.org
import processing.core.PImage;
// imported this through File, Project Structure, Libraries, added core.jar from processing library

public class FlappyBird extends PApplet{
    PImage backpic, birdpic, wallpic, welcomescreen;
    int game, score, highscore, x, y, vertical, wallx[] = new int[2], wally[] = new int[2];

    public static void main(String[] args) {
        FlappyBird pt = new FlappyBird();
        PApplet.runSketch(new String[]{"FlappyBird"}, pt);
    }

    public void setup() {
        backpic = loadImage("https://raw.githubusercontent.com/junetheyi/flappybird/main/img/back.png");
        birdpic = loadImage("https://raw.githubusercontent.com/junetheyi/flappybird/main/img/bird.png");
        wallpic = loadImage("https://raw.githubusercontent.com/junetheyi/flappybird/main/img/wall.png");
        welcomescreen = loadImage("https://raw.githubusercontent.com/junetheyi/flappybird/main/img/start.png");
        game = 1; // 1 = welcome screen
        score = 0;
        highscore = 0;
        x = -200;
        vertical = 0;
//        size(600, 800);
        fill(255, 0, 0); // dictates the text color, this rgb is red so score and high score will be red
        textSize(20); // whenever we use function text(), this dictates the size (score and high score)
    }

    public void settings() {
        size(600, 800); // height of the screen, 600 across, 800 going down
    }

    public void draw() {
        if(game == 0) { // game runs when game == 0
            imageMode(CORNER); // sets the mode for background image in upper left corner
            image(backpic, x, 0);
            image(backpic, x+backpic.width, 0);
            x -= 5; //sets the speed in which the background moves
            vertical += 1; // controls how fast the bird falls,
            y += vertical; //if vertical is a value, can't control how the bird goes up...
            if (x == -1800) { // image size is 1800, this just resets the image
                x =0;
            }
            for (int i = 0; i<2; i++) { // drawing the 2 walls in a for loops
                imageMode(CENTER);
                image(wallpic, wallx[i], wally[i] - (wallpic.height/2+100)); // controls the top half (bigger # = less wall)
                image(wallpic, wallx[i], wally[i] + (wallpic.height/2+100)); // controls the bottom half (smaller # = less wall)
                if (wallx[i] < 0) { // when the wall reaches the left border, this creates new walls
                    wally[i] = (int)random( 200, height-200);
                    wallx[i] = width; // width = the right most position
                }
                if (wallx[i] == width/2) { // if the wall reaches left side, score increases by one. highscore is also saved if relevant
                    highscore = max(++score, highscore);
                }
                // collision equation, if y>height or y<0, that means the bird is not on the screen anymore
                // abs(width/2-wallx[i]) < 25 && abs(y-wally[i])>100) if the dist to x and y axis is too small, we lose.
                if (y>height || y<0 || (abs(width/2-wallx[i]) < 25 && abs(y-wally[i])>100)) {
                    game =1;
                }
                wallx[i] -=4; // change to 5 or 6 to make the game harder, determines wall movement speed
            }
            image(birdpic, width/2, y); // draws the bird
            text("Score: " +score, 10, 20); //shows the score on the top left (position 10 and 20)
        }
        else {
            imageMode(CENTER);
            image(welcomescreen, width/2, height/2);
            text("High Score: " + highscore, 50, 130);
        }
    }

    public void mousePressed() {
        vertical = -15; // whenever we press the mouse, we reduce the vertical by 15. so bird goes up by 15.
        if (game == 1) { // this is when welcome screen is displayed
            wallx[0] = 600;
            wally[0] = y = height /2;
            wallx[1] = 900;
            wally[1] = 600;
            x = game = score = 0;
        }
    }
}
