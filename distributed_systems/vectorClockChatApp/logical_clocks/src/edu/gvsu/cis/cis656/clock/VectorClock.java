package edu.gvsu.cis.cis656.clock;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

public class VectorClock implements Clock {

    // suggested data structure ...
    private Map<String,Integer> clock = new Hashtable<String,Integer>();

    @Override
    public void update(Clock other) {
        Set<String> myKeys = clock.keySet();
        for (String key : other.getKeyset()) {
            if (myKeys.contains(key)) {
                Integer myRecord = clock.get(key);
                Integer otherRecord = other.getTime(Integer.parseInt(key));
                if (otherRecord > myRecord) {
                    clock.put(key, otherRecord);
                }
            }
            else {
                Integer otherPid = Integer.parseInt(key);
                addProcess(otherPid, other.getTime(otherPid));
            }
        }
    }

    @Override
    public void setClock(Clock other) {
        clock.clear();
        for (String key : other.getKeyset()) {
            Integer otherPid = Integer.parseInt(key);
            addProcess(otherPid, other.getTime(otherPid));
        }
    }

    @Override
    public void tick(Integer pid) {
        String key = Integer.toString(pid);
        Integer currVal = clock.get(key);
        if (null == currVal) {
            addProcess(pid, 1);
        }
        else {
            clock.put(key, currVal + 1);
        }
    }

    @Override
    public boolean happenedBefore(Clock other) {
        boolean rv = true;
        for (String key : clock.keySet()) {
            Integer otherRecord = other.getTime(Integer.parseInt(key));
            if (otherRecord < clock.get(key)) {
                rv = false;
                break;
            }
        }
        return rv;
    }

    public String toString() {
        JSONObject clockJson = new JSONObject();
        for (String key : clock.keySet()) {
            clockJson.put(key, clock.get(key));
        }
        return clockJson.toString();
    }

    @Override
    public void setClockFromString(String clockStr) {
        Map<String, Integer> newClock = new Hashtable<String,Integer>();
        try {
            JSONObject clockJson = new JSONObject(clockStr);
            for (String key : clockJson.keySet()) {
                newClock.put(key, clockJson.getInt(key));
            }
            clock.clear();
            clock = newClock;
        }
        catch (Exception e) {
            /* There was an error; probably bad string format */
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int getTime(int p) {
        Integer rv = clock.get(Integer.toString(p));
        if (null == rv) {
            rv = 0;
        }
        return rv;
    }

    @Override
    public void addProcess(int p, int c) {
        clock.put(Integer.toString(p), c);
    }

    @Override
    public Set<String> getKeyset() {
        return clock.keySet();
    }
}
