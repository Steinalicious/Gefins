package Entities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class QueueInfo {
    String numInQue, firstInQue;

    public QueueInfo() {
        this.numInQue = "0";
        this.firstInQue = "Enginn í biðröð";
    }

    public QueueInfo(JSONObject queueInfo) {
        try {
            Log.d("HAHAHAHA", queueInfo.toString());
            this.numInQue = queueInfo.getString("numInQue");
            this.firstInQue = queueInfo.getString( "firstInQue");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getNumInQue() {
        return numInQue;
    }

    public void setNumInQue(String numInQue) {
        this.numInQue = numInQue;
    }

    public String getFirstInQue() {
        return firstInQue;
    }

    public void setFirstInQue(String firstInQue) {
        this.firstInQue = firstInQue;
    }

    @Override
    public String toString() {
        return "QueueInfo{" +
                "numInQue='" + numInQue + '\'' +
                ", firstInQue='" + firstInQue + '\'' +
                '}';
    }
}
