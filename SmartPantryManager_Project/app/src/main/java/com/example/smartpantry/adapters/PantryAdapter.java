package com.example.smartpantry.adapters;
import android.view.*;import android.widget.*;import androidx.recyclerview.widget.RecyclerView;import com.example.smartpantry.R;import com.example.smartpantry.models.PantryItem;import java.util.*;
public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.VH>{
 public interface Listener{void edit(PantryItem p);void delete(PantryItem p);}
 private List<PantryItem> data;private Listener listener;public PantryAdapter(List<PantryItem>d,Listener l){data=d;listener=l;}
 public void setData(List<PantryItem>d){data=d;notifyDataSetChanged();}
 @Override public VH onCreateViewHolder(ViewGroup p,int v){return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_pantry,p,false));}
 @Override public void onBindViewHolder(VH h,int pos){PantryItem x=data.get(pos);h.name.setText(x.getName());h.qty.setText(x.getQuantity()+" "+x.getUnit());h.edit.setOnClickListener(v->listener.edit(x));h.del.setOnClickListener(v->listener.delete(x));}
 @Override public int getItemCount(){return data.size();}
 static class VH extends RecyclerView.ViewHolder{TextView name,qty;Button edit,del;VH(View v){super(v);name=v.findViewById(R.id.txtName);qty=v.findViewById(R.id.txtQuantity);edit=v.findViewById(R.id.btnEdit);del=v.findViewById(R.id.btnDelete);}}
}
