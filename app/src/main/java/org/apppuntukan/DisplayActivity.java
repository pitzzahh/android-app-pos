package org.apppuntukan;

import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import org.apppuntukan.model.abstractions.NoActionBarActivity;

public class DisplayActivity extends NoActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
        setContentView(R.layout.activity_display);
    }

    // Add Fragments to ViewPager
    void addPages(){
        // Add Fragments to ViewPager
        ViewPager viewPager = findViewById(R.id.pager);
    }
}

