package com.example.gaurav.gpstracker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class CustomView extends View implements Observer {
    private List<Pair<Location, Long>> points = new ArrayList<>();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int metric;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomView, 0, 0);

        try {
            metric = a.getInteger(R.styleable.CustomView_metric, 0);
        } finally {
            a.recycle();
        }
    }

    protected Double computeMean(List<Double> values) {
        Double mean = 0.0;
        for (Double value : values) {
            mean += value;
        }
        return mean / values.size();
    }

    protected Double computeMax(List<Double> values) {
        Double max = 0.0;
        for (Double value : values) {
            if (max < value) {
                max = value;
            }
        }
        return max;
    }

    protected Pair<List<Double>, List<Double>> computeStats() {
        List<Double> distanceGroups = new ArrayList<>();
        List<Double> timeGroups = new ArrayList<>();
        Pair<Location, Long> lastPoint = points.get(0);

        Double distance = 0.0;
        Double time = 0.0;

        for (Pair<Location, Long> point : points.subList(1, points.size())) {
            distance += point.first.distanceTo(lastPoint.first);
            time += point.second - lastPoint.second;

            if (distance >= 100) {
                distanceGroups.add(distance / time * 60.0 * 60.0);
                distance = 0.0;

                timeGroups.add(time / 1000.0);
                time = 0.0;
            }

            lastPoint = point;
        }

        return new Pair<>(distanceGroups, timeGroups);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Double height = Double.valueOf(canvas.getHeight());
        Double width = Double.valueOf(canvas.getWidth());

        height -= 40;

        if (points.size() > 1) {
            Pair<List<Double>, List<Double>> stats = computeStats();
            List<Double> values = null;

            if (metric == 0) {
                values = stats.first;
            } else {
                values = stats.second;
            }

            Double max = computeMax(values);
            Double mean = computeMean(values);

            int i = 0;
            float barWidth = (float) (width / values.size());

            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(8);
            canvas.drawLine(0, (float)(height * 1), (float)(width * 1), (float)(height * 1), paint);

            for (Double value : values) {
                paint.setColor(Color.MAGENTA);
                canvas.drawRect(i * barWidth,
                        (float) (height - height * (float) ((value / max))),
                        (i + 1) * barWidth,
                        (float) (height * 1),
                        paint);

                paint.setColor(Color.BLACK);
                paint.setTextSize(24);

                canvas.drawText((i + 1) * 100 + "m", i * barWidth + barWidth / 2, (float) (height + 30), paint);

                paint.setColor(Color.BLUE);
                paint.setStrokeWidth(8);
                canvas.drawLine(i * barWidth + barWidth / 2, (float)(height * 1), (float)(i * barWidth + barWidth / 2), (float)(height + 10), paint);

                i++;
            }

            if (values.size() > 1) {
                paint.setStrokeWidth(8);
                paint.setColor(Color.GREEN);
                canvas.drawLine(0,
                        (float) (height - height * (mean / max)),
                        Float.valueOf(String.valueOf(width)),
                        (float) (height - height * (mean / max)),
                        paint);
            }

            paint.setColor(Color.BLACK);
            paint.setTextSize(24);

            canvas.drawText(String.format("avg speed %1$.2f km/h", computeMean(stats.first)), 0, 20, paint);
            canvas.drawText(String.format("avg time: %1$.2f s", computeMean(stats.second)), 0,40 , paint);
        }

        super.onDraw(canvas);
    }

    public void setMetric(int metric) {
        this.metric = metric;
        invalidate();
    }

    public void reset() {
        points = new ArrayList<>();
        invalidate();
    }

    @Override
    public void update(Observable o, Object arg) {
        points.add((Pair<Location, Long>) arg);
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        invalidate();
    }
}
