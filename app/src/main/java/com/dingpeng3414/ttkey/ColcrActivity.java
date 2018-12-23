package com.dingpeng3414.ttkey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;

public class ColcrActivity extends AppCompatActivity {
    TextView item1, item2;
    ColorPicker picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colcr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("主题选择");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {
        item1 = (TextView) findViewById(R.id.item1);
        item2 = (TextView) findViewById(R.id.item2);

        picker = (ColorPicker) findViewById(R.id.picker);

        SVBar svBar = (SVBar) findViewById(R.id.svbar);
        OpacityBar opacityBar = (OpacityBar) findViewById(R.id.opacitybar);
        SaturationBar saturationBar = (SaturationBar) findViewById(R.id.saturationbar);
        ValueBar valueBar = (ValueBar) findViewById(R.id.valuebar);

        picker.addSVBar(svBar);
        picker.addOpacityBar(opacityBar);
        picker.addSaturationBar(saturationBar);
        picker.addValueBar(valueBar);

        picker.getColor();

//        picker.setOldCenterColor(picker.getColor());

        picker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                item1.setTextColor(picker.getColor());
                item2.setTextColor(picker.getColor());
                showToast(String.valueOf(picker.getColor()));
            }
        });

//        picker.setShowOldCenterColor(false);

//        opacityBar.setOnOpacityChangedListener(new OpacityBar.OnOpacityChangedListener() {
//            @Override
//            public void onOpacityChanged(int opacity) {
//                showToast("opacityBar");
//            }
//        });
//        valueBar.setOnValueChangedListener(new ValueBar.OnValueChangedListener() {
//            @Override
//            public void onValueChanged(int value) {
//                showToast("valueBar");
//            }
//        });
//        saturationBar.setOnSaturationChangedListener(new SaturationBar.OnSaturationChangedListener() {
//            @Override
//            public void onSaturationChanged(int saturation) {
//                showToast("saturationBar");
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_color_menu, menu);
        return true;
    }

    private void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                resultIntent(picker.getColor());
                this.finish();
                break;
            case R.id.action_save:
                resultIntent(picker.getColor());
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void resultIntent(int color) {
        Intent intent = new Intent();
        intent.putExtra("color", String.valueOf(color));
        setResult(2, intent);
    }
}
