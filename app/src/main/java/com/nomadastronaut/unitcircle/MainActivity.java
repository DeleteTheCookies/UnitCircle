package com.nomadastronaut.unitcircle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.trigSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.trigSpinner_array, R.layout.spinner_layout);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        Spinner spinner2 = (Spinner) findViewById(R.id.unitSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.unitSpinner_array, R.layout.spinner_layout);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner2.setAdapter(adapter2);
    }

    public void submitAngle(View view)
    {
        Circle circle = (Circle) this.findViewById(R.id.Circle);
        EditText editAngle = (EditText) this.findViewById(R.id.editAngle);
        Spinner trigSpinner = (Spinner) this.findViewById(R.id.trigSpinner);
        Spinner unitSpinner = (Spinner) this.findViewById(R.id.unitSpinner);

        String trigFunction = trigSpinner.getSelectedItem().toString();
        String unit = unitSpinner.getSelectedItem().toString();

        Log.d("trigFunction", "submitAngle: " + trigFunction);
        Log.d("unit", "submitAngle: " + unit);

        if(editAngle.getText().toString().equals("")){
            circle.setAngle(45);
        }
        else{
            circle.setAngle(Float.parseFloat(editAngle.getText().toString()));
        }

        float result = circle.measureTrig(trigFunction, unit);
        Log.d("After measureTrig:", "" + result);
        String result_ToPrint = String.format(java.util.Locale.US,"%.4f", result);

        TextView resultDec = (TextView) this.findViewById(R.id.resultDec);
        resultDec.setText(result_ToPrint);

        TextView resultFrac = (TextView) this.findViewById(R.id.resultFraction);
        resultFrac.setText(":)");
    }
}
