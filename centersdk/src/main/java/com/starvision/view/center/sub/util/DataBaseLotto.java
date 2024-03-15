package com.starvision.view.center.sub.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseLotto extends SQLiteOpenHelper {

	private static final String DB_NAME = "MyData";
	private static final int DB_VERSION = 8;

	public static final String TABLE_NAME = "Lotto";
	public static final String COL_STATUS = "name";
	public static final String COL_DATE_SEV = "dateserver";
	public static final String COL_DATE_Dev = "datedevice";
	public static final String COL_RADIO = "radio";
	public static final String COL_DELAYCCU = "delayccu";
	
	public static final String TABLE_NAME_CHECK = "CheckLotto";
	public static final String CHE_DATE = "date";
	public static final String CHE_ONE = "one";
	public static final String CHE_ONE_BY = "one_by";
	public static final String CHE_TWO_END = "two_end";
	public static final String CHE_TWO = "two";
	public static final String CHE_FIRST_THREE_END = "first_three_end";
	public static final String CHE_THREE_END = "three_end";
	public static final String CHE_THREE = "three";
	public static final String CHE_FOUR = "four";
	public static final String CHE_FIVE = "five";
	public static final String CHE_LINK_LOTTO = "linklotto";
	public static final String FORM_NEW = "form_new";
	
	public static final String TABLE_NAME_LEAVEREVIEW = "LeaveReview";
	public static final String CHE_NUMBER_LEAVE = "numberleave";
	public static final String CHE_DATE_LEAVE = "dateleave";

	public static final String TABLE_NAME_LOTTO_HISTORY= "HistoryLotto";
	public static final String CHE_STRDATE_HIS = "StrDate";
	public static final String CHE_STR_RESULT_HIS = "StrResult";
	public static final String CHE_STR_NUMBER_HIS = "StrNumber";
	public static final String CHE_STR_TEXT_HIS = "StrText";
	public static final String CHE_STR_BOOQR_HIS = "isBooQrCode";


	private static final String CREATE_TABLE_NOTIC = "CREATE TABLE "
			+ TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COL_STATUS + " TEXT, " 
			+ COL_DATE_SEV + " TEXT, " 
			+ COL_DATE_Dev + " TEXT, "
			+ COL_RADIO + " TEXT, "
			+ COL_DELAYCCU + " TEXT" + ");";
	
	private static final String CREATE_TABLE_CHECKLOTTO = "CREATE TABLE "
			+ TABLE_NAME_CHECK + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ CHE_DATE + " TEXT," 
			+ CHE_ONE + " TEXT," 
			+ CHE_ONE_BY + " TEXT,"
			+ CHE_TWO_END + " TEXT," 
			+ CHE_TWO + " TEXT," 
			+ CHE_FIRST_THREE_END + " TEXT," 
			+ CHE_THREE_END + " TEXT," 
			+ CHE_THREE + " TEXT," 
			+ CHE_FOUR + " TEXT," 
			+ CHE_FIVE + " TEXT,"
			+ CHE_LINK_LOTTO + " TEXT,"
			+ FORM_NEW + " TEXT);";
	
	private static final String CREATE_TABLE_LEAVEREVIEW = "CREATE TABLE "
			+ TABLE_NAME_LEAVEREVIEW + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ CHE_DATE_LEAVE + " TEXT,"
			+ CHE_NUMBER_LEAVE + " TEXT" + ");";

	private static final String CREATE_TABLE_LOTTO_HISTORY = "CREATE TABLE "
			+ TABLE_NAME_LOTTO_HISTORY + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ CHE_STRDATE_HIS + " TEXT,"
			+ CHE_STR_RESULT_HIS + " TEXT,"
			+ CHE_STR_NUMBER_HIS + " TEXT,"
			+ CHE_STR_TEXT_HIS + " TEXT);";




	public DataBaseLotto(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_NOTIC);
		db.execSQL(CREATE_TABLE_CHECKLOTTO);
		db.execSQL(CREATE_TABLE_LEAVEREVIEW);
		db.execSQL(CREATE_TABLE_LOTTO_HISTORY);

		db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL_STATUS + ", "
		+ COL_DATE_SEV + ", "
		+ COL_DATE_Dev + ", "
		+ COL_DELAYCCU + ") VALUES ('True', '00000', '','0');");
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME + "'");
		db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME_CHECK + "'");
		db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME_LEAVEREVIEW + "'");
		db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME_LOTTO_HISTORY + "'");
		onCreate(db);
	}
}
