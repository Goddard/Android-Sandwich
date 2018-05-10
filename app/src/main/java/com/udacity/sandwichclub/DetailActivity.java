package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

    private TextView mDescriptionTextView;
    private TextView mPlaceOfOriginTextView;
    private TextView mPlaceOfOriginLabelTextView;
    private TextView mAlsoKnownAsTextView;
    private TextView mAlsoKnownLabelTextView;
    private TextView mIngredientsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mDescriptionTextView =   findViewById(R.id.description_tv);
        mPlaceOfOriginTextView = findViewById(R.id.origin_tv);
        mPlaceOfOriginLabelTextView = findViewById(R.id.origin_label);
        mAlsoKnownAsTextView =  findViewById(R.id.also_known_tv);
        mAlsoKnownLabelTextView = findViewById(R.id.also_known_label);
        mIngredientsTextView =  findViewById(R.id.ingredients_tv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
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
        mDescriptionTextView.setText(sandwich.getDescription());

        if (sandwich.getPlaceOfOrigin().isEmpty()){
            mPlaceOfOriginLabelTextView.setVisibility(View.GONE);
            mPlaceOfOriginTextView.setVisibility(View.GONE);
        }else {
            mPlaceOfOriginLabelTextView.setVisibility(View.VISIBLE);
            mPlaceOfOriginTextView.setVisibility(View.VISIBLE);
            mPlaceOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        }

        if (sandwich.getAlsoKnownAs().size() == 0){
            mAlsoKnownAsTextView.setVisibility(View.GONE);
            mAlsoKnownLabelTextView.setVisibility(View.GONE);
        }else {
            mAlsoKnownAsTextView.setVisibility(View.VISIBLE);
            mAlsoKnownLabelTextView.setVisibility(View.VISIBLE);
            String formattedAliases = listToString(sandwich.getAlsoKnownAs());
            mAlsoKnownAsTextView.setText(formattedAliases);
        }

        String formattedIngredients = listToString(sandwich.getIngredients());
        mIngredientsTextView.setText(formattedIngredients);
    }

    private String listToString(List<String> items){
        String result = "";

        StringBuilder t = new StringBuilder();
        for(int i=0; i <= items.size()-1; i++) {
            t.append(items.get(i));
            if(i != items.size()-1)
                t.append(", ");
        }
        result = t.toString();

        return result;
    }
}
