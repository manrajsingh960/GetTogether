package com.example.manrajsingh960.gettogether;

import android.provider.BaseColumns;

/**
 * Created by Dennis on 10/30/2017.
 */

public final class FeedReaderContract {
    private FeedReaderContract() {}

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COL1 = "title";
        public static final String COL2 = "start";
        public static final String COL3 = "end";
        public static final String COL4 = "description";
    }

}
