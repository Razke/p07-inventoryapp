package com.example.raz.bookstore;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raz.bookstore.data.BookContract;

public class DetailsActivity extends AppCompatActivity {

    TextView bookNameTextView;

    TextView priceTextView;

    TextView quantityTextView;

    Integer quantity;

    TextView supplierNameTextView;

    TextView supplierPhoneNumberTextView;

    public String supplierPhoneNumber;

    Uri currentBookUri;

    Uri newUri;

    Button deleteButton;

    Button editButton;

    Button decrementButton;

    Button incrementButton;

    Button contactButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Disable portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        currentBookUri = intent.getData();

        Intent i = getIntent();
        newUri = i.getData();

        bookNameTextView = findViewById(R.id.text_book_name);
        priceTextView = findViewById(R.id.text_book_price);
        quantityTextView = findViewById(R.id.text_book_quantity);
        supplierNameTextView = findViewById(R.id.text_book_supplier);
        supplierPhoneNumberTextView = findViewById(R.id.text_book_supplier_phone);

        deleteButton = findViewById(R.id.button_delete);
        editButton = findViewById(R.id.button_edit);
        decrementButton = findViewById(R.id.button_decrement);
        incrementButton = findViewById(R.id.button_increment);
        contactButton = findViewById(R.id.button_contact);

        Cursor c = managedQuery(currentBookUri, null, null, null, "productName");

        if (c.moveToFirst()) {
            do {
                String nameString = c.getString(c.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_NAME));
                String priceString = c.getString(c.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_PRICE));
                String quantityString = c.getString(c.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_QUANTITY));
                String supplierNameString = c.getString(c.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_SUPPLIER));
                supplierPhoneNumber = c.getString(c.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_SUPPLIER_PHONE));
                quantity = c.getInt(c.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_QUANTITY));

                bookNameTextView.setText(nameString);
                priceTextView.setText("Price: £" + priceString);
                quantityTextView.setText(quantityString.toString());
                supplierNameTextView.setText("Supplier: " + supplierNameString);
                supplierPhoneNumberTextView.setText("Supplier Phone: " + supplierPhoneNumber);

            } while (c.moveToNext());
        }

        editButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, EditorActivity.class);

                intent.setData(currentBookUri);
                startActivity(intent);
            }
        });

        decrementButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (quantity > 0) {

                    quantity = quantity - 1;

                    ContentValues values = new ContentValues();
                    values.put(BookContract.BookEntry.COLUMN_BOOK_QUANTITY, quantity);
                    getContentResolver().update(currentBookUri, values, null, null);

                    quantityTextView.setText(quantity.toString());
                }
            }
        });

        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                quantity = quantity + 1;

                ContentValues values = new ContentValues();
                values.put(BookContract.BookEntry.COLUMN_BOOK_QUANTITY, quantity);
                getContentResolver().update(currentBookUri, values, null, null);
                quantityTextView.setText(quantity.toString());
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDeleteConfirmationDialog();
            }
        });

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + supplierPhoneNumber));
                startActivity(intent);
            }
        });
    }

    private void showDeleteConfirmationDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                deleteBook();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteBook() {
        if (currentBookUri != null) {

            int rowsDeleted = getContentResolver().delete(currentBookUri, null, null);

            if (rowsDeleted == 0) {
                Toast.makeText(this, getString(R.string.editor_delete_book_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_delete_book_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }
}