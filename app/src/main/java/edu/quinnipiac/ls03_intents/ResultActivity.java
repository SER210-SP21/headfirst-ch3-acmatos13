package edu.quinnipiac.ls03_intents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String value = getIntent().getStringExtra("inputKey");
        TextView returnVal = (TextView) findViewById(R.id.displayintentextra);
        returnVal.setText(value);
    }

    @Override
    public void finish() {
        EditText returnVal = (EditText) findViewById(R.id.returnValue);
        Intent data = new Intent (this, MainActivity.class);
        data.putExtra("returnKey", returnVal.getText().toString());

        setResult(RESULT_OK, data);

        super.finish();
    }
}