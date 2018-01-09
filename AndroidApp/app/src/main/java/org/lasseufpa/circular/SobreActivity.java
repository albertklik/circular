package org.lasseufpa.circular;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by alberto on 04/12/2017.
 */

public class SobreActivity extends AppCompatActivity
{

    private Button button;
    private ImageView alphamageimage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        button = (Button) findViewById(R.id.button2);
        alphamageimage = (ImageView) findViewById(R.id.imageView5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/lasseufpa/circular"));
                startActivity(myIntent);
            }
        });
        alphamageimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://alphamage.com"));
                startActivity(myIntent);
            }
        });

    }
}
