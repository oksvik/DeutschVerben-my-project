package com.example.dudar.deutschverben.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by dudar on 31.03.2017.
 */

public final class duvContract {
    private duvContract (){
    }

    public static final String CONTENT_AUTHORITY = "com.example.dudar.deutschverben";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_GROUPS = "groups";
    public static final String PATH_VERBS = "verbs";

    public static abstract class GroupEntry implements BaseColumns {

        /** Name of database table for pets */
        public static final String TABLE_NAME = "groups";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_EINORDNUNG = "einordnung";
        public static final String COLUMN_MNEMO = "mnemo";
        public static final String COLUMN_GROUP = "groupNumber";
        public static final String COLUMN_ANNOTATION = "annotation";

        public static final String JSON_ID = "id";
        public static final String JSON_EINORDNUNG = "einordnung";
        public static final String JSON_MNEMO = "mnemo";
        public static final String JSON_GROUP = "group";
        public static final String JSON_ANNOTATION = "annotation";

        /** The content URI to access the duv data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_GROUPS);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of groups.
         */
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GROUPS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single group.
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GROUPS;

    }

    public static abstract class VerbEntry implements BaseColumns {

        /** Name of database table for pets */
        public static final String TABLE_NAME = "verbs";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_INFINITIV = "infinitiv";
        public static final String COLUMN_PRAETERITUM = "praeteritum";
        public static final String COLUMN_PERFEKT = "perfekt";
        public static final String COLUMN_HILFSVERB = "hilfsverb";
        public static final String COLUMN_GROUPID = "groupId";
        public static final String COLUMN_TRANSLATION = "translation";

        public static final String JSON_ID = "id";
        public static final String JSON_INFINITIV = "infinitiv";
        public static final String JSON_PRAETERITUM = "praeteritum";
        public static final String JSON_PERFEKT = "perfekt";
        public static final String JSON_HILFSVERB = "hilfsverb";
        public static final String JSON_GROUPID = "groupId";
        public static final String JSON_TRANSLATION = "translation";

        /** The content URI to access the duv data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_VERBS);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of verbs.
         */
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VERBS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single verb.
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VERBS;

    }
}
