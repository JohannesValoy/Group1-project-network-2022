package no.ntnu.idata2304.group1.data;

import java.util.Comparator;

public class SortByDate implements Comparator<SensorRecord> {


    @Override
    public int compare(SensorRecord o1, SensorRecord o2) {
        return o1.date().compareTo(o2.date());
    }
}
