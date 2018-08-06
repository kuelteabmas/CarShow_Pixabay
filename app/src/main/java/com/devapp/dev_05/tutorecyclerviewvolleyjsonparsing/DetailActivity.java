package com.devapp.dev_05.tutorecyclerviewvolleyjsonparsing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import static com.devapp.dev_05.tutorecyclerviewvolleyjsonparsing.MainActivity.EXTRA_CREATOR;
import static com.devapp.dev_05.tutorecyclerviewvolleyjsonparsing.MainActivity.EXTRA_LIKES;
import static com.devapp.dev_05.tutorecyclerviewvolleyjsonparsing.MainActivity.EXTRA_URL;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Define variables
        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String creatorName = intent.getStringExtra(EXTRA_CREATOR);
        int likeCount = intent.getIntExtra(EXTRA_LIKES, 0);

        // Define the different views for this activity
        ImageView imageView = findViewById(R.id.image_view_detail);
        TextView textViewCreator = findViewById(R.id.text_view_creator_detail);
        TextView textViewLikes = findViewById(R.id.text_view_like_detail);

        // Fetch the appropriate image and set the image and display in the ImageView
        Picasso.get()
                .load(imageUrl)
                .fit()
                .centerInside()
                .into(imageView);

        // Fetch the appropriate Creator Name and Like Count and display in the TextViews
        textViewCreator.setText(creatorName);
        textViewLikes.setText("Likes: " + likeCount);
    }


}
