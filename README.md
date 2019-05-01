# bricksnballs-talal1999
bricksnballs-talal1999 created by GitHub Classroom

BallCrusher	
Rules:

You get 3 lives.
There are 5 Obstacles.
You lose a life once ball vanishes from the bottom of the screen.
You lose the game once all lives are lost.
You win the game if all obstacles vanish
Each time a ball hits the obstacle, obstacle Hit Count decreases by 1. It vanishes once that count reaches 0.

Note:

 Some of the stuff is hardcoded. These are pretty easy fixes that we can use to determine the difficulty i.e. obstacle Hitcount, number of lives you get, ball speed. This is not optimal, however given the constraint of time I choose to avoid the minor aesthetics.

Lastly, you might notice that Bat has same code as Obstacle. I chose not use Inheritance as I would argue that their behaviors are different enough. One Vanishes other does not, one moves whilst other is fixed.  

I am not limiting the number of balls because juggling balls is more fun. Also, the logic of the game shall not allow you to have unlimited balls because you will run out of  lives probably. Theoretically, you could, practically impossible. 

There is one level of the game yet. Obstacles are regenerated once you restart the game.  

