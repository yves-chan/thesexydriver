package com.costbear.android.thesexydriver;

import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ProfileActivity extends ActionBarActivity {

    Button done;
    EditText year, make, model;

    CSVFile csvFile;
    InputStream inputStream;
    List<Car> carList;

    ListView listView;

    ItemArrayAdapter itemArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        done = (Button) findViewById(R.id.done);
        inputStream = getResources().openRawResource(R.raw.cardatabase2000to2014);
        csvFile = new CSVFile(inputStream);

        carList = csvFile.read();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = (EditText) findViewById(R.id.yearField);
                make = (EditText) findViewById(R.id.makeField);
                //model = (EditText) findViewById(R.id.modelField);

                String yearEntered = year.getText().toString();
                String makeEntered = make.getText().toString();
                //String modelEntered = model.getText().toString();

                List<Car> filteredCarList = new ArrayList<Car>();

                for (Car c : carList) {
                    if (yearEntered.equals(String.valueOf(c.getYear()))) {
                        filteredCarList.add(c);
                    }
                }

                List<Car> filteredByMake = new ArrayList<Car>();

                for (Car c : filteredCarList) {
                    if (makeEntered.equalsIgnoreCase(c.getMake())) {
                        filteredByMake.add(c);
                    }
                }

                List<String> carModelsList = new ArrayList<String>();

                for (Car c : filteredCarList) {
                    String model;
                    model = c.getModel();

                    carModelsList.add(model);

                }

                listView = (ListView) findViewById(R.id.listview);
                itemArrayAdapter = new ItemArrayAdapter(getApplicationContext(), R.layout.row_models);

                Parcelable state = listView.onSaveInstanceState();
                listView.setAdapter(itemArrayAdapter);
                listView.onRestoreInstanceState(state);

                for (Car c : filteredByMake) {
                    itemArrayAdapter.add(c);
                }

                //itemArrayAdapter = new ItemArrayAdapter(this, carModelsList);


            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
