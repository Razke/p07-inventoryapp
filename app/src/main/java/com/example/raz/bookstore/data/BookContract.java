package com.example.raz.bookstore.data;

import android.provider.BaseColumns;

/**
 * API Contract for the Books app.
 */
public final class BookContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private BookContract() {
    }

    /**
     * Inner class that defines constant values for the books database table.
     * Each entry in the table represents a single book.
     */
    public static final class BookEntry implements BaseColumns {
        /**
         * Name of database table for books
         */
        public final static String TABLE_NAME = "books";
        /**
         * Unique ID number for the book (only for use in the database table).
         * <p>
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;
        /**
         * Name of the book.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_BOOK_NAME = "productName";
        /**
         * Price of the book.
         * <p>
         * Type: INTEGER
         */
        public final static String COLUMN_BOOK_PRICE = "price";
        /**
         * Quantity of the books.
         * <p>
         * Type: INTEGER
         */
        public final static String COLUMN_BOOK_QUANTITY = "quantity";
        /**
         * Quantity of the books.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_BOOK_SUPPLIER = "supplierName";
        /**
         * Quantity of the books.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_BOOK_SUPPLIER_PHONE = "supplierPhoneNumber";
    }
}