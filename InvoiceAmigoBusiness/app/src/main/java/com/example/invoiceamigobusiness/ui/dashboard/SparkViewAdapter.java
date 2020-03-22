package com.example.invoiceamigobusiness.ui.dashboard;

import com.robinhood.spark.SparkAdapter;

public class SparkViewAdapter extends SparkAdapter {
    private float[] yData;

    public SparkViewAdapter(float[] yData) {
        this.yData = yData;
    }

    @Override
    public int getCount() {
        return yData.length;
    }

    @Override
    public Object getItem(int index) {
        return yData[index];
    }

    @Override
    public float getY(int index) {
        return yData[index];
    }
}
