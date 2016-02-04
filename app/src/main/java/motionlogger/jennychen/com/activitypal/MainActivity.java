package motionlogger.jennychen.com.activitypal;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.text.style.UnderlineSpan;
import android.widget.ViewSwitcher;
import android.widget.ViewSwitcher.ViewFactory;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {
    private List<CharSequence> conversionOptions;
    private Map<String, Integer> conversionRatesMap = new HashMap<>();
    private SpannableString output;
    private String outputUnit = "";
    private String outputType = "";
    private int inputNumber = 0;
    private String inputType = "";
    private final static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        conversionOptions = Arrays.asList(this.getResources().getTextArray(R.array.activity_options));

        final TextView resultTextView = (TextView) findViewById(R.id.resultDisplay);
        final TextView resultUnitView = (TextView) findViewById(R.id.result_unit);

        final Spinner firstSpinner = (Spinner) findViewById(R.id.activities);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.activity_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        firstSpinner.setAdapter(adapter);

        final Spinner unitSpinner = (Spinner) findViewById(R.id.input_unit);
        ArrayAdapter<CharSequence> unit_adapter = ArrayAdapter.createFromResource(this,
                R.array.conversion_units, android.R.layout.simple_spinner_item);
        unit_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(unit_adapter);

        final Spinner resultSpinner = (Spinner) findViewById(R.id.result_type);
        ArrayAdapter<CharSequence> result_adapter = ArrayAdapter.createFromResource(this,
                R.array.activity_options, android.R.layout.simple_spinner_item);
        result_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        resultSpinner.setAdapter(result_adapter);


        CharSequence[] conversionOptions = this.getResources().getTextArray(R.array.activity_options);
        int[] conversionRates = this.getResources().getIntArray(R.array.conversion_rates);

        for(int i=0; i<conversionOptions.length; i++) {
            conversionRatesMap.put(conversionOptions[i].toString(), conversionRates[i]);
        }

        EditText inputCount = (EditText) findViewById(R.id.input);
        inputCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                if (!s.toString().equals("")){
                    inputNumber = Integer.parseInt(s.toString());
                }

            }
        });

        Button convertButton = (Button) findViewById(R.id.convert_btn);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outputType = resultSpinner.getSelectedItem().toString();
                inputType = firstSpinner.getSelectedItem().toString();
                Float ratio = inputNumber *(conversionRatesMap.get(outputType).floatValue()/conversionRatesMap.get(inputType).floatValue());
                SpannableString output = new SpannableString(DECIMAL_FORMAT.format(ratio));


                resultTextView.setText(String.valueOf(output));


                if (outputType.equals("Pushup") || outputType.equals("Situp") || outputType.equals("Squats") || outputType.equals("Pullup")) {
                    outputUnit = "reps";

                }else if (outputType.equals("Calories")) {
                    outputUnit = "calories";
                }else {
                    outputUnit = "minutes";
                }

                resultUnitView.setText(outputUnit);

            }
        });

        resultUnitView.setText(outputUnit);



    }


}
