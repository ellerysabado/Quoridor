package com.example.quoridor.ui.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;

import com.example.quoridor.R;

import java.util.ArrayList;
import java.util.List;

import com.example.quoridor.data.game.game.Tile;
import com.example.quoridor.data.game.game.Wall;
import com.example.quoridor.data.game.game.Player;


/**
*
 * Custom Quoridor Board ui element.
 * @apiNote To use simply change the players, walls, and altwalls array lists and then invalidate()
 * to update the view. And, to receive tile and wall clicks, you must implement
 * the boardClick listener.
 *
 * @author Carter Awbrey
* */
public class QuoridorBoard  extends View{

    public final int tilesColor;
    public int altTilesColor;
    public final int wallsColor;
    public final int player1Color;
    public final int player2Color;
    public final int player3Color;
    public final int player4Color;

    private final Paint paint = new Paint();

    private float wallWidth = 1;
    private float tileWidth = 1;
    public List<Player> players;
    public List<Wall> walls;
    public List<Tile> altTiles;
    public BoardOnClick boardClickListener;


    public QuoridorBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.QuoridorBoard, 0, 0);

        try{
            tilesColor = a.getInteger(R.styleable.QuoridorBoard_tilesColor, Color.LTGRAY);
            altTilesColor = a.getInteger(R.styleable.QuoridorBoard_altTilesColor, Color.WHITE);
            wallsColor = a.getInteger(R.styleable.QuoridorBoard_wallsColor, Color.BLACK);
            player1Color = a.getInteger(R.styleable.QuoridorBoard_player1Color, Color.RED);
            player2Color = a.getInteger(R.styleable.QuoridorBoard_player2Color, Color.BLUE);
            player3Color = a.getInteger(R.styleable.QuoridorBoard_player3Color, Color.GREEN);
            player4Color = a.getInteger(R.styleable.QuoridorBoard_player4Color, Color.YELLOW);
        }finally {
            a.recycle();
        }

        players = new ArrayList<>(4);
        walls = new ArrayList<>(10);
        altTiles = new ArrayList<>(4);
        boardClickListener = new BoardOnClick() {
            @Override
            public void clickWall(int x, int y, int dir) {

            }

            @Override
            public void clickTile(int x, int y) {

            }
        };
    }

    @Override
    protected void onMeasure(int width, int height){
        super.onMeasure(width, height);

        int dimensions = Math.min(getMeasuredWidth(), getMeasuredWidth());
        wallWidth = dimensions/26f;
        tileWidth = dimensions/13f;

        setMeasuredDimension(dimensions, dimensions);
    }

    @Override
    protected void onDraw(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawGameBoard(canvas);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        if(action == MotionEvent.ACTION_DOWN){
            if(((int) (y/(this.getWidth()/26)) + 1) % 3 == 0 || ((int) (x/(this.getWidth()/26)) + 1) % 3 == 0){
                //touched a wall

                boardClickListener.clickWall(((int) Math.ceil((x/(wallWidth+tileWidth)))) - 1,
                        ((int) Math.ceil((y/(wallWidth+tileWidth)))) - 1,
                        ((int) (y/(this.getWidth()/26)) + 1) % 3 == 0 ? 0 : 1);

//                Wall nw = new Wall();
//
//                nw.x = ((int) Math.ceil((x/(wallWidth+tileWidth)))) - 1;
//                nw.y = ((int) Math.ceil((y/(wallWidth+tileWidth)))) - 1;
//                nw.dir =  ((int) (y/(this.getWidth()/26)) + 1) % 3 == 0 ? 0 : 1;
//
//                walls.add(nw);
                invalidate();
            }else{
                boardClickListener.clickTile(((int) Math.ceil((x/(wallWidth+tileWidth)))) - 1,
                        ((int) Math.ceil((y/(wallWidth+tileWidth)))) - 1);

//                Player np = new Player();
//                np.x = ((int) Math.ceil((x/(wallWidth+tileWidth)))) - 1;
//                np.y = ((int) Math.ceil((y/(wallWidth+tileWidth)))) - 1;
//                np.num = 1;
//
//                players.add(np);
                 invalidate();
            }

        }
        return true;
    }

    private void drawGameBoard(Canvas canvas){
        //drawing regular tiles
        paint.setColor(tilesColor);
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL);
        for(int y = 0; y < 9; y++){
            for (int x = 0; x < 9; x++) {
                float top = (wallWidth*y + tileWidth*y);
                float bottom = (wallWidth*y + tileWidth*y) + tileWidth;
                float left = (wallWidth*x + tileWidth*x);
                float right = (wallWidth*x + tileWidth*x) + tileWidth;
                canvas.drawRect(left, top, right, bottom, paint);
            }
        }

        //drawing alt tiles
        paint.setColor(altTilesColor);
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL);
        for(int i = 0; i < altTiles.size(); i++){
            int x = altTiles.get(i).x;
            int y = altTiles.get(i).y;

/*            float top = (wallWidth*y + tileWidth*y);
            float bottom = (wallWidth*y + tileWidth*y) + tileWidth;
            float left = (wallWidth*x + tileWidth*x);
            float right = (wallWidth*x + tileWidth*x) + tileWidth;
            canvas.drawRect(left, top, right, bottom, paint);*/

            float centerX = (wallWidth*x + tileWidth*x) + (tileWidth/2f);
            float centerY = (wallWidth*y + tileWidth*y) + (tileWidth/2f);
            float radius = (tileWidth/2f)*0.25f;

            canvas.drawCircle(centerX, centerY, radius, paint);
        }


        //drawing all characters
        for(int i = 0; i < players.size(); i++){
            drawCharacter(canvas, players.get(i).x, players.get(i).y, players.get(i).number);
        }

        //drawing all walls
        for(int i = 0; i < walls.size(); i++){
            drawWall(canvas, walls.get(i).x, walls.get(i).y, walls.get(i).dir);
        }
    }

    private void drawWall(Canvas canvas, int x, int y, int dir){
        paint.setColor(wallsColor);
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL);

        if(dir == 1){
            float top = (wallWidth*y + tileWidth*y);
            float bottom = (wallWidth*y + tileWidth*y) + tileWidth + tileWidth + wallWidth;
            float left = (wallWidth*x + tileWidth*x) + tileWidth;
            float right = (wallWidth*x + tileWidth*x) + wallWidth + tileWidth;
            canvas.drawRect(left, top, right, bottom, paint);
        }else{
            float top = (wallWidth*y + tileWidth*y) + tileWidth;
            float bottom = (wallWidth*y + tileWidth*y) + tileWidth + wallWidth;
            float left = (wallWidth*x + tileWidth*x);
            float right = (wallWidth*x + tileWidth*x) + wallWidth + tileWidth + tileWidth;
            canvas.drawRect(left, top, right, bottom, paint);
        }
    }

    private void drawCharacter(Canvas canvas, int x, int y, int character){
        switch (character){
            case 1:
                paint.setColor(player1Color);
                break;
            case 2:
                paint.setColor(player2Color);
                break;
            case 3:
                paint.setColor(player3Color);
                break;
            default:
                paint.setColor(player4Color);
                break;
        }

        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL);

        float centerX = (wallWidth*x + tileWidth*x) + (tileWidth/2f);
        float centerY = (wallWidth*y + tileWidth*y) + (tileWidth/2f);
        float radius = (tileWidth/2f)*0.75f;

        canvas.drawCircle(centerX, centerY, radius, paint);
    }
}
