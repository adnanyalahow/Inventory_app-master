package com.example.yalahow.inventoryApp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yalahow.inventoryApp.data.ProductContract.ProductEntry;


/**
 * Created by Adnan Yalahow on 8/14/2018.
 */

public class ProductCursorAdapter extends CursorAdapter  {

    /**
     * Constructs a new {@link ProductCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param viewGroup  The parent to which the new view is attached to
     * @return the newly created list item view.
     */

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_items, viewGroup, false);
    }

    /**
     * This method binds the product data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current product can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = view.findViewById(R.id.name);
        TextView quantityTextView =  view.findViewById(R.id.quantity);
        TextView priceTextView = view.findViewById(R.id.price);
        Button saleButton =  view.findViewById(R.id.sale);


        // Find the columns of product attributes that we're interested in
        int idColumnIndex = cursor.getColumnIndex(ProductEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
        int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);
        final int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);

        // Read the product attributes from the Cursor for the current product
        int productId = cursor.getInt(idColumnIndex);
        String productName = cursor.getString(nameColumnIndex);
        final int productQuantity = cursor.getInt(quantityColumnIndex);
        int productPrice = cursor.getInt(priceColumnIndex);
        final Uri uri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, productId);


        saleButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
            // Check if quantity is higher than zero
                if (productQuantity > 0) {
                    // Assign a new quantity value of minus one to represent one item sold
                    int newQuantity = productQuantity - 1;
                    // Create and initialise a new ContentValue object with the new quantity
                    ContentValues values = new ContentValues();
                    values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, newQuantity);
                    // Update the database
                    context.getContentResolver().update(uri, values, null, null);
                } else {
                    // Inform the user that quantity is zero and can't be updated
                    Toast.makeText(context, context.getString(R.string.product_out), Toast.LENGTH_SHORT).show();
                }


            }



        });
        // Update the TextViews with the attributes for the current product
        nameTextView.setText(productName);
        priceTextView.setText(context.getString(R.string.price) + " " + String.valueOf(productPrice));
        quantityTextView.setText(String.valueOf(productQuantity + " " + context.getString(R.string.quantity)));

    }
}
