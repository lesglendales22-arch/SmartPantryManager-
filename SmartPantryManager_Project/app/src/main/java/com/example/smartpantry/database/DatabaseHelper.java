package com.example.smartpantry.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.smartpantry.models.PantryItem;
import com.example.smartpantry.models.Recipe;
import java.util.*;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB="smart_pantry.db";
    public DatabaseHelper(Context c){super(c,DB,null,1);}
    @Override public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE pantry(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL,quantity REAL NOT NULL,unit TEXT NOT NULL,expiry TEXT)");
        db.execSQL("CREATE TABLE recipes(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL,instructions TEXT NOT NULL)");
        db.execSQL("CREATE TABLE recipe_ingredients(id INTEGER PRIMARY KEY AUTOINCREMENT,recipe_id INTEGER,ingredient_name TEXT,quantity REAL,unit TEXT)");
        seedRecipes(db);
    }
    private void seedRecipes(SQLiteDatabase db){
        addRecipe(db,"Vegetable Omelette","Eggs, Tomatoes, Cheese","Beat eggs. Add tomatoes and cheese. Cook in a pan until set.");
        addRecipe(db,"Tomato Pasta","Pasta, Tomatoes, Cheese","Cook pasta. Add tomatoes and cheese. Mix and serve.");
        addRecipe(db,"Chicken Rice","Chicken, Rice, Onion","Cook chicken and onion. Add cooked rice and mix.");
        addRecipe(db,"Vegetable Stir Fry","Carrot, Cabbage, Onion","Chop vegetables and stir fry until cooked.");
        addRecipe(db,"Tuna Sandwich","Bread, Tuna, Mayonnaise","Mix tuna and mayonnaise. Place between bread slices.");
        addRecipe(db,"Pancakes","Flour, Milk, Eggs","Mix ingredients and cook small portions in a pan.");
        addRecipe(db,"French Toast","Bread, Milk, Eggs","Dip bread in egg and milk mixture. Fry both sides.");
        addRecipe(db,"Chicken Pasta","Chicken, Pasta, Tomatoes, Cheese","Cook pasta and chicken. Add tomatoes and cheese.");
        addRecipe(db,"Tomato Soup","Tomatoes, Onion, Water","Cook tomatoes and onion with water. Blend until smooth.");
        addRecipe(db,"Vegetable Soup","Carrot, Potato, Onion, Water","Boil vegetables until soft and season to taste.");
        addRecipe(db,"Egg Fried Rice","Rice, Eggs, Onion","Fry onion and egg. Add cooked rice and mix.");
        addRecipe(db,"Cheese Sandwich","Bread, Cheese, Butter","Butter bread, add cheese and toast.");
        addRecipe(db,"Potato Omelette","Potato, Eggs, Onion","Cook potato and onion. Add beaten eggs and cook.");
        addRecipe(db,"Chicken Wrap","Chicken, Tortilla, Lettuce, Tomato","Cook chicken and fill tortilla with all ingredients.");
        addRecipe(db,"Spaghetti Bolognese","Pasta, Mince, Tomatoes, Onion","Cook mince and onion. Add tomatoes and pasta.");
        addRecipe(db,"Chicken Salad","Chicken, Lettuce, Tomato, Cucumber","Cook chicken and combine with vegetables.");
        addRecipe(db,"Garlic Pasta","Pasta, Garlic, Butter","Cook pasta. Fry garlic in butter and combine.");
        addRecipe(db,"Tuna Pasta","Pasta, Tuna, Mayonnaise","Cook pasta. Mix with tuna and mayonnaise.");
        addRecipe(db,"Vegetable Rice","Rice, Carrot, Peas, Onion","Fry vegetables and combine with cooked rice.");
        addRecipe(db,"Egg Sandwich","Bread, Eggs, Mayonnaise","Boil eggs, mix with mayonnaise and place on bread.");
    }
    private void addRecipe(SQLiteDatabase db,String n,String ingredients,String instructions){
        ContentValues v=new ContentValues(); v.put("name",n); v.put("instructions",instructions); long id=db.insert("recipes",null,v);
        for(String s:ingredients.split(", ")){ContentValues i=new ContentValues();i.put("recipe_id",id);i.put("ingredient_name",normalize(s));i.put("quantity",1);i.put("unit","unit");db.insert("recipe_ingredients",null,i);}
    }
    private String normalize(String s){s=s.toLowerCase().trim(); if(s.endsWith("s") && !s.endsWith("ss")) s=s.substring(0,s.length()-1); return s;}
    public long addPantry(String n,double q,String u,String e){ContentValues v=new ContentValues();v.put("name",n);v.put("quantity",q);v.put("unit",u);v.put("expiry",e);return getWritableDatabase().insert("pantry",null,v);}
    public int updatePantry(int id,String n,double q,String u,String e){ContentValues v=new ContentValues();v.put("name",n);v.put("quantity",q);v.put("unit",u);v.put("expiry",e);return getWritableDatabase().update("pantry",v,"id=?",new String[]{String.valueOf(id)});}
    public int deletePantry(int id){return getWritableDatabase().delete("pantry","id=?",new String[]{String.valueOf(id)});}
    public List<PantryItem> getPantry(){List<PantryItem> l=new ArrayList<>();Cursor c=getReadableDatabase().rawQuery("SELECT * FROM pantry ORDER BY name",null);while(c.moveToNext())l.add(new PantryItem(c.getInt(0),c.getString(1),c.getDouble(2),c.getString(3),c.getString(4)));c.close();return l;}
    public PantryItem getPantryItem(int id){Cursor c=getReadableDatabase().rawQuery("SELECT * FROM pantry WHERE id=?",new String[]{String.valueOf(id)});PantryItem p=null;if(c.moveToFirst())p=new PantryItem(c.getInt(0),c.getString(1),c.getDouble(2),c.getString(3),c.getString(4));c.close();return p;}
    public List<Recipe> getSuggestedRecipes(){List<Recipe> out=new ArrayList<>();Map<String,Double> pantry=new HashMap<>();Cursor p=getReadableDatabase().rawQuery("SELECT name,quantity FROM pantry",null);while(p.moveToNext())pantry.put(normalize(p.getString(0)),p.getDouble(1));p.close();Cursor r=getReadableDatabase().rawQuery("SELECT id,name,instructions FROM recipes",null);while(r.moveToNext()){int id=r.getInt(0);Cursor i=getReadableDatabase().rawQuery("SELECT ingredient_name,quantity FROM recipe_ingredients WHERE recipe_id=?",new String[]{String.valueOf(id)});boolean ok=true;while(i.moveToNext()){String n=normalize(i.getString(0));double q=i.getDouble(1);if(!pantry.containsKey(n)||pantry.get(n)<q){ok=false;break;}}i.close();if(ok)out.add(new Recipe(id,r.getString(1),r.getString(2)));}r.close();return out;}
    public Recipe getRecipe(int id){Cursor c=getReadableDatabase().rawQuery("SELECT id,name,instructions FROM recipes WHERE id=?",new String[]{String.valueOf(id)});Recipe r=null;if(c.moveToFirst())r=new Recipe(c.getInt(0),c.getString(1),c.getString(2));c.close();return r;}
    public String getIngredients(int recipeId){StringBuilder b=new StringBuilder();Cursor c=getReadableDatabase().rawQuery("SELECT ingredient_name,quantity,unit FROM recipe_ingredients WHERE recipe_id=?",new String[]{String.valueOf(recipeId)});while(c.moveToNext())b.append("• ").append(c.getString(0)).append(" - ").append(c.getDouble(1)).append(" ").append(c.getString(2)).append("\n");c.close();return b.toString();}
    @Override public void onUpgrade(SQLiteDatabase db,int oldV,int newV){db.execSQL("DROP TABLE IF EXISTS recipe_ingredients");db.execSQL("DROP TABLE IF EXISTS recipes");db.execSQL("DROP TABLE IF EXISTS pantry");onCreate(db);}
}
