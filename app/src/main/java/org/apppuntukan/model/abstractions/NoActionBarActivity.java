package org.apppuntukan.model.abstractions;

import java.util.Objects;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public abstract class NoActionBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        try
        {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        }
        catch (NullPointerException ignored){}
        super.onCreate(savedInstanceState);
    }
}
