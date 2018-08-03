package ca.celias.amt.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Chris Elias
 */
@XmlRootElement(name="result")
public class ResultDTO<T> {

    private int draw;
    private long recordsTotal;
    private long recordsFiltered;
    private T[] data;

    /**
     * @return the draw
     */
    public int getDraw() {
        return draw;
    }

    /**
     * @param draw
     *            the draw to set
     */
    public void setDraw(int draw) {
        this.draw = draw;
    }

    /**
     * @return the recordsTotal
     */
    public long getRecordsTotal() {
        return recordsTotal;
    }

    /**
     * @param countResult
     *            the recordsTotal to set
     */
    public void setRecordsTotal(long countResult) {
        this.recordsTotal = countResult;
    }

    /**
     * @return the recordsFiltered
     */
    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    /**
     * @param recordsFiltered
     *            the recordsFiltered to set
     */
    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    /**
     * @return the data
     */
    public T[] getData() {
        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData(T[] data) {
        this.data = data;
        recordsTotal = data.length;
    }
}