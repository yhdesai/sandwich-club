package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String TAG = "Detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            Log.e(TAG, "intent is null1");
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            Log.e(TAG, "extra position not found");
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            Log.e(TAG, "Sandwich data no available");
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        TextView nameTextView = findViewById(R.id.nameContent);
        nameTextView.setText(sandwich.getMainName());

        TextView alsoKnownAs = findViewById(R.id.alsoKnownAsContent);
        TextView alsoKnownAsTitle = findViewById(R.id.alsoKnownAsTitle);
        if (!sandwich.getAlsoKnownAs().isEmpty()) {
            String alsoKnownAsText = TextUtils.join(", ", sandwich.getAlsoKnownAs());
            alsoKnownAs.setText(alsoKnownAsText);
        } else {
            alsoKnownAs.setVisibility(View.GONE);
            alsoKnownAsTitle.setVisibility(View.GONE);

        }

        TextView descriptionTv = findViewById(R.id.description_content);
        descriptionTv.setText(sandwich.getDescription());

        TextView originTv = findViewById(R.id.origin_tv_content);
        TextView originTVTitle = findViewById(R.id.origin_tv);
        if (!sandwich.getPlaceOfOrigin().isEmpty()) {
            String originText = sandwich.getPlaceOfOrigin();
            originTv.setText(originText);
        } else {
            originTv.setVisibility(View.GONE);
            originTVTitle.setVisibility(View.GONE);

        }

        TextView ingredientTv = findViewById(R.id.detail_ingredients_content);
        ingredientTv.setText((TextUtils.join(", ", sandwich.getIngredients())));


    }
}
