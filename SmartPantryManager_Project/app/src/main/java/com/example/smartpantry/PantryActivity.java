package com.example.smartpantry;
import android.content.*;import android.os.*;import android.widget.*;import androidx.appcompat.app.AppCompatActivity;import androidx.recyclerview.widget.*;import com.example.smartpantry.adapters.PantryAdapter;import com.example.smartpantry.database.DatabaseHelper;import com.example.smartpantry.models.PantryItem;
public class PantryActivity extends AppCompatActivity{
 DatabaseHelper db;PantryAdapter adapter;
 @Override protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_pantry);db=new DatabaseHelper(this);RecyclerView rv=findViewById(R.id.recyclerPantry);rv.setLayoutManager(new LinearLayoutManager(this));adapter=new PantryAdapter(db.getPantry(),new PantryAdapter.Listener(){public void edit(PantryItem p){Intent i=new Intent(PantryActivity.this,AddEditIngredientActivity.class);i.putExtra("id",p.getId());startActivity(i);}public void delete(PantryItem p){db.deletePantry(p.getId());load();}});rv.setAdapter(adapter);findViewById(R.id.btnAdd).setOnClickListener(v->startActivity(new Intent(this,AddEditIngredientActivity.class)));}
 @Override protected void onResume(){super.onResume();if(adapter!=null)load();}private void load(){adapter.setData(db.getPantry());}
}
