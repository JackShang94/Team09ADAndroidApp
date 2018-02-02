package com.example.jack.team09adandroidapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Yuanxushu on 28/1/2018.
 */

public class Item extends HashMap<String,String>{
    final static String baseURL = "http://172.17.251.72:80/logicU/AndroidServices/ItemService.svc/";


    public Item(String itemID,String categoryID,String description,String location,
                String unitOfMeasure,String reorderLevel,String reorderQty,String qtyOnHand){
        put("ItemID",itemID);
        put("CategoryID",categoryID);
        put("Description",description);
        put("Location",location);
        put("UnitOfMeasure",unitOfMeasure);
        put("ReorderLevel",reorderLevel);
        put("ReorderQty",reorderQty);
        put("QtyOnHand",qtyOnHand);

    }

    public static List<Item> getItemList() {
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL + "Items" );
        List<Item> ItemList = new ArrayList<Item>();
        try {
            for (int i = 0; i < a.length(); i++) {
                JSONObject b = a.getJSONObject(i);

                ItemList.add(new Item(b.getString("ItemID"), b.getString("CategoryID"),
                        b.getString("Description"),b.getString("Location"), b.getString("UnitOfMeasure"),
                        b.getString("ReorderLevel"),b.getString("ReorderQty"),
                        b.getString("QtyOnHand")));
            }
        } catch (Exception e) {
            Log.e("item",e.toString());
        }
        return (ItemList);
    }

    public static List<Item> getItemListBySearch(String keyword) {
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL + "Items/" + keyword);
        List<Item> ItemList = new ArrayList<Item>();
        try {
            for (int i = 0; i < a.length(); i++) {
                JSONObject b = a.getJSONObject(i);

                ItemList.add(new Item(b.getString("ItemID"), b.getString("CategoryID"),
                        b.getString("Description"),b.getString("Location"), b.getString("UnitOfMeasure"),
                        b.getString("ReorderLevel"),b.getString("ReorderQty"),
                        b.getString("QtyOnHand")));
            }
        }catch(Exception e){
            Log.e("itemSearch",e.toString());
        }
        return(ItemList);
    }

    public static Item getItemByID(String id) {
        Item item = null;
        try {
            JSONObject jb = JSONParser.getJSONFromUrl(baseURL+"Item/"+id);
            item = new Item(jb.getString("ItemID"), jb.getString("CategoryID"),jb.getString("Description"),jb.getString("Location"), jb.getString("UnitOfMeasure"),
                            jb.getString("ReorderLevel"),jb.getString("ReorderQty"),
                            jb.getString("QtyOnHand"));
        } catch (Exception e) {
            Log.e("getItemID",e.toString());
        }
        return item;
    }

}
