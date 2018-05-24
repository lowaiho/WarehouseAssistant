package com.otel.warehouseassistant.tool;

import android.content.Context;

import com.otel.warehouseassistant.EnquiryActivity;
import com.otel.warehouseassistant.ListImageActivity;
import com.otel.warehouseassistant.R;
import com.otel.warehouseassistant.menu.MenuObject;

import java.util.ArrayList;
import java.util.List;

public class MenuUtils {
    private static List<MenuObject> listViewItems = new ArrayList<>();

    private final static int[] listColors = {R.color.menuBlockItemColor1, R.color.menuBlockItemColor2, R.color.menuBlockItemColor3,
            R.color.menuBlockItemColor4, R.color.menuBlockItemColor5, R.color.menuBlockItemColor6,
            R.color.menuBlockItemColor7, R.color.menuBlockItemColor8, R.color.menuBlockItemColor9,
            R.color.menuBlockItemColor10};

    private static int getBGColor(int i) {
        return listColors[i % 10];
    }

    private static void LoadAllMenus(Context context) {
        listViewItems = new ArrayList<>();

        listViewItems.add(new MenuObject(context.getString(R.string.search), R.drawable.box, getBGColor(listViewItems.size()), null, "ALL", null, EnquiryActivity.class));
        listViewItems.add(new MenuObject(context.getString(R.string.takePhoto), R.drawable.photo_camera, getBGColor(listViewItems.size()), null, "ALL", null, ListImageActivity.class));
        listViewItems.add(new MenuObject(context.getString(R.string.inbound), R.drawable.receiving, getBGColor(listViewItems.size()), null, "ALL", null, EnquiryActivity.class));
        listViewItems.add(new MenuObject(context.getString(R.string.outbound), R.drawable.delivery, getBGColor(listViewItems.size()), null, "ALL", null, EnquiryActivity.class));

    }

    public static List<MenuObject> LoadMainMenu(Context context) {
        LoadAllMenus(context);

        List<MenuObject> result = new ArrayList<>();

        result.addAll(listViewItems);
        /*
        for (MenuObject m : listViewItems) {
            if (m.getForOperations().equalsIgnoreCase("ALL")
                    || availableOperations.equalsIgnoreCase(OPERATION_DEVELOPMENT)
                    || m.getForOperations().contains(availableOperations)) {
                result.add(m);
            }
        }
        */
        return result;
    }
}
