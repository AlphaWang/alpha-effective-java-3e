package com.effectivejava.ch08_method;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

/**
 * Author Mr.Pro
 * Date   2018/6/24 = 下午8:38
 */
public final class Period {

    private final Date start;
    private final Date end;


    private final LocalDate startLocalDate;
    private final LocalDate endLocalDate;

    /**
     * @param start          the beginning of the period
     * @param end            the end of the period, must not precede start
     * @param startLocalDate the beginning of the period
     * @param endLocalDate   the end of the period, must not precede start
     * @throws IllegalArgumentException if start is after end, or startLocalDate is after endLocalDate
     * @throws NullPointerException     if start or end is null
     */
    public Period(Date start, Date end, LocalDate startLocalDate, LocalDate endLocalDate) {
        this.startLocalDate = startLocalDate;
        this.endLocalDate = endLocalDate;
        /*
        * from Item-49
        * */
        Objects.requireNonNull(start, "start is null");
        Objects.requireNonNull(end, "end is null");
        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException(start + " after " + end);
        }
        this.start = start;
        this.end = end;
    }

    public Date getEnd() {
        return new Date(end.getTime());
    }

    public Date getStart() {
        return start;
    }

    public LocalDate getEndLocalDate() {
        return endLocalDate;
    }

    public LocalDate getStartLocalDate() {
        return startLocalDate;
    }

    public static void main(String[] args){
        Date start = new Date();
        Date end = new Date();
        LocalDate startLD =  LocalDate.now();
        LocalDate endLD =  LocalDate.now();
        Period period = new Period(start, end, startLD, endLD);
        period.getEnd().setYear(100);
        // period.getEndLocalDate() there is not set method

    }
}
