package com.dm.tictactoe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class TicTacToe extends View {

    private Paint boardPaint;
    private Paint xPaint;
    private Paint oPaint;

    private RectF[][] cells;
    private byte[][] cellData;

    private boolean xTurn = true;

    public TicTacToe(Context context) {
        super(context);
        init();
    }

    public TicTacToe(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TicTacToe(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        boardPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        boardPaint.setColor(Color.BLACK);
        boardPaint.setStrokeWidth(5);

        xPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        xPaint.setColor(Color.RED);
        xPaint.setStrokeWidth(4);
        oPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        oPaint.setColor(Color.BLUE);
        oPaint.setStrokeWidth(4);
        oPaint.setStyle(Paint.Style.STROKE);

        cells = new RectF[3][3];
        cellData = new byte[3][3];
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w > 0 && h > 0) {
            for (int i = 0; i<3; i++) {
                float left = i * w /3f;
                float right = left + w /3f;
                for (int j = 0; j<3; j++) {
                    float top = j * h /3f;
                    float bottom = top + h / 3f;
                    cells[i][j] = new RectF(left, top, right, bottom);
                }
            }
        }
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBoard(canvas);
        for (int i = 0; i<3; i++) {
            for (int j = 0; j < 3; j++) {
                drawCell(canvas, cells[i][j], cellData[i][j]);
            }
        }
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            for (int i = 0; i<3; i++) {
                for (int j = 0; j<3; j++) {
                    if (cells[i][j].contains(event.getX(), event.getY())) {
                        if (cellData[i][j] == 0) {
                            if (xTurn) {
                                cellData[i][j] = -1;
                            } else {
                                cellData[i][j] = 1;
                            }
                            xTurn = !xTurn;
                            invalidate();
                        } else {
                            Toast.makeText(getContext(), "NO", Toast.LENGTH_SHORT).show();
                        }

                        // win logic
                        
                    }
                }
            }
        }

        return true;
    }

    private void drawBoard(Canvas canvas) {
        float startX, endX;
        float startY, endY;

        startY = 0;
        endY = getHeight();
        startX = getWidth() / 3f;
        endX = startX;
        canvas.drawLine(startX, startY, endX, endY, boardPaint);

        startX *= 2;
        endX = startX;
        canvas.drawLine(startX, startY, endX, endY, boardPaint);

        startX = 0;
        endX = getWidth();
        startY = getHeight() / 3f;
        endY = startY;
        canvas.drawLine(startX, startY, endX, endY, boardPaint);

        startY *= 2;
        endY = startY;
        canvas.drawLine(startX, startY, endX, endY, boardPaint);
    }

    private void drawCell(Canvas canvas, RectF rectF, int cellData) {
        switch (cellData) {
            case -1:
                drawX(canvas, rectF);
                break;
            case 1:
                drawO(canvas, rectF);
                break;
            default:
                break;
        }
    }

    private void drawX(Canvas canvas, RectF rectF) {

        canvas.drawLine(rectF.left, rectF.top, rectF.right, rectF.bottom, xPaint);
        canvas.drawLine(rectF.left, rectF.bottom, rectF.right, rectF.top, xPaint);
    }

    private void drawO(Canvas canvas, RectF rectF) {

        canvas.drawCircle(rectF.centerX(), rectF.centerY(), rectF.width() /2f, oPaint);
    }
}
