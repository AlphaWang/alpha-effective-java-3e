package com.effectivejava.ch06_enums_annotations;

public class Item34_4_StrategyEnum {

    /**
     * Enum that switches on its value to share code - questionable
     */
    enum PayrollDay1 {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY,
        SATURDAY, SUNDAY;

        private static final int MINS_PER_SHIFT = 8 * 60;

        int pay(int minutesWorked, int payRate) {
            int basePay = minutesWorked * payRate;
            int overtimePay;
            switch(this) {
                case SATURDAY: case SUNDAY: // Weekend
                    overtimePay = basePay / 2;
                    break;
                default: // Weekday
                    overtimePay = minutesWorked <= MINS_PER_SHIFT ? 0 : (minutesWorked - MINS_PER_SHIFT) * payRate / 2;
            }
            return basePay + overtimePay;
        }
    }

    /**
     * `constant-specific method implementations` : harder to share code among enum constants
     */
    enum PayrollDay2 {
        MONDAY {
            int pay(int minutesWorked, int payRate) {
                int basePay = minutesWorked * payRate;
                int overtimePay = minutesWorked <= MINS_PER_SHIFT ? 0 : (minutesWorked - MINS_PER_SHIFT) * payRate / 2;
                return basePay + overtimePay;
            }
        },
        TUESDAY {
            int pay(int minutesWorked, int payRate) {
                int basePay = minutesWorked * payRate;
                int overtimePay = minutesWorked <= MINS_PER_SHIFT ? 0 : (minutesWorked - MINS_PER_SHIFT) * payRate / 2;
                return basePay + overtimePay;
            }
        },
        WEDNESDAY {
            int pay(int minutesWorked, int payRate) {
                int basePay = minutesWorked * payRate;
                int overtimePay = minutesWorked <= MINS_PER_SHIFT ? 0 : (minutesWorked - MINS_PER_SHIFT) * payRate / 2;
                return basePay + overtimePay;
            }
        },
        THURSDAY {
            int pay(int minutesWorked, int payRate) {
                int basePay = minutesWorked * payRate;
                int overtimePay = minutesWorked <= MINS_PER_SHIFT ? 0 : (minutesWorked - MINS_PER_SHIFT) * payRate / 2;
                return basePay + overtimePay;
            }
        },
        FRIDAY {
            int pay(int minutesWorked, int payRate) {
                int basePay = minutesWorked * payRate;
                int overtimePay = minutesWorked <= MINS_PER_SHIFT ? 0 : (minutesWorked - MINS_PER_SHIFT) * payRate / 2;
                return basePay + overtimePay;
            }
        },
        SATURDAY {
            int pay(int minutesWorked, int payRate) {
                int basePay = minutesWorked * payRate;
                int overtimePay = basePay / 2;
                return basePay + overtimePay;
            }
        },
        SUNDAY {
            int pay(int minutesWorked, int payRate) {
                int basePay = minutesWorked * payRate;
                int overtimePay = basePay / 2;
                return basePay + overtimePay;
            }
        };

        private static final int MINS_PER_SHIFT = 8 * 60;
    }

    /**
     * The strategy enum pattern
     */
    enum PayrollDay3 {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY,
        SATURDAY(PayType.WEEKEND), SUNDAY(PayType.WEEKEND);

        private final PayType payType;

        PayrollDay3(PayType payType) { this.payType = payType; }
        PayrollDay3() { this(PayType.WEEKDAY); }  // Default

        int pay(int minutesWorked, int payRate) {
            return payType.pay(minutesWorked, payRate);
        }

        // The strategy enum type
        private enum PayType {
            WEEKDAY {
                int overtimePay(int minsWorked, int payRate) {
                    return minsWorked <= MINS_PER_SHIFT ? 0 :
                        (minsWorked - MINS_PER_SHIFT) * payRate / 2;
                }
            }, WEEKEND {
                int overtimePay(int minsWorked, int payRate) {
                    return minsWorked * payRate / 2;
                }
            };

            abstract int overtimePay(int mins, int payRate);

            private static final int MINS_PER_SHIFT = 8 * 60;
            
            int pay(int minsWorked, int payRate) {
                int basePay = minsWorked * payRate;
                return basePay + overtimePay(minsWorked, payRate);
            }
        }
    }
}
