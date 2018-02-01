package com.example.jack.team09adandroidapp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by e0224927 on 28/1/2018.
 */

public class Item extends HashMap<String,String>{
    final static String baseURL = URL.baseURL+"/AndroidServices/ItemService.svc/";


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
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL + "Item" );
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

        }
        return (ItemList);
    }

    public static List<Item> getItemListByCAT(String category) {
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL + "Item/" + category);
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

        }
        return(ItemList);
    }

}
