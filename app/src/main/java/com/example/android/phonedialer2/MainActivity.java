package com.example.android.phonedialer2;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String[] numberStrings = {
        "1","2","3","4","5","6","7","8","9","*","0","#"
    };
    String[] labelStrings = {
        "VM","ABC","DEF","GHI","JKL","MNO","PQRS","TUV","WXYZ","","+",""
    };
    LinearLayout ll_list;
    int gridHeight;
    int height;
    int width;
    TextView numberScreen;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Display display = getWindowManager().getDefaultDisplay();
        int statusbarheight = getStatusBarHeight();
        int actionbarheight = getSupportActionBar().getHeight();
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB_MR2)
        {

            width = display.getWidth();  // deprecated
            height = display.getHeight()- (actionbarheight + statusbarheight );
        }
        else {

            Point size1 = new Point();
            display.getSize(size1);
            width = size1.x;
            height = size1.y - ( actionbarheight + statusbarheight);//visible layout height
        }
        GridView gridview = (GridView) findViewById(R.id.number_gridview);
        numberScreen = (TextView) findViewById(R.id.number_screen);
        gridview.setAdapter(new numberGridAdapter(this,numberStrings,labelStrings));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Log.i("Number Press","Position: "+position);
                String currentText = numberScreen.getText().toString()+numberStrings[position];
                numberScreen.setText(currentText);
            }

        });
    }
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public class numberGridAdapter extends BaseAdapter {

        private Context context;
        private String[] numberValues;
        private String[] labelValues;


        public numberGridAdapter(MainActivity mainActivity, String[] numberArray, String[] labelArray) {
            this.context = mainActivity;
            this.numberValues = numberArray;
            this.labelValues = labelArray;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            LinearLayout gridView;

            if (convertView == null) {

                // get layout from text_item.xml
                gridView = (LinearLayout)inflater.inflate(R.layout.number_button, null);
                Log.i("Dialer","Height: "+height);
                Log.i("Dialer","NumScreen Height: "+numberScreen.getHeight());

                gridView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, (height-numberScreen.getHeight())/5));


                //gridView.setMinimumHeight(screenHeight/9);
                // set value into textview
                TextView numTextView = (TextView) gridView.findViewById(R.id.number);
                TextView labelTextView = (TextView) gridView.findViewById(R.id.label);
                numTextView.setText(numberValues[position]);
                labelTextView.setText(labelValues[position]);

            } else {
                gridView = (LinearLayout) convertView;
            }

            return gridView;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return numberValues.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }
    }

}
