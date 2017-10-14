package com.apps.nishtha.hackdtu.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static com.apps.nishtha.hackdtu.models.Medicine.Time;

/**
 * Created by megha on 07/10/17.
 */

public class AbstractDBAdapter implements DatabaseContract{

    Context mContext;
    DatabaseHelper mdbHelper;
    SQLiteDatabase mDatabase;

    public AbstractDBAdapter(Context context){
        mContext = context;
        mdbHelper = new DatabaseHelper(mContext);
    }

    public AbstractDBAdapter open() throws SQLException {
        mDatabase = mdbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    public void insertMedicine(com.apps.nishtha.hackdtu.models.Medicine medicine){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_MEDICINE_ID, medicine.getId());
        values.put(COLUMN_NAME_MEDICINE_NAME, medicine.getName());
        values.put(COLUMN_NAME_TIMES_IN_DAY, medicine.getTimes_in_day());
        values.put(COLUMN_NAME_MEDICINES_LEFT, medicine.getMedicines_left());
        values.put(COLUMN_NAME_DAYS_LEFT, medicine.getDays_left());
        values.put(COLUMN_NAME_DAYS_TOTAL, medicine.getDays_total());
        values.put(COLUMN_NAME_DISEASE, medicine.getDisease());
        values.put(COLUMN_NAME_MEDICINE_DESCRIPTION, medicine.getMedicine_description());

        open();
        mDatabase.insert(TABLE_NAME_MEDICINE, null, values);
        close();

        if(medicine.getTimes_of_medicine() != null)
        for(Time time: medicine.getTimes_of_medicine()){
            insertTime(time);
        }
    }

    public void insertTime(Time time){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_TIME_ID, time.getTime_id());
        values.put(COLUMN_NAME_FOREIGN_MEDICINE_ID, time.getMedicine_id());
        values.put(COLUMN_NAME_TIME, time.getTime_string());

        open();
        mDatabase.insert(TABLE_NAME_TIME, null, values);
        close();
    }

    public void takenMedicine(com.apps.nishtha.hackdtu.models.Medicine.MedicineSchedule schedule){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_DATE_TIME_MEDICINE_ID, schedule.getMedicine_id());
        values.put(COLUMN_NAME_DATE_TIME_TIME_ID, schedule.getTime_id());
        values.put(COLUMN_NAME_DATE, schedule.getDate_string());
        values.put(COLUMN_NAME_DATE_TIME_MEDICINE_NAME, schedule.getMedicine_name());
        values.put(COLUMN_NAME_DATE_TIME_TIME, schedule.getTime());

        open();
        mDatabase.insert(TABLE_NAME_DATE_TIME_MEDICINE_TAKEN, null, values);
        close();
    }
    public ArrayList<com.apps.nishtha.hackdtu.models.Medicine> getAllMedicines(){
        open();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_MEDICINE;
        ArrayList<com.apps.nishtha.hackdtu.models.Medicine> medicines = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            com.apps.nishtha.hackdtu.models.Medicine medicine = new com.apps.nishtha.hackdtu.models.Medicine(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_MEDICINE_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME_MEDICINE_NAME)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_TIMES_IN_DAY)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_MEDICINES_LEFT)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_DAYS_LEFT)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_DAYS_TOTAL)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DISEASE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME_MEDICINE_DESCRIPTION)),
                    null );
            medicines.add(medicine);
        }
        return medicines;
    }

    public com.apps.nishtha.hackdtu.models.Medicine getMedicine(long id){
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_MEDICINE + " WHERE " + COLUMN_NAME_MEDICINE_ID + " = " + id;
        com.apps.nishtha.hackdtu.models.Medicine medicine = null;

        open();
        Cursor cursor = mDatabase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {

            ArrayList<com.apps.nishtha.hackdtu.models.Medicine.Time> times = getTimesByMedicine(id);

            medicine = new com.apps.nishtha.hackdtu.models.Medicine(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_MEDICINE_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME_MEDICINE_NAME)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_TIMES_IN_DAY)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_MEDICINES_LEFT)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_DAYS_LEFT)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_DAYS_TOTAL)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DISEASE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME_MEDICINE_DESCRIPTION)),
                    times );
        }
        cursor.close();
        close();
        return medicine;
    }

    public ArrayList<com.apps.nishtha.hackdtu.models.Medicine.Time> getTimesByMedicine(long medicine_id){
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_TIME + " WHERE " + COLUMN_NAME_FOREIGN_MEDICINE_ID + " = " + medicine_id;
        ArrayList<com.apps.nishtha.hackdtu.models.Medicine.Time> times = new ArrayList<>();

        open();
        Cursor cursor = mDatabase.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {

            com.apps.nishtha.hackdtu.models.Medicine.Time time = new com.apps.nishtha.hackdtu.models.Medicine.Time(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_TIME_ID)),
                    medicine_id,
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TIME))
            );
            times.add(time);

        }
        cursor.close();
        close();
        return times;
    }

    public com.apps.nishtha.hackdtu.models.Medicine.Time getTime(long id){
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_TIME + " WHERE " + COLUMN_NAME_FOREIGN_MEDICINE_ID + " = " + id;
        com.apps.nishtha.hackdtu.models.Medicine.Time time = null;

        open();
        Cursor cursor = mDatabase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            time = new com.apps.nishtha.hackdtu.models.Medicine.Time(
                    id,
                    cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_FOREIGN_MEDICINE_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TIME))
            );
        }
        cursor.close();
        close();
        return time;
    }

    public ArrayList<com.apps.nishtha.hackdtu.models.Medicine.MedicineSchedule> getScheduleOfMedicine(long medicine_id, String date){
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_DATE_TIME_MEDICINE_TAKEN + " WHERE " +
                COLUMN_NAME_DATE_TIME_MEDICINE_ID + " = " + medicine_id  + " and " +
                COLUMN_NAME_DATE + " = \"" + date + "\" ORDER BY " +
                COLUMN_NAME_DATE_TIME_TIME;
        ArrayList<com.apps.nishtha.hackdtu.models.Medicine.MedicineSchedule> schedule = new ArrayList<>();

        open();
        Cursor cursor = mDatabase.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {

            com.apps.nishtha.hackdtu.models.Medicine.MedicineSchedule time = new com.apps.nishtha.hackdtu.models.Medicine.MedicineSchedule(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_DATE_TIME_TIME_ID)),
                    medicine_id,
                    date,
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DATE_TIME_MEDICINE_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TIME))
            );
            schedule.add(time);

        }
        cursor.close();
        close();
        return schedule;
    }

    public ArrayList<com.apps.nishtha.hackdtu.models.Medicine.MedicineSchedule> getScheduleOfDate(String date){
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_DATE_TIME_MEDICINE_TAKEN + " WHERE " +
                COLUMN_NAME_DATE + " = " + date + " ORDER BY " +
                COLUMN_NAME_TIME + ", " + COLUMN_NAME_MEDICINE_NAME;
        ArrayList<com.apps.nishtha.hackdtu.models.Medicine.MedicineSchedule> schedule = new ArrayList<>();

        open();
        Cursor cursor = mDatabase.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {

            com.apps.nishtha.hackdtu.models.Medicine.MedicineSchedule time = new com.apps.nishtha.hackdtu.models.Medicine.MedicineSchedule(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_DATE_TIME_TIME_ID)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_DATE_TIME_MEDICINE_ID)),
                    date,
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DATE_TIME_MEDICINE_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TIME))
            );
            schedule.add(time);

        }
        cursor.close();
        close();
        return schedule;
    }

    public boolean deleteMedicine(long id){
        open();
        boolean deleted = mDatabase.delete(TABLE_NAME_MEDICINE, COLUMN_NAME_MEDICINE_ID + "=" + id, null) > 0;
        deleted &= mDatabase.delete(TABLE_NAME_TIME, COLUMN_NAME_FOREIGN_MEDICINE_ID + "=" + id, null) > 0;
        deleted &= mDatabase.delete(TABLE_NAME_DATE_TIME_MEDICINE_TAKEN, COLUMN_NAME_DATE_TIME_MEDICINE_ID + "=" + id, null) > 0;
        close();
        return deleted;
    }

    public boolean deleteTime(long id){
        open();
        boolean deleted = mDatabase.delete(TABLE_NAME_TIME, COLUMN_NAME_TIME_ID + "=" + id, null) > 0;
        close();
        return deleted;
    }

    public boolean deleteMedicineTaken(com.apps.nishtha.hackdtu.models.Medicine.MedicineSchedule schedule){
        open();
        boolean deleted = mDatabase.delete(TABLE_NAME_TIME,
                COLUMN_NAME_DATE_TIME_MEDICINE_ID + "=" + schedule.getMedicine_id() + " and " +
                        COLUMN_NAME_DATE_TIME_TIME_ID + "=" + schedule.getTime_id() + " and " +
                        COLUMN_NAME_DATE + "=" + schedule.getDate_string()
                , null) > 0;
        close();
        return deleted;
    }

    public long getNextMedicineId(){
        open();
        return DatabaseUtils.queryNumEntries(mDatabase,  TABLE_NAME_MEDICINE) + 1;
    }

    public long getNextTimeId(){
        open();
        return DatabaseUtils.queryNumEntries(mDatabase,  TABLE_NAME_TIME) + 1;
    }

}