package com.example.smartpantry;
import android.os.*;import android.widget.*;import androidx.appcompat.app.AppCompatActivity;import com.example.smartpantry.database.DatabaseHelper;import com.example.smartpantry.models.Recipe;
public class RecipeDetailActivity extends AppCompatActivity{
 @Override protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_recipe_detail);DatabaseHelper db=new DatabaseHelper(this);int id=getIntent().getIntExtra("id",-1);Recipe r=db.getRecipe(id);if(r!=null){((TextView)findViewById(R.id.txtRecipeTitle)).setText(r.getName());((TextView)findViewById(R.id.txtIngredients)).setText("Ingredients:\n\n"+db.getIngredients(id));((TextView)findViewById(R.id.txtInstructions)).setText("Preparation:\n\n"+r.getInstructions());}}
}
