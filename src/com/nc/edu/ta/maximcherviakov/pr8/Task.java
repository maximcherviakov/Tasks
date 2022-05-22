package com.nc.edu.ta.maximcherviakov.pr8;

public class Task implements Cloneable {
    private String title;
    private int time;
    private int start;
    private int end;
    private int repeat;
    private boolean isActive;
    private boolean isRepeated;

    /**
     * Empty constructor
     */
    public Task() {
    }

    /**
     * Constructor, which sets title and time of the task.
     * And make it not active.
     *
     * @param title title of the task
     * @param time  time of the next notification
     */
    public Task(String title, int time) throws RuntimeException {
        setTitle(title);
        setTime(time);
        setActive(false);
    }

    /**
     * Constructor, which sets title, start time, end time and repeat interval.
     * And make it not active.
     *
     * @param title  title of the task
     * @param start  start time
     * @param end    end time
     * @param repeat repeat interval
     */
    public Task(String title, int start, int end, int repeat) throws RuntimeException {
        setTitle(title);
        setTime(start, end, repeat);
        setActive(false);
    }

    /**
     * @return the status of current task.
     */
    @Override
    public String toString() {
        if (!isActive()) {
            return "Task \"" + getTitle() + "\" is inactive";
        } else {
            if (!isRepeated()) {
                return "Task \"" + getTitle() + "\" at " + getTime();
            } else {
                return "Task \"" + getTitle() + "\" from " + getStartTime() + " to " + getEndTime() + " every " + getRepeatInterval() + " seconds";
            }
        }
    }

    /**
     * @param time current time
     * @return the time to the next notification.
     */
    public int nextTimeAfter(int time) throws RuntimeException {
        if (time < 0) {
            throw new RuntimeException("Entered incorrect time");
        } else {
            if (isActive()) {
                if (isRepeated()) {
                    if (time < getStartTime()) {
                        return getStartTime();
                    } else if (getStartTime() + getRepeatInterval() * ((time - getStartTime()) / getRepeatInterval()) + getRepeatInterval() <= getEndTime()) {
                        return getStartTime() + getRepeatInterval() * ((time - getStartTime()) / getRepeatInterval()) + getRepeatInterval();
                    } else {
                        return -1;
                    }
                } else {
                    if (time < getTime()) {
                        return getTime();
                    } else {
                        return -1;
                    }
                }

            } else {
                return -1;
            }
        }
    }

    /**
     * @return cloned task
     */
    @Override
    public Task clone() {
        Task cloneTask = new Task();

        cloneTask.setTitle(this.getTitle());
        cloneTask.setTime(this.getTime());
        cloneTask.setTime(this.getStartTime(), this.getEndTime(), this.getRepeatInterval());
        cloneTask.setActive(this.isActive);
        cloneTask.setRepeatInterval(this.isRepeated());

        return cloneTask;
    }

    /**
     * Checks if this object equal to other object
     *
     * @param task other object
     * @return true, when equal or false, when not equal
     */
    @Override
    public boolean equals(Object task) {
        if (task == null) {
            return false;
        } else if (!task.getClass().getSimpleName().equals(this.getClass().getSimpleName())) {
            return false;
        } else if (this == task) {
            return true;
        } else if (this.hashCode() != ((Task) task).hashCode()) {
            return false;
        } else {
            return this.getTitle().equals(((Task) task).getTitle())
                    && this.getTime() == ((Task) task).getTime()
                    && this.getStartTime() == ((Task) task).getStartTime()
                    && this.getRepeatInterval() == ((Task) task).getRepeatInterval()
                    && this.getEndTime() == ((Task) task).getEndTime()
                    && this.isRepeated() == ((Task) task).isRepeated()
                    && this.isActive() == ((Task) task).isActive();
        }
    }

    /**
     * @return Integer number for every task
     */
    @Override
    public int hashCode() {
        if (isRepeated()) {
            return 29 * (title.hashCode() + 31 * (start + 37 * (end + 29 * (repeat + 31 * ((isActive ? 1 : 0) + 37 * (isRepeated ? 1 : 0))))));
        } else {
            return 29 * (title.hashCode() + 31 * (time + 37 * ((isActive ? 1 : 0) + 29 * (isRepeated ? 1 : 0))));
        }
    }

    /**
     * @param str the string being checked
     * @return true, when string is empty or false otherwise
     */
    public boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }
        char[] word = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            if (word[i] != ' ') {
                return false;
            }
        }
        return true;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return time
     */
    public int getTime() {
        if (isRepeated()) {
            return start;
        } else {
            return time;
        }
    }

    /**
     * @return start time
     */
    public int getStartTime() {
        if (isRepeated()) {
            return start;
        } else {
            return time;
        }
    }

    /**
     * @return end time
     */
    public int getEndTime() {
        if (isRepeated()) {
            return end;
        } else {
            return time;
        }
    }

    /**
     * @return repeat interval
     */
    public int getRepeatInterval() {
        if (isRepeated()) {
            return repeat;
        } else {
            return 0;
        }
    }

    /**
     * @return is task active or not
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * @return is task repeated or not
     */
    public boolean isRepeated() {
        return isRepeated;
    }

    /**
     * @param title title of the task
     */
    public void setTitle(String title) throws RuntimeException {
        if (isEmpty(title)) {
            throw new RuntimeException("Entered empty title of the task");
        }
        this.title = title;
    }

    /**
     * @param time time
     */
    public void setTime(int time) throws RuntimeException {
        if (time < 0) {
            throw new RuntimeException("Entered incorrect time");
        } else {
            this.time = time;
            isRepeated = false;
        }
    }

    /**
     * @param start  start time
     * @param end    end time
     * @param repeat repeat interval
     */
    public void setTime(int start, int end, int repeat) throws RuntimeException {
        if (start < 0) {
            throw new RuntimeException("Entered incorrect start time");
        } else if (end < start) {
            throw new RuntimeException("Entered incorrect end time");
        } else if (repeat < 0 || start + repeat > end) {
            throw new RuntimeException("Entered incorrect repeat interval");
        } else {
            this.start = start;
            this.end = end;
            this.repeat = repeat;
            isRepeated = true;
        }
    }

    /**
     * @param isActive active task or not
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * @param isRepeated repeated task or not
     */
    public void setRepeatInterval(boolean isRepeated) {
        this.isRepeated = isRepeated;
    }
}
