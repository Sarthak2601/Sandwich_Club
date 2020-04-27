package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView placeOfOrigin;
    TextView alsoKnownAs;
    TextView ingredients;
    TextView description;
    Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        placeOfOrigin = findViewById(R.id.origin_tv);
        alsoKnownAs = findViewById(R.id.also_known_tv);
        ingredients = findViewById(R.id.ingredients_tv);
        description = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        placeOfOrigin.setText(sandwich.getPlaceOfOrigin());
        description.setText(sandwich.getDescription());
//        ingredients.setText((CharSequence) sandwich.getIngredients());
        //alsoKnownAs.setText((CharSequence) sandwich.getAlsoKnownAs());

        List<String> alsoKnown = sandwich.getAlsoKnownAs();
        if(alsoKnown != null) {
            for (int i = 0; i < alsoKnown.size(); i++) {
                String value =  alsoKnown.get(i);
                alsoKnownAs.append(value+"\n");
            }
        }

        List<String> ingredientsList = sandwich.getIngredients();
        if(ingredientsList != null) {
            for (int i = 0; i < ingredientsList.size(); i++) {
                String value =  ingredientsList.get(i);
                ingredients.append(value+"\n");
            }
        }


    }
}
