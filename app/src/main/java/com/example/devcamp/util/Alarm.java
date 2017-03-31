package com.example.devcamp.util;

import java.io.Serializable;

/**
 * Created by jiyoung on 2017-02-25.
 */

public class Alarm implements Serializable {

    public Alarm(int _id, String type, String time, boolean sunday , boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday , boolean cancel, String memo, String ringtone, String ringtone_url) {
        this._id = _id;
        this.type = type;
        this.time = time;
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.cancel = cancel;
        this.memo = memo;
        this.ringtone = ringtone;
        this.ringtone_url = ringtone_url;
    }

    public Alarm(String type, String time, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday, String memo, String ringtone, String ringtone_url) {
        this.type = type;
        this.time = time;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.cancel = cancel;
        this.memo = memo;
        this.ringtone = ringtone;
        this.ringtone_url = ringtone_url;
    }

    private int _id;
    private String type;
    private String time;
    private boolean monday = false;
    private boolean tuesday = false;
    private boolean wednesday = false;
    private boolean thursday = false;
    private boolean friday = false;
    private boolean saturday = false;
    private boolean sunday = false;
    private boolean cancel = false;
    private String memo;
    private String ringtone;
    private String ringtone_url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRingtone_url() {
        return ringtone_url;
    }

    public void setRingtone_url(String ringtone_url) {
        this.ringtone_url = ringtone_url;
    }




    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getRingtone() {
        return ringtone;
    }

    public void setRingtone(String ringtone) {
        this.ringtone = ringtone;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime(){
        return time;
    }
}