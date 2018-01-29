package com.example.wow;

import android.provider.BaseColumns;

/**
 * Created by D on 1/29/2018.
 */

public class ShoppingCartContract {
    private ShoppingCartContract(){}

    public static final class ShoppingCartEntry implements BaseColumns {
        public static final String TABLE_NAME = "shoppingCart";
        public static final String COLUMN_IMAGE ="imageid";
        public static final String COLUMN_PRODUCT ="product";
        public static final String COLUMN_PRICE ="price";
        public static final String COLUMN_QUANTITY ="quantity";
        public static final String COLUMN_TIMESTAMP ="timestamp";
    }
}
