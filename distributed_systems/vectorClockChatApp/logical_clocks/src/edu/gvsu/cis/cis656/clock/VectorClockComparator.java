package edu.gvsu.cis.cis656.clock;

import java.util.Comparator;


public class VectorClockComparator implements Comparator<VectorClock> {

    @Override
    public int compare(VectorClock lhs, VectorClock rhs) {
        int rv;
        if (lhs.happenedBefore(rhs)) {
            rv = -1;
        }
        else if (rhs.happenedBefore(lhs)) {
            rv = 1;
        }
        else {
            rv = 0;
        }
        return rv;
    }
}
