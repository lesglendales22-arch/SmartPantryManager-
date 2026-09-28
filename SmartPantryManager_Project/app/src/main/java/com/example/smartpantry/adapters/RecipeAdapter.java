package com.example.smartpantry.adapters;
import android.view.*;import android.widget.*;import androidx.recyclerview.widget.RecyclerView;import com.example.smartpantry.R;import com.example.smartpantry.models.Recipe;import java.util.*;
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.VH>{
 public interface Listener{void click(Recipe r);}private List<Recipe>d;private Listener l;public RecipeAdapter(List<Recipe>d,Listener l){this.d=d;this.l=l;}
 public void setData(List<Recipe>x){d=x;notifyDataSetChanged();}
 @Override public VH onCreateViewHolder(ViewGroup p,int v){return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_recipe,p,false));}
 @Override public void onBindViewHolder(VH h,int p){Recipe r=d.get(p);h.t.setText(r.getName());h.itemView.setOnClickListener(v->l.click(r));}
 @Override public int getItemCount(){return d.size();}
 static class VH extends RecyclerView.ViewHolder{TextView t;VH(View v){super(v);t=v.findViewById(R.id.txtRecipeName);}}
}
