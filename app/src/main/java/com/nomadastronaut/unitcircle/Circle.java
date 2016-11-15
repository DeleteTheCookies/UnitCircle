package com.nomadastronaut.unitcircle;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class Circle extends View {
    // TODO: Set scope of class attributes in Circle.java
    private boolean firstRun = true;
    public float angle = 45;
    public float x;
    public float y;
    private float originX;
    private float originY;
    boolean showGuides;
    int strokeColor_int;
    int pointerColor_int;
    int guidesColor_int;
    float circleStrokeWidth;
    float circleRadius;
    float pointerStrokeWidth;
    float pointerLength;
    Paint strokeColor;
    Paint pointerColor;
    Paint guidesColor;

    public Circle(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Get the attributes specified in attrs.xml
        // These are set in activity_main.xml
        // More info: https://developer.android.com/training/custom-views/create-view.html
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Circle, 0, 0);

        try {
            showGuides = a.getBoolean(R.styleable.Circle_showGuides, false);
            // Circle
            circleStrokeWidth = a.getFloat(R.styleable.Circle_circleStrokeWidth, 15);
            circleRadius = a.getFloat(R.styleable.Circle_circleRadius, 300);
            strokeColor_int = a.getColor(R.styleable.Circle_circleStrokeColor, ContextCompat.getColor(context, R.color.colorCircleStroke));
            // Guides
            guidesColor_int = a.getColor(R.styleable.Circle_guidesColor, ContextCompat.getColor(context, R.color.colorGuides));
            // Pointer
            pointerStrokeWidth = a.getFloat(R.styleable.Circle_pointerStrokeWidth, 15);
            pointerLength = a.getFloat(R.styleable.Circle_pointerLength, 300);
            pointerColor_int = a.getColor(R.styleable.Circle_pointerStrokeColor, ContextCompat.getColor(context, R.color.colorPointerStroke));
        }
        finally {
            a.recycle();
        }

        init(); // Private method belonging to this class
    }

    /**
     * Getters and Setters
     */

    public void setAngle(float angle)
    {
        firstRun = false;
        this.angle = angle;
        calculateX();
        calculateY();

        invalidate();
        requestLayout();
    }

    public boolean isShowGuides() {
        return isShowGuides();
    }
    public void setShowGuides(boolean showGuides) {
        this.showGuides = showGuides;
        invalidate();
        requestLayout();
    }
    public float getCircleRadius(){
        return circleRadius;
    }
    public void setCircleRadius(float circleRadius){
        this.circleRadius = circleRadius;
        invalidate();
        requestLayout();
    }
    public float getPointerLength(){
        return pointerLength;
    }
    public void setPointerLength(float pointerLength){
        this.pointerLength = pointerLength;
        invalidate();
        requestLayout();
    }

    /**
     *  Class methods
     */

    private void init(){
        // Initialize Android Paint elements: strokeColor and pointerColor
        strokeColor = new Paint();
        strokeColor.setColor(strokeColor_int);
        strokeColor.setStyle(Paint.Style.STROKE);
        strokeColor.setStrokeWidth(circleStrokeWidth);

        guidesColor = new Paint();
        guidesColor.setColor(guidesColor_int);
        guidesColor.setStyle(Paint.Style.STROKE);
        guidesColor.setStrokeWidth(pointerStrokeWidth);

        pointerColor = new Paint();
        pointerColor.setColor(pointerColor_int);
        pointerColor.setStyle(Paint.Style.STROKE);
        pointerColor.setStrokeWidth(pointerStrokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        // TODO: override onSizeChanged()

        if(firstRun == true) {
            // View's center point:
            originX = getWidth()/2;
            originY = getHeight()/2;

            Log.d("originX", String.valueOf(originX));
            Log.d("originY", String.valueOf(originY));
            Log.d("circleRadius", String.valueOf(circleRadius));

            circleRadius = (float) (originX/1.5); // Temp fix for different screens *Portrait only
            Log.d("circleRadius [PORTRAIT]", String.valueOf(circleRadius));

            calculateX();
            calculateY();

            // Draw the Circle
            canvas.drawCircle(originX, originY, circleRadius, strokeColor);
            // Draw the guides
            canvas.drawLine(originX - circleRadius - circleStrokeWidth, originY, originX + circleRadius + circleStrokeWidth, originY, guidesColor); // X
            canvas.drawLine(originX, originY - circleRadius - circleStrokeWidth, originX, originY + circleRadius + circleStrokeWidth, guidesColor); // Y
            // Draw the pointer
            canvas.drawLine(originX, originY, originX + x, originY - y, pointerColor);
        }
        else {
            // Draw the Circle
            canvas.drawCircle(originX, originY, circleRadius, strokeColor);
            // Draw the guides
            canvas.drawLine(originX - circleRadius - circleStrokeWidth, originY, originX + circleRadius + circleStrokeWidth, originY, guidesColor); // X
            canvas.drawLine(originX, originY - circleRadius - circleStrokeWidth, originX, originY + circleRadius + circleStrokeWidth, guidesColor); // Y
            // Draw the pointer
            canvas.drawLine(originX, originY, originX + x, originY - y, pointerColor);
        }
    }

    public void calculateX()
    {
        this.x = (float) Math.cos(Math.toRadians(angle)) * (circleRadius + circleStrokeWidth/2);
    }

    public void calculateY()
    {
        this.y = (float) Math.sin(Math.toRadians(angle)) * (circleRadius + circleStrokeWidth/2);
    }

    public float measureTrig(String trigFunction, String unit)
    {
        float radAngle = angle;
        Log.d("measureTrig:", "radAngle: " + angle);
        Log.d("measureTrig:", "unit: " + unit);
        unit = unit.toLowerCase();
        Log.d("measureTrig:", "unit: " + unit);

        if(unit.equals("deg")){
            radAngle = (float) Math.toRadians(radAngle);
            Log.d("Deg to Rad", "measureTrig: Converted to Radians from " + unit);
        }

        Log.d("measureTrig:", "radAngle: " + angle);

        float x_calc = (float) Math.cos(radAngle);
        float y_calc = (float) Math.sin(radAngle);
        float result = 0;

        trigFunction = trigFunction.toLowerCase();

        switch(trigFunction)
        {
            case "sine":
                result = y_calc;
                Log.d("yay", "yay");
                break;
            case "cosine":
                result = x_calc;
                break;
            case "tangent":
                result = (y_calc/x_calc);
                break;
            case "cosecant":
                result = (1/y_calc);
                break;
            case "secant":
                result = (1/x_calc);
                break;
            case "cotangent":
                result = (x_calc/y_calc);
                break;
            default:
                result = 0;
                break;
        }
        return result;
    }

    public float convertToRad(float degAngle)
    {
        float convertedAngle = degAngle * ((float) (Math.PI / 180));

        return(convertedAngle);
    }
}
