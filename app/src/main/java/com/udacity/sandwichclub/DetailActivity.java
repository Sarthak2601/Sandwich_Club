package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
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
    TextView description;
    Sandwich sandwich;
    LinearLayout knownAsLL;
    LinearLayout ingredientsLL;
    TextView placeOfOriginHeading;
    TextView alsoKnownAsHeading;
    TextView ingredientsHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        placeOfOrigin = findViewById(R.id.origin_tv);
        description = findViewById(R.id.description_tv);
        knownAsLL = findViewById(R.id.linearLayoutKnownAs);
        ingredientsLL = findViewById(R.id.linearLayoutIngredients);
        placeOfOriginHeading = findViewById(R.id.placeOfOrigin);
        alsoKnownAsHeading = findViewById(R.id.alsoKnownAs);
        ingredientsHeading = findViewById(R.id.ingredients);

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
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        if(!sandwich.getPlaceOfOrigin().isEmpty()){
            placeOfOrigin.setText(sandwich.getPlaceOfOrigin());
        }
        else{
            placeOfOriginHeading.setVisibility(View.GONE);
            placeOfOrigin.setVisibility(View.GONE);
        }

        description.setText(sandwich.getDescription());
        List<String> alsoKnown = sandwich.getAlsoKnownAs();
        populateLists(alsoKnown,knownAsLL,alsoKnownAsHeading);
        List<String> ingredientsList = sandwich.getIngredients();
        populateLists(ingredientsList,ingredientsLL,ingredientsHeading);

    }

    private void newTextviews(LinearLayout linearLayout, String string){
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        TextView textView = new TextView(this);
        textView.setLayoutParams(layoutParams);
        textView.setText("\u2022 "+string);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setTextColor(getResources().getColor(android.R.color.white));
        linearLayout.addView(textView);


    }

    private void populateLists(List<String> list,LinearLayout linearLayout, TextView textView){
        if(!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                String value =  list.get(i);
                newTextviews(linearLayout,value);
            }
        }
        else{
            textView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.GONE);
        }
    }
}
