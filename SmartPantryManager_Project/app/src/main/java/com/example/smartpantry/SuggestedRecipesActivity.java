package com.example.smartpantry;
import android.content.*;import android.os.*;import android.widget.*;import androidx.appcompat.app.AppCompatActivity;import androidx.recyclerview.widget.*;import com.example.smartpantry.adapters.RecipeAdapter;import com.example.smartpantry.database.DatabaseHelper;
public class SuggestedRecipesActivity extends AppCompatActivity{
 DatabaseHelper db;RecipeAdapter adapter;TextView empty;
 @Override protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_suggestions);db=new DatabaseHelper(this);empty=findViewById(R.id.txtEmpty);RecyclerView rv=findViewById(R.id.recyclerRecipes);rv.setLayoutManager(new LinearLayoutManager(this));adapter=new RecipeAdapter(db.getSuggestedRecipes(),r->{Intent i=new Intent(this,RecipeDetailActivity.class);i.putExtra("id",r.getId());startActivity(i);});rv.setAdapter(adapter);update();}
 @Override protected void onResume(){super.onResume();if(adapter!=null)update();}
 void update(){java.util.List<com.example.smartpantry.models.Recipe> r=db.getSuggestedRecipes();adapter.setData(r);empty.setVisibility(r.isEmpty()?android.view.View.VISIBLE:android.view.View.GONE);}
}
